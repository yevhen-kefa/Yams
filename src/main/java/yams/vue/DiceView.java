package yams.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import yams.model.game.Dice;
import yams.controleur.BoardController;


public class DiceView extends StackPane {
    private final Label label;
    private Dice dice;

    private boolean save = false;
    private boolean permanentlySaved = false;

    private static final double SIZE = 80;

    //Sets the design for the dices
    public DiceView(Dice dice, BoardController controller) {
        this.dice = dice;

        Rectangle background = new Rectangle(SIZE, SIZE);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.DARKGRAY);

        label = new Label(String.valueOf(dice.value()));
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        getChildren().addAll(background, label);

        setOnMouseClicked(event -> {
            // Vérifier si c'est le tour d'un bot
            if (controller.isCurrentPlayerBot()) {
                return; // Ne rien faire si c'est le tour d'un bot
            }

            if (getParent() != null && getParent().getClass() == AnchorPane.class) {
                ((AnchorPane) getParent()).getChildren().remove(this);
            }
            controller.toggleDicePlacement(this);
        });
    }

    public boolean isSaved() {
        return save;
    }
    public boolean isPermanentlySaved() {
        return permanentlySaved;
    }

    public void setSaved(boolean locked) {
        this.save = locked;
    }
    public void setPermanentlySaved(boolean locked) {
        this.permanentlySaved = locked;
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

    public Dice getDice() {
        return dice;
    }
}
