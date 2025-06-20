package yams.model.players;

import javafx.scene.paint.Color;
import yams.model.combinations.CombinationModel;
import yams.model.game.DiceModel;

import java.util.*;


public class Bot implements PlayerModel {
    // The bot's scoresheet (initialized with an empty name)
    private Scoresheet scoresheet;
    private final Set<CombinationModel> usingCombination = new HashSet<>();
    private Color color;
    private final ArrayList<DiceModel> diceSet = new ArrayList<>();

    @Override
    public void setSet(List<DiceModel> choosedSet) {
        this.diceSet.clear();
        for (DiceModel dice : choosedSet) {
            this.diceSet.add(dice);
        }
    }

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
        return true;
    }

    @Override
    public void setName(String name) {
        scoresheet = new Scoresheet(name);
    }

    @Override
    public String getName() {
        // Returns the bot's name
        return "Bot";
    }
    @Override
    public Scoresheet getScoresheet() {
        // Returns the bot's scoresheet
        return scoresheet;
    }

    @Override
    public void chooseReroll(int number, ArrayList<Integer> choice) {

        // Randomly decides how many dice to reroll (0 to 5)
        var rerollCount = new Random().nextInt(6);
        // Randomly selects unique dice indices to reroll (1 to 5)
        Set<Integer> used = new HashSet<>();
        while (choice.size() < rerollCount) {
            var dice = new Random().nextInt(5) + 1;
            if (used.add(dice)) {
                choice.add(dice);
            }
        }
        // Adds 0 at the end to indicate the end of rerolling
        choice.add(0);
    }

    @Override
    public String chooseCombination() {

        if (usingCombination.size() >= 7) {
            return null;
        }
        String combination;
        int i = 0;
        int max = 50;
        do {
            // Bot randomly chooses a valid combination
            combination = CombinationModel.of();
            i++;
            if (i > max) {
                return null;
            }
        } while (usingCombination.contains(CombinationModel.of(combination)));
        //Adds the combination to the list of combinations the bot is using
        usingCombination.add(CombinationModel.of(combination));
        return combination;
    }
}
