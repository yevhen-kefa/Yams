package fr.uge.yams;

import fr.uge.yams.combinations.Combination;

import java.util.*;

public class Human implements Player{

    // scoresheet de l'humain
    private Scoresheet scoresheet;

    @Override
    public void askName() {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().trim();
        scoresheet = new Scoresheet(name.isEmpty() ? "Guest" : name);
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
    public ArrayList<Integer> askReroll(Scanner scanner) {

        ArrayList<Integer> choice = new ArrayList<Integer>();

        while (choice.size() < 5 || choice.getLast() != 0) {

            //Demander a l'humain pour choisir les dés
            System.out.println("Please choose which dice to reroll by entering their index (1 to 5) or 0 to end the reroll.");

            try {
                int number = scanner.nextInt();
                if (number < 0 || number > 5) {
                    System.out.println("Error: Number must be between 0 and 5");
                    scanner.nextLine();
                    continue;
                }
                if (choice.contains(number)) {
                    System.out.println("Error: You've already selected dice " + number);
                    scanner.nextLine();
                    continue;
                }
                choice.add(number);

            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer");
                scanner.nextLine();
            }
        }
        return choice;
    }

    @Override
    public Combination askCombination(Scanner scanner) {
        //Ask player for a combination to score
        System.out.println("Please choose a combination to score in your score sheet by entering its first letter.");
        System.out.println("Valid combinations are: ");
        for (Combination combination : Combination.values()) {
            System.out.println("- " + combination);
        }
        try {
            var input = scanner.nextLine().trim();
            var use = Combination.of(input.toUpperCase());
            //Choose combination from list of valid combinations
            return use;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid combination. Please enter a valid combination.");
            return askCombination(scanner);
        }
    }

}