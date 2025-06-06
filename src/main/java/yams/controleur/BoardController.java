package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import yams.model.NavAgent;
import yams.model.game.Board;
import yams.model.game.Dice;
import yams.model.players.PlayerModel;
import yams.vue.DiceView;

import java.util.ArrayList;
import java.util.List;

public class BoardController {

    private final NavAgent nav = new NavAgent();

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

    private List<PlayerModel> players;

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
        updatePlayersDisplay();
    }

    private void updatePlayersDisplay() {
        playersVBox.getChildren().clear();

        for (PlayerModel player : players) {
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
    private int rollCount = 0;

    // Méthode d'initialisation appelée automatiquement après le chargement du FXML
    @FXML
    public void initialize() {
        board = new Board();
    }


    // Action liée au bouton de relance des dés
    @FXML
    void btnReroll(ActionEvent event) {
        double diceSize = 114; // Розмір кубика

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
    void btnReturn(ActionEvent event) {
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
        double x = 0, y = 0;

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
