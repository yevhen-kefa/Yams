package fr.uge.yams.combinations;

import fr.uge.yams.Board;
import fr.uge.yams.Dice;

public record Yahtzee() implements Combination {

  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (Dice dice : board.dices()) {
      count[dice.value()]++;
    }
    for (int i = 1; i < count.length; i++) {
      if (count[i] == 5) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int score(Board board) {
    int[] count = new int[7];
    for (Dice dice : board.dices()) {
      count[dice.value()]++;
    }
    for (int i = 1; i < count.length; i++) {
      if (count[i] == 5) {
        return 50;
      }
    }
    return 0;
  }

  @Override
  public String toString() {
    return "Yahtzee";
  }
}
