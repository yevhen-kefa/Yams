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
					
					if (selected.contains(selectedDie)) {
						System.out.println("You already selected this Die, choose another one.");
					}
					
					else if (selectedDie >= 0 && selectedDie <= 5) {
						selected.add(selectedDie);
						break;
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
		System.out.println("Please choose a combination to score in your score sheet or to sacrifice by entering its first letter.");
		System.out.print("Enter a Combination: ");
		
		var choice = scanner.nextLine();
		return choice;
	}

	private static CombinationResult parseCombination(String combinationName, Board board) {
	    return switch (combinationName) {
	        case "C" -> {
	            var combo = new Chance();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
	        case "T" -> {
	            var combo = new ThreeOfAKind();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
	        case "FK" -> {
	            var combo = new FourOfAKind();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
	        case "F" -> {
	            var combo = new FullHouse();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
	        case "S" -> {
	            var combo = new SmallStraight();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
	        case "L" -> {
	            var combo = new LargeStraight();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
	        case "Y" -> {
	            var combo = new Yam();
	            yield combo.amITheRightOne(board.sendYourself()) ? new CombinationResult(combo,true) : new CombinationResult(combo,false);
	        }
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
			CombinationResult result;

			while (true) {
			    try {
			        result = parseCombination(askCombination(scanner), board);
			        if(scoreSheet.sendScoreMap().containsKey(result.combination()) && scoreSheet.sendScoreMap().get(result.combination()) == 0) {
			        	System.out.println("This cell was already sacrificed, choose another one.");
					}
			        else {
			        	scoreSheet.updateScore(result, board);
			        	break;
			        }	

			    } catch (IllegalArgumentException e) {
			        System.out.println("Invalid input. Please enter a valid combination that isn't already in the scoresheet");
			        System.out.println("C, F, FK, L, S, T, Y.");
			    } 
			}
			
			System.out.println(scoreSheet);
		}
		System.out.println("C'est fini ! Votre score final est de : " + scoreSheet.scoreTotal() + " points !");
	}
}
