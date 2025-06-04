package yams.vue;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import yams.model.game.Dice;

public class DiceView extends StackPane {
    private final Rectangle background;
    private final Label label;
    private Dice dice;

    public DiceView(Dice dice) {
        this.dice = dice;

        background = new Rectangle(80, 80);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.DARKGRAY);

        label = new Label(String.valueOf(dice.value()));
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        getChildren().addAll(background, label);
    }

    public void updateDice(Dice newDice) {
        this.dice = newDice;
        label.setText(String.valueOf(newDice.value()));
    }

    public Dice getDice() {
        return dice;
    }
}
