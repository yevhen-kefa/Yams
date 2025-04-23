package fr.uge.yams;

import java.util.ArrayList;
import java.util.Scanner;

public class Yams {

	public static String init(Scanner scanner) {

		System.out.println("Welcome, player, please enter your name.");
		return scanner.nextLine();

	}

	private static int askReroll(Scanner scanner) {
	    int choice;

	    while (true) {
	        System.out.println("Do you want to reroll your dices? Type 0 for no, 1-5 to reroll this amount of dices.");
	        System.out.print("Enter a number: ");

	        try {
	            choice = Integer.parseInt(scanner.nextLine());

	            if (choice >= 0 && choice <= 5) {
	                break;
	                
	            }
	            
	            else {
	                System.out.println("Invalid number of rerolls. Please enter a number between 0 and 5.");
	            }
	            
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	        }
	    }
	    return choice;
	}
	
	private static ArrayList<Integer> askWhichDices(int choice, Scanner scanner) {
		
		final ArrayList<Integer> selected = new ArrayList<>();
		for (int i = 0; i < choice; i++) {
		    while (true) {
				System.out.println("which dice do you want to reroll ? select 1-5, " + (i+1) + " out of " + choice);
				System.out.print("Enter a number: ");
				
		        try {
					var selectedDie = Integer.parseInt(scanner.nextLine());
					if (selectedDie >= 0 && selectedDie <= 5) {
						selected.add(selectedDie);
						break;
					}
					
					else if (selected.contains(selectedDie)) {
						System.out.println("You already selected this Die, choose another one.");
					}
					
					else {
						System.out.println("Invalid dice number. Please enter a number between 1 and 5.");
					}
					
				}catch(NumberFormatException e) {
					System.out.println("Invalid input. Please enter a valid number.");
				}
			}
		}
		return selected;
	}

	private static String askCombination(Scanner scanner) {
		System.out.println("Please choose a combination to score in your score sheet by entering its first letter.");
		System.out.print("Enter a Combination: ");
		
		var choice = scanner.nextLine();
		return choice;
	}

	private static Combination parseCombination(String combinationName) {

		return switch (combinationName) {
		//Ajout de toutes les combinaisons
		case "C" -> new Chance();
		case "T" -> new ThreeOfAKind();
		case "FK" -> new FourOfAKind();
		case "F" -> new FullHouse();
		case "S" -> new SmallStraight();
		case "L" -> new LargeStraight();
		case "Y" -> new Yam();
		default -> throw new IllegalArgumentException("Unexpected value: " + combinationName);
		};
	}

	public static void main(String[] args) {

		var scanner = new Scanner(System.in);
		var name = init(scanner);
		if (name == "") {
			System.out.println("Hello guest, and good luck !\n");
		}
		else {
			System.out.println("Hello " + name + ", and good luck !\n");
		}

		var scoreSheet = new ScoreSheet();
		// Début du tour du joueur
		for (var roundCounter = 0; roundCounter < 7; roundCounter++) {
			System.out.println("Welcome in round " + (roundCounter + 1));
			var board = new Board();
			System.out.println(board);
			// Relances dans le tour
			for (var updateCounter = 0; updateCounter < 2; updateCounter++) {
				var choice = askReroll(scanner);
				if (choice > 0) {
					//on choisit combien de des on souhaite relancer, on choisit ensuite lesquels
					final ArrayList<Integer> selectedDices = askWhichDices(choice, scanner);
					for (Integer i : selectedDices) {
						board.reroll(i);
					}
					System.out.println(board);
				} else {
					break;
				}
			}
			Combination combinationChoice;

			while (true) {
			    try {
			        combinationChoice = parseCombination(askCombination(scanner));
			        break;

			    } catch (IllegalArgumentException e) {
			        System.out.println("Invalid input. Please enter a valid combination that isn't already in the scoresheet");
			        System.out.println("C, F, FK, L, S, T, Y.");
			    }
			}
			
			scoreSheet.updateScore(combinationChoice, board);
			System.out.println(scoreSheet);
		}
		System.out.println("C'est fini !");
	}
}
