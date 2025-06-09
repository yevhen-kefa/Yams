package yams.model.players;

import javafx.scene.paint.Color;
import yams.model.game.DiceModel;

import java.util.ArrayList;
import java.util.List;

public class Human implements PlayerModel {
    // scoresheet de l'humain
    private Scoresheet scoresheet;
    private Color color;
    private final ArrayList<DiceModel> diceSet = new ArrayList<>();

    @Override
    public void setSet(List<DiceModel> choosedSet) {
        this.diceSet.clear();
        for (DiceModel dice : choosedSet) {
            this.diceSet.add(dice);
        }
    }

    @Override
    public List<DiceModel> getSet() {
        return diceSet;
    }

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