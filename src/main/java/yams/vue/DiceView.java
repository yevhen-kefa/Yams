package yams.vue;
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
   /* public String getSpritePath() {
        return "/img/dice/" + type.toString().toLowerCase() + "/" + value + ".png";
    }*/
    //Sets the design for the dices
    public DiceView(DiceModel dice, BoardController controller) {
        this.dice = dice;

        this.background = new ImageView(dice.getSpritePath());
        this.background.setFitWidth(SIZE);
        this.background.setFitHeight(SIZE);

        getChildren().addAll(background);

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
        this.dice = newDice;
        System.out.println("🔁 Updating dice: " + newDice.getType() + " " + newDice.value() + " -> " + newDice.getSpritePath());

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

    public DiceModel getDice() {
        return dice;
    }
}
