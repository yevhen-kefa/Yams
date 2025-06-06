package yams.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import yams.model.game.Dice;
import yams.controleur.BoardController;


public class DiceView extends StackPane {
    private final Rectangle background;
    private final Label label;
    private Dice dice;

    private static final double SIZE = 80;

    //Sets the design for the dices
    public DiceView(Dice dice) {
        this.dice = dice;

        background = new Rectangle(SIZE, SIZE);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.DARKGRAY);

        label = new Label(String.valueOf(dice.value()));
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        getChildren().addAll(background, label);

        setOnMouseClicked(event -> {
            if (getParent() != null && getParent().getClass() == AnchorPane.class) {
                ((AnchorPane) getParent()).getChildren().remove(this);
            }
            BoardController.moveToSaved(this);
        });
    }
    // Update dices
    public void updateDice(Dice newDice) {
        this.dice = newDice;
        label.setText(String.valueOf(newDice.value()));
    }

    //Position dices
    public void setPosition(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }

    // Set random rotation
    public void setRandomRotation() {
        setRotate(-30 + Math.random() * 60);
    }


    public double getDiceSize() {
        return SIZE;
    }

    public Dice getDice() {
        return dice;
    }
}
