package fr.uge.yams;
import fr.uge.yams.combinations.Combination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Bot implements Player{
    // The bot's scoresheet (initialized with an empty name)
    private Scoresheet scoresheet;
    private Set<Combination> usingCombination = new HashSet<>();
    private int rerollAttempts = 0;


    public void askName() {
        scoresheet = new Scoresheet("Bot");
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
    public ArrayList<Integer> askReroll(Scanner scanner) {
        rerollAttempts = 0;
        if (rerollAttempts >= 3) {
            System.out.println("Bot has reached maximum rerolls.");
            var stop = new ArrayList<Integer>();
            stop.add(0);
            return stop;
        }

        System.out.println("Bot is deciding which dice to reroll...");

        // Creates a list for dice the bot wants to reroll
        var rerolls = new ArrayList<Integer>();
        // Randomly decides how many dice to reroll (0 to 2)
        var rerollCount = new Random().nextInt(5);
        // Randomly selects unique dice indices to reroll (1 to 5)
        Set<Integer> used = new HashSet<>();
        while (rerolls.size() < rerollCount) {
            var dice = new Random().nextInt(5) + 1;
            if (used.add(dice)) {
                rerolls.add(dice);
            }
        }
        // Adds 0 at the end to indicate the end of rerolling
        rerolls.add(0);
        // Prints which dice the bot will reroll
        System.out.println("Bot will rerol dice: " + rerolls);
        rerollAttempts++;
        return rerolls;
    }

    @Override
    public Combination askCombination(Scanner scanner) {
        System.out.println("Bot is choosing a combination to score...");
        if (usingCombination.size() >= 7) {
            System.out.println("Bot has used all combinations and cannot choose a new one.");
            return null;
        }
        Combination combination;
        int i = 0;
        int max = 50;
        do {
            // Bot randomly chooses a valid combination
            combination = Combination.of();
            i++;
            if (i > max) {
                System.out.println("Bot could not find a valid combination after " + max + " tries.");
                break;
            }
        } while (usingCombination.contains(combination));
        //Adds the combination to the list of combinations the bot is using
        usingCombination.add(combination);
        // Prints the chosen combination
        System.out.println("Bot chooses combination: " + combination);
        return combination;
    }
}
