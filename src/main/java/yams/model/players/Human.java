package yams.model.players;

import yams.model.combinations.CombinationModel;

import java.util.ArrayList;

public class Human implements PlayerModel {

    // scoresheet de l'humain
    private Scoresheet scoresheet;

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

    @Override
    public CombinationModel chooseCombination(String input) {
            //Choose a combination from a list of valid combinations
        if (input.isEmpty() || scoresheet.containsCombination(CombinationModel.of(input.toUpperCase()))) {
            throw new IllegalArgumentException("Invalid combination");
        }
        return CombinationModel.of(input.toUpperCase());

    }

}