package yams.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import yams.model.NavAgent;
import yams.model.combinations.CombinationModel;
import yams.model.game.Board;
import yams.model.game.Dice;
import yams.model.players.PlayerModel;
import yams.vue.DiceView;

import java.util.ArrayList;
import java.util.List;

public class BoardController {

    private final NavAgent nav = new NavAgent();
    private final List<Label> playerScoreLabels = new ArrayList<>();


    @FXML
    private Button btnReroll;

    @FXML
    private Button btnReturn;

    @FXML
    private Label nomPlayer;

    @FXML
    private Label scrPlayer;


    @FXML
    private Label rerollCount;

    @FXML
    private AnchorPane anchorDice;

    private final List<DiceView> diceViews = new ArrayList<>();
    private Board board;

    @FXML
    private VBox playersVBox;


    @FXML
    private StackPane rootLayout;

    @FXML
    private VBox scoresVBox;

    private List<PlayerModel> party;

    private int currentPlayerIndex = 0;

    public void setParty(List<PlayerModel> party) {
        this.party = party;
        updatePlayersDisplay();
        updateCurrentPlayerDisplay(party.get(0));
    }

    private void updatePlayersDisplay() {
        playersVBox.getChildren().clear();

        for (PlayerModel player : party) {
            Label playerLabel = new Label(player.getName());

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
            playersVBox.getChildren().add(playerLabel);
        }
    }

    private void updateScoresDisplay() {
        scoresVBox.getChildren().clear();
        playerScoreLabels.clear();

        for (PlayerModel player : party) {
            int totalScore = player.getScoresheet().scoreTotal();
            Label scoreLabel = new Label(player.getName() + ": " + totalScore);

            Color fxColor = player.getColor() != null ? player.getColor() : Color.BLACK;
            String colorHex = String.format("#%02x%02x%02x",
                    (int) (fxColor.getRed() * 255),
                    (int) (fxColor.getGreen() * 255),
                    (int) (fxColor.getBlue() * 255));

            scoreLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: " + colorHex + ";");
            scoresVBox.getChildren().add(scoreLabel);
            playerScoreLabels.add(scoreLabel);
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

        // score setup
        scrPlayer.setText(player.getScoresheet().toString());

    }

    public void refreshPlayerScores() {
        for (int i = 0; i < party.size(); i++) {
            PlayerModel player = party.get(i);
            Label label = playerScoreLabels.get(i);
            label.setText(player.getName() + ": " + player.getScoresheet().scoreTotal());
        }
    }

    private int rollCount = 0;

    // Méthode d'initialisation appelée automatiquement après le chargement du FXML
    @FXML
    public void initialize() {
        board = new Board();
        for (int i = 0; i < 7; i++) {

        }
    }


    // Action liée au bouton de relance des dés
    @FXML
    void btnReroll() {

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

            // Використовуємо вже готову функцію для позиціонування та ротації
            placeDiceWithoutOverlap(diceView, i);
        }

        rollCount++;
        if (rollCount < 4) {
            rerollCount.setText((3 - rollCount) + "/3");
        }
        
    }
    // Button Return
    @FXML
    void btnReturn() {
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        nav.goTo(stage, "/mode.fxml");
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
    private void endTurn(PlayerModel player, CombinationModel combination) {
        player.updateScore(combination, board);  // score recording
        refreshPlayerScores();                   // VBox update with points
        //TODO The logic of switching to another player
    }
    private void goToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % party.size();
        PlayerModel next = party.get(currentPlayerIndex);
        updateCurrentPlayerDisplay(next);
    }
/*
    void onConfirmCombination(ActionEvent event) {
        CombinationModel chosen = ... // отримай вибрану комбінацію
        PlayerModel current = players.get(currentPlayerIndex);
        endTurn(current, chosen);
    }
*/

}
