package yams.vue;
<<<<<<< HEAD

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
    private BoardController controller;

    private static final double SIZE = 80;

    //Sets the design for the dices
    public DiceView(Dice dice, BoardController controller)  {
=======
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import yams.controleur.BoardController;
import yams.model.game.DiceModel;


public class DiceView extends StackPane {
    public ImageView background;
    private DiceModel dice;

    private boolean save = false;
    private boolean permanentlySaved = false;

    private static final double SIZE = 80;

    //Sets the design for the dices
    public DiceView(DiceModel dice, BoardController controller) {
>>>>>>> 79cac2900e188a6e1d6c1ae7522a5d54eb7ace14
        this.dice = dice;
        this.controller = controller;

<<<<<<< HEAD
        background = new Rectangle(SIZE, SIZE);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.DARKGRAY);
=======
        this.background = new ImageView(dice.getSpritePath());
        this.background.setFitWidth(SIZE);
        this.background.setFitHeight(SIZE);
>>>>>>> 79cac2900e188a6e1d6c1ae7522a5d54eb7ace14

        getChildren().addAll(background);

<<<<<<< HEAD
        getChildren().addAll(background, label);

        setOnMouseClicked(event -> {
            if (getParent() != null && getParent().getClass() == AnchorPane.class) {
                ((AnchorPane) getParent()).getChildren().remove(this);
            }
            controller.toggleDicePlacement(this);
        });
    }
    // Update dices
    public void updateDice(Dice newDice) {
=======
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
    public void updateDice(DiceModel newDice) {
>>>>>>> 79cac2900e188a6e1d6c1ae7522a5d54eb7ace14
        this.dice = newDice;
        System.out.println("Updating dice: " + newDice.getType() + " " + newDice.value() + " -> " + newDice.getSpritePath());

        this.getChildren().clear();
        this.background = new ImageView(newDice.getSpritePath());
        this.background.setFitWidth(SIZE);
        this.background.setFitHeight(SIZE);
        this.getChildren().add(this.background);
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

<<<<<<< HEAD

    public double getDiceSize() {
        return SIZE;
    }

    public Dice getDice() {
=======
    public DiceModel getDice() {
>>>>>>> 79cac2900e188a6e1d6c1ae7522a5d54eb7ace14
        return dice;
    }
}
