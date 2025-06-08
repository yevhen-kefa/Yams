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
import yams.model.players.PlayerModel;
import yams.vue.DiceView;
import yams.vue.CombinationSelectionView;


import java.util.ArrayList;
import java.util.List;

public class BoardController {
    private final NavAgent nav = new NavAgent();
    private final List<DiceView> diceViews = new ArrayList<>();
    private Board board;
    private List<PlayerModel> party;
    private PlayerModel currentPlayer;
    private boolean canChooseCombination = true;
    private int rollCount = 0;
    private int currentRound = 1;
    private final int MAX_ROUNDS = 7;


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
    @FXML
    private Label roundCount;

    public void setParty(List<PlayerModel> party) {
        this.party = party;
        this.currentPlayer = party.get(0);
        updatePlayersDisplay();
        startNewTurn();
    }

    private void updatePlayersDisplay() {
        playersVBox.getChildren().clear();
        for (PlayerModel player : party) {
            Label playerLabel = new Label(player.getName());

            if (player == currentPlayer) {
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
        nomPlayer.setText(player.getName());
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

    public void toggleDicePlacement(DiceView die) {
        if (die.isPermanentlySaved()) {
            return;
        }

        if (saveDice.getChildren().contains(die)) {
            // Move back to board
            saveDice.getChildren().remove(die);
            anchorDice.getChildren().add(die);
            placeDiceWithoutOverlap(die, diceViews.indexOf(die));
            die.setSaved(false);
        } else {
            // Move to saveDice
            anchorDice.getChildren().remove(die);
            saveDice.getChildren().add(die);
            die.setLayoutX(0);
            die.setLayoutY(0);
            die.setRotate(0);
            die.setSaved(false);
        }
    }

    private void startNewTurn() {
        if (party == null || party.isEmpty()) return;
        //
        roundCount.setText("Round " + currentRound + " of " + MAX_ROUNDS);

        if (currentRound > MAX_ROUNDS) {
            endGame();
            return;
        }

        rollCount = 0;
        canChooseCombination = true;
        board = new Board();

        anchorDice.getChildren().clear();
        diceViews.clear();
        saveDice.getChildren().clear();

        updateCurrentPlayerDisplay(currentPlayer);
        updatePlayersDisplay();

        boolean isBot = currentPlayer.isBot();

        if (isBot) {
            btnReroll.setDisable(true);
            btnEnd.setDisable(true);
            btnReroll.setText("Bot is playing...");
        } else {
            btnReroll.setDisable(false);
            btnReroll.setText("Roll Dice");
            btnEnd.setDisable(true);
        }

        rerollCount.setText("3/3");

        if (isBot) {
            playBotTurn();
        }
    }

    private void playBotTurn() {
        btnReroll();

        // Random number of rerolls between 1 and 3
        int numRerolls = (int) (Math.random() * 3) + 1;

        new Thread(() -> {
            try {
                for (int i = 0; i < numRerolls; i++) {
                    Thread.sleep(1500);

                    // Randomly save/put back 0-5 dice
                    int numToToggle = (int) (Math.random() * 6);
                    for (int j = 0; j < numToToggle; j++) {
                        int diceIndex = (int) (Math.random() * diceViews.size());
                        DiceView diceToToggle = diceViews.get(diceIndex);
                        javafx.application.Platform.runLater(() -> {
                            toggleDicePlacement(diceToToggle);
                        });
                    }

                    javafx.application.Platform.runLater(() -> {
                        btnReroll();
                    });
                }

                Thread.sleep(1500);
                javafx.application.Platform.runLater(() -> {
                    currentPlayer.chooseCombination();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @FXML
    void btnReroll() {
        boolean isBot = currentPlayer.isBot();

        if (canChooseCombination && !isBot) {
            btnEnd.setDisable(false);
        } else {
            btnEnd.setDisable(true);
            canChooseCombination = false;
        }

        if (rollCount == 0) {
            for (int i = 0; i < 5; i++) {
                Dice dice = board.getDice(i);
                DiceView diceView = new DiceView(dice, this);
                diceViews.add(diceView);
                anchorDice.getChildren().add(diceView);
            }
            btnReroll.setText("Reroll");
        } else if (rollCount == 2) {
            btnReroll.setText("No rolls left");
            btnReroll.setDisable(true);
        }

        //We prohibit reverse movements after this moment.
        for (DiceView diceView : diceViews) {
            if (saveDice.getChildren().contains(diceView)) {
                diceView.setSaved(true);
                diceView.setPermanentlySaved(true);
            }
        }

        for (int i = 0; i < 5; i++) {
            DiceView diceView = diceViews.get(i);
            if (diceView.isSaved()) continue;
            Dice newDice = board.reroll(i);

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
        if (currentPlayer.isBot()) {
            return;
        }
        // Utiliser la vue au lieu du modèle pour l'interaction utilisateur
        String result = CombinationSelectionView.chooseCombination();


        if (!result.isEmpty()) {
            finishTurn(result);
        }
    }

    private void finishTurn(String combination) {
        if (!currentPlayer.getScoresheet().containsCombination(CombinationModel.of(combination))) {
            currentPlayer.updateScore(CombinationModel.of(combination), board);
            scrPlayer.setText(currentPlayer.getScoresheet().toString());
            nextPlayer();
        } else {
            if (!currentPlayer.isBot()) {
                showError("You have already chosen this combination. \n Please choose another one.");
            }
        }
    }

    private void nextPlayer() {
        int currentIndex = party.indexOf(currentPlayer);
        currentIndex++;

        if (currentIndex >= party.size()) {
            currentIndex = 0;
            currentRound++;
        }

        currentPlayer = party.get(currentIndex);
        btnEnd.setDisable(true);
        startNewTurn();
    }

    private void endGame() {
        PlayerModel winner = party.get(0);
        int maxScore = winner.getScoresheet().scoreTotal();

        for (PlayerModel player : party) {
            int score = player.getScoresheet().scoreTotal();
            if (score > maxScore) {
                maxScore = score;
                winner = player;
            }
        }

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

        nav.goTo(btnReturn, "/menu.fxml");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    public boolean isCurrentPlayerBot() {
        if (party == null || party.isEmpty()) {
            return false;
        }
        return currentPlayer.isBot();
    }

    @FXML
    public void initialize() {
        board = new Board();
        btnEnd.setDisable(true);
        rerollCount.setText("3/3");
    }
}

