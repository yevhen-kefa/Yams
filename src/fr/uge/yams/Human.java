package fr.uge.yams;

import fr.uge.yams.combinations.Combination;

import java.util.*;

public class Human implements Player{

    // scoresheet de l'humain
    private Scoresheet scoresheet;

    @Override
    public void askName() {
        Scanner scanner = new Scanner(System.in);
        String name;
        do {
            System.out.println("Please enter the player's name:");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            }
        } while (name.isEmpty());
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
    public ArrayList<Integer> askReroll(Scanner scanner) {

        ArrayList<Integer> choice = new ArrayList<Integer>();

        do {

            //Demander a l'humain pour choisir les dés
            System.out.println("Please choose which dice to reroll by entering their index (1 to 5) or 0 to end the reroll.");
            System.out.println(choice);
            Integer n = 0;
            System.out.print(n == 0);

            try {
                int number = scanner.nextInt();
                if (number < 0 || number > 5) {
                    System.out.println("Error: Number must be between 0 and 5");
                    scanner.nextInt();
                    continue;
                }
                if (choice.contains(number)) {
                    System.out.println("Error: You've already selected dice " + number);
                    scanner.nextInt();
                    continue;
                }
                choice.add(number);

            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer");
                scanner.nextLine();
            }
        } while (choice.size() < 5 && choice.getLast() != 0);
        return choice;
    }

    @Override
    public Combination askCombination(Scanner scanner) {
        //Ask player for a combination to score
        scanner.nextLine(); // Ajoutez cette ligne

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