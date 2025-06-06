package yams.controleur;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yams.model.NavAgent;
import yams.model.game.Board;
import yams.model.game.Dice;
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
    private AnchorPane anchorDice;

    private final List<DiceView> diceViews = new ArrayList<>();
    private Board board;

    //Creating and adding dices to the screen
    @FXML
    public void initialize() {
        board = new Board();

        Platform.runLater(() -> {
            for (int i = 0; i < 5; i++) {
                Dice dice = board.getDice(i);
                DiceView diceView = new DiceView(dice);
                diceViews.add(diceView);
                anchorDice.getChildren().add(diceView);
                placeDiceWithoutOverlap(diceView, i);
            }
        });
    }
    //Button rerol
    @FXML
    void btnReroll(ActionEvent event) {
        for (int i = 0; i < 5; i++) {
            Dice newDice = board.reroll(i);
            DiceView diceView = diceViews.get(i);
            diceView.updateDice(newDice);
            placeDiceWithoutOverlap(diceView, i);
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
        double diceSize = current.getDiceSize()+50;
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
