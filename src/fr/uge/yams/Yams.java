package fr.uge.yams;

import java.util.Random;
import java.util.Scanner;

import fr.uge.yams.combinations.Combination;

public class Yams {
  private static ScoreSheet init(Scanner scanner) {
    System.out.println("Welcome, player, please enter your name.");
    return new ScoreSheet(scanner.nextLine());
  }

  private static ScoreSheet friend(Scanner scanner) {
    System.out.println("Input your friend's name or leave empty to allow the bot play!!.");
    return new ScoreSheet(scanner.nextLine());
  }

  private static int askReroll(Scanner scanner) {
    System.out.println("Do you want to reroll a dice? Type 0 for no, 1-5 to reroll this dice.");
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

  private static void makeRandomMove(ScoreSheet player, Board board) {
    var rerolls = new Random().nextInt(3);
    System.out.println(player.name() + " rerolls " + rerolls + " times");
    for (var i = 0; i < rerolls; i++) {
      var dice = new Random().nextInt(5) + 1;
      System.out.println(player.name() + " rerolls dice " + dice);
      board.reroll(dice);
    }

    var pattern = Combination.of();
    System.out.println(player.name() + " chooses " + pattern);
    player.updateScore(pattern, board);
  }

  private static boolean chooseGameMode(Scanner scanner) {
    System.out.println("Do you want to play solo or with a friend? (Type 's' or 'f')");
    var choice = scanner.nextLine().trim().toLowerCase();
    if (choice.equals("s")) {
      return true;
    } else if (choice.equals("f")) {
      return false;
    } else {
      System.out.println("Invalid choice. Please type 's' or 'f'.");
      return chooseGameMode(scanner);
    }
  }

  public static void main(String[] args) {
    var scanner = new Scanner(System.in);
    boolean soloMode = chooseGameMode(scanner);

    var player = init(scanner);
    System.out.println("Hello " + player.name() + ", and good luck !\n");

    ScoreSheet friend = null;
    if (!soloMode) {
      friend = friend(scanner);
      System.out.println("Hello " + friend.name() + ", and good luck !\n");
    }

    var turnPlayer = true;

    for (var roundCounter = 0; roundCounter < (soloMode ? 6 : 12); roundCounter++) {
      ScoreSheet currentPlayer = soloMode ? player : (turnPlayer ? player : friend);

      if (!soloMode && friend.isBot() && !turnPlayer) {
        makeRandomMove(friend, new Board());
        System.out.println(friend);
        turnPlayer = !turnPlayer;
        continue;
      }

      System.out.println(currentPlayer.name() + ", Welcome in round " + (soloMode ? roundCounter + 1 : (roundCounter + 1) / 2));
      var board = new Board();
      System.out.println(board);

      for (var updateCounter = 0; updateCounter < 3; updateCounter++) {
        var choice = askReroll(scanner);
        if (choice == 0)
          break;

        board.reroll(choice);
        System.out.println(board);
      }

      var combinationChoice = askCombination(scanner);
      currentPlayer.updateScore(combinationChoice, board);
      System.out.println(currentPlayer);

      if (!soloMode) {
        turnPlayer = !turnPlayer;
      }
    }

    System.out.println("Game Over!");
  }
}