package fr.uge.yams;

import java.util.Scanner;

import fr.uge.yams.combinations.Combination;

public class Yams {
	public static String init(Scanner scanner) {
		System.out.println("Welcome, player, please enter your name.");
		return scanner.nextLine();
	}

	private static int askReroll(Scanner scanner) {
		System.out.println("Do you want to reroll a die? Type 0 for no, 1-5 to reroll this die.");
		var choice = scanner.nextLine();
    try {
      return Integer.parseInt(choice);
    } catch (NumberFormatException e) {
      System.out.println("Please enter a number between 0 and 5.");
      return askReroll(scanner);
    }
	}

	private static Combination askCombination(Scanner scanner) {
		System.out.println("Please choose a combination to score in your score sheet by entering its first letter.");
    try {
      return Combination.of(scanner.nextLine());
    } catch (IllegalArgumentException e) {
      System.out.println("Please enter a valid combination.");
      return askCombination(scanner);
    }
	}

	public static void main(String[] args) {

		var scanner = new Scanner(System.in);
		var name = init(scanner);
		System.out.println("Hello " + name + ", and good luck !\n");

		var scoreSheet = new ScoreSheet();
		// Début du tour du joueur
		for (var roundCounter = 0; roundCounter < 2; roundCounter++) {
			System.out.println("Welcome in round " + (roundCounter + 1));
			var board = new Board();
			System.out.println(board);

      // Relances dans le tour
			for (var updateCounter = 0; updateCounter < 3; updateCounter++) {
				var choice = askReroll(scanner);
				if (choice > 0) {
					board.reroll(choice);
					System.out.println(board);
				} else {
					break;
				}
			}

			var combinationChoice = askCombination(scanner);
			scoreSheet.updateScore(combinationChoice, board);
			System.out.println(scoreSheet);
		}
		System.out.println("C'est fini !");
	}

}
