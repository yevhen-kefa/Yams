package yams.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import yams.model.NavAgent;
import yams.model.combinations.CombinationModel;
import yams.model.game.Board;
import yams.model.game.Dice;
import yams.model.players.Bot;
import yams.model.players.PlayerModel;
import yams.vue.DiceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardController {
    private final NavAgent nav = new NavAgent();
    private final List<DiceView> diceViews = new ArrayList<>();
    private Board board;
    private List<PlayerModel> party;
    private int currentPlayerIndex = 0;
    private boolean canChooseCombination = true;
    private int rollCount = 0;
    private int currentRound = 1;
    private final int MAX_ROUNDS = 7;
    private VBox staticSaveDice;



    @FXML
    private Button btnReroll;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnEnd;
    @FXML
    private Label nomPlayer;
    @FXML
    private Label scrPlayer;
    @FXML
    private Label rerollCount;
    @FXML
    private VBox playersVBox;
    @FXML
    private AnchorPane anchorDice;
    @FXML
    private VBox saveDice;

    public void setParty(List<PlayerModel> party) {
        this.party = party;
        updatePlayersDisplay();
        startNewTurn();
    }

    private void updatePlayersDisplay() {
        playersVBox.getChildren().clear();
        for (int i = 0; i < party.size(); i++) {
            PlayerModel player = party.get(i);
            Label playerLabel = new Label(player.getName());

            // Mettre en évidence le joueur actuel
            if (i == currentPlayerIndex) {
                playerLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #FFD700; -fx-background-color: rgba(255,215,0,0.3); -fx-padding: 5;");
            } else {
                Color fxColor = player.getColor();
                if (fxColor == null) {
                    fxColor = Color.BLACK;
                }
                String colorHex = String.format("#%02x%02x%02x",
                        (int) (fxColor.getRed() * 255),
                        (int) (fxColor.getGreen() * 255),
                        (int) (fxColor.getBlue() * 255)
                );
                playerLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: " + colorHex + ";");
            }
            playersVBox.getChildren().add(playerLabel);
        }
    }


    private void updateCurrentPlayerDisplay(PlayerModel player) {
        // name setup
        nomPlayer.setText(player.getName());
        // color setup
        Color fxColor = player.getColor();
        if (fxColor == null) fxColor = Color.BLACK;

        String colorHex = String.format("#%02x%02x%02x",
                (int) (fxColor.getRed() * 255),
                (int) (fxColor.getGreen() * 255),
                (int) (fxColor.getBlue() * 255)
        );
        nomPlayer.setStyle("-fx-text-fill: " + colorHex + "; -fx-font-size: 25px;");
        scrPlayer.setText(player.getScoresheet().toString());
    }

    // Méthode d'initialisation appelée automatiquement après le chargement du FXML
    @FXML
    public void initialize() {
        board = new Board();
        btnEnd.setDisable(true);
        rerollCount.setText("3/3");
    }

    public void moveToSaved(DiceView diceView) {
        if (!saveDice.getChildren().contains(diceView)) {
            diceView.setLayoutX(0);
            diceView.setLayoutY(0);
            diceView.setRotate(0);
            saveDice.getChildren().add(diceView);
        }
    }
    public void toggleDicePlacement(DiceView diceView) {
        if (saveDice.getChildren().contains(diceView)) {
            // Видалити з VBox і повернути на дошку
            saveDice.getChildren().remove(diceView);
            anchorDice.getChildren().add(diceView);

            // Розмістити без перекриття
            placeDiceWithoutOverlap(diceView, diceViews.indexOf(diceView));
        } else {
            anchorDice.getChildren().remove(diceView);
            saveDice.getChildren().add(diceView);

            // Скинути позицію/поворот (не обов'язково, але зручно)
            diceView.setLayoutX(0);
            diceView.setLayoutY(0);
            diceView.setRotate(0);
        }
    }

    private void startNewTurn() {
        if (party == null || party.isEmpty()) return;

        // Vérifier si le jeu est terminé
        if (currentRound > MAX_ROUNDS) {
            endGame();
            return;
        }

        // Réinitialiser pour le nouveau tour
        rollCount = 0;
        canChooseCombination = true;
        board = new Board(); // Nouveau plateau pour ce tour

        // Nettoyer les dés précédents
        anchorDice.getChildren().clear();
        diceViews.clear();

        // Mettre à jour l'affichage
        updateCurrentPlayerDisplay(party.get(currentPlayerIndex));
        updatePlayersDisplay();

        // Vérifier si c'est un bot ou un joueur humain
        boolean isBot = party.get(currentPlayerIndex).isBot();

        if (isBot) {
            // Désactiver les boutons pour les bots
            btnReroll.setDisable(true);
            btnEnd.setDisable(true);
            btnReroll.setText("Bot is playing...");
        } else {
            // Réactiver les boutons pour les joueurs humains
            btnReroll.setDisable(false);
            btnReroll.setText("Roll Dice");
            btnEnd.setDisable(true); // End reste désactivé jusqu'au premier lancer
        }

        rerollCount.setText("3/3");

        // Si c'est un bot, jouer automatiquement
        if (isBot) {
            playBotTurn();
        }
    }

    private void playBotTurn() {
        // Simulation simple pour le bot - vous pouvez améliorer cette logique
        btnReroll(); // Premier lancer

        // Simuler un délai puis choisir une combinaison
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Attendre 1.5 secondes
                javafx.application.Platform.runLater(() -> {
                    // Le bot choisit automatiquement la première combinaison disponible
                    chooseBestCombinationForBot();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    private void chooseBestCombinationForBot() {
        Bot currentPlayer = (Bot) party.get(currentPlayerIndex);
        finishTurn(currentPlayer.chooseCombination());
    }


    // Action liée au bouton de relance des dés
    @FXML
    void btnReroll() {
        
        boolean isBot = party != null && !party.isEmpty() && party.get(currentPlayerIndex).isBot();

        if (canChooseCombination && !isBot) {
            btnEnd.setDisable(false);  // Activer seulement pour les joueurs humains
        } else {
            btnEnd.setDisable(true);
            canChooseCombination = false;
        }


        if (rollCount == 0) {
            for (int i = 0; i < 5; i++) {
                Dice dice = board.getDice(i);
                DiceView diceView = new DiceView(dice);
                diceViews.add(diceView);
                anchorDice.getChildren().add(diceView);
            }
            
            btnReroll.setText("Reroll");
        } else if (rollCount == 2) {
            btnReroll.setText("No rolls left");
            btnReroll.setDisable(true);
        }

        for (int i = 0; i < 5; i++) {
            Dice newDice = board.reroll(i);
            DiceView diceView = diceViews.get(i);
            diceView.updateDice(newDice);
            placeDiceWithoutOverlap(diceView, i);
        }
        
        rollCount++;
        if (rollCount < 4) {
            rerollCount.setText((3 - rollCount) + "/3");
        }
    }
    
    @FXML
    void btnReturn() {
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        nav.goTo(stage, "/mode.fxml");
    }

    @FXML
    void btnEnd() {

        if (party.get(currentPlayerIndex).isBot()) {
            return;
        }
        String result = party.get(currentPlayerIndex).chooseCombination();

        if (!result.isEmpty()) {
            finishTurn(result);
        }

    }

    private void finishTurn(String combination) {
        PlayerModel current = party.get(currentPlayerIndex);

        if (!current.getScoresheet().containsCombination(CombinationModel.of(combination))) {
            current.updateScore(CombinationModel.of(combination), board);
            scrPlayer.setText(current.getScoresheet().toString());
            // Passer au joueur suivant
            nextPlayer();

        } else {
            if (!current.isBot()) { // Ne pas montrer d'erreur pour les bots
                showError("You have already chosen this combination. \n Please choose another one.");
            }
        }
    }

    private void nextPlayer() {
        currentPlayerIndex++;

        // Si on a fait le tour de tous les joueurs, passer au tour suivant
        if (currentPlayerIndex >= party.size()) {
            currentPlayerIndex = 0;
            currentRound++;
        }
        btnEnd.setDisable(true);
        // Commencer le tour du joueur suivant
        startNewTurn();
    }
    private void endGame() {
        // Calculer les scores finaux et déterminer le gagnant
        PlayerModel winner = party.get(0);
        int maxScore = winner.getScoresheet().scoreTotal();

        for (PlayerModel player : party) {
            int score = player.getScoresheet().scoreTotal();
            if (score > maxScore) {
                maxScore = score;
                winner = player;
            }
        }

        // Afficher le résultat
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Finished!");

        StringBuilder results = new StringBuilder("Final Scores:\n\n");
        for (PlayerModel player : party) {
            results.append(player.getName()).append(": ").append(player.getScoresheet().scoreTotal()).append(" points\n");
        }
        results.append("\nWinner: ").append(winner.getName()).append(" with ").append(maxScore).append(" points!");

        alert.setContentText(results.toString());
        alert.showAndWait();

        // Retourner au menu principal
        btnReturn();
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Places a cube without intersecting with others
    private void placeDiceWithoutOverlap(DiceView current, int index) {
        double diceSize = 114;
        double maxWidth = anchorDice.getWidth() - diceSize;
        double maxHeight = anchorDice.getHeight() - diceSize;

        boolean overlap;
        int attempts = 0;
        double x;
        double y;

        do {
            overlap = false;
            x = Math.random() * maxWidth;
            y = Math.random() * maxHeight;

            for (int j = 0; j < diceViews.size(); j++) {
                if (j == index) continue;
                DiceView other = diceViews.get(j);

                double x1 = other.getLayoutX();
                double y1 = other.getLayoutY();

                if (x1 < x + diceSize && x1 + diceSize > x &&
                        y1 < y + diceSize && y1 + diceSize > y) {
                    overlap = true;
                    break;
                }
            }
            attempts++;
            
        } while (overlap && attempts < 100);

        current.setPosition(x, y);
        current.setRandomRotation();
    }
}