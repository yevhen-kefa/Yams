package fr.uge.yams;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Yams {

  private static Scanner scanner = new Scanner(System.in);
  private ArrayList<Player> players;

  private static ArrayList<Player> createParty(Scanner scanner) {
    ArrayList<Player> party = new ArrayList<Player>();
    int nbPlayers = 0;
    System.out.println("How many players? :");

    try {
      nbPlayers = Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
      System.out.println("Error : Invalid number of players. Please try again.");
      return createParty(scanner);
    }

    for (int i = 0; i < nbPlayers; i++) {
      System.out.println("What is Player " + (i + 1) + " ? H for Human or B for Bot :");
      String name = scanner.nextLine().trim();
      if (name.equalsIgnoreCase("B") || name.equalsIgnoreCase("b")) {
        party.add(new Bot());
      } else if (name.equalsIgnoreCase("H") || name.equalsIgnoreCase("h")) {
        party.add(new Human());
      } else {
        System.out.println("Error : Invalid player type. Please try again.");
        i--;
        continue;
      }
      party.get(i).askName();
    }
    return party;
  }
  public static void main(String[] args) {
    ArrayList<Player> party = createParty(scanner);
    System.out.println("Game started!");
    for (int i = 0; i < 7; i++) {
      System.out.println("-----------------" + "Round " + (i + 1) + "-----------------\n");
      for (Player player : party) {
        player.playTurn();
      }
    }
    System.out.println("Game Over!");

    System.out.println("\nFinal Scores:");
    for (Player player : party) {
      String name = player.getName();
      int score = player.getScoresheet().scoreTotal();
      System.out.println(name + ": " + score + " points");
    }

    Player winner = party.stream()
            .max((a, b) -> Integer.compare(a.getScoresheet().scoreTotal(), b.getScoresheet().scoreTotal()))
            .orElse(null);

    if (winner != null) {
      System.out.println("\nThe winner is " + winner.getName() + " with " + winner.getScoresheet().scoreTotal() + " points!");
    }
  }
}