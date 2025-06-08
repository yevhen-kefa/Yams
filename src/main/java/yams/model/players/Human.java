package yams.model.players;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.paint.Color;
import yams.model.combinations.CombinationModel;

import java.util.ArrayList;
import java.util.Optional;

public class Human implements PlayerModel {

    // scoresheet de l'humain
    private Scoresheet scoresheet;
    private Color color;

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public void setName(String name) {
        scoresheet = new Scoresheet(name);
    }

    @Override
    public String getName() {
        return scoresheet.name();
    }

    @Override
    public Scoresheet getScoresheet() {
        return scoresheet;
    }

    @Override
    public void chooseReroll(int number, ArrayList<Integer> choice) {

        if (number < 0 || number > 5) {
            throw new IllegalArgumentException("Invalid number of dice to reroll");
        }
        else if (choice.contains(number)) {
            throw new IllegalArgumentException("Dice number already chosen");
        }
        choice.add(number);
    }

    public String chooseCombination() {
        return null;
    }


}