package fr.uge.yams.combinations;

import fr.uge.yams.Board;
import fr.uge.yams.Dice;

public record FourOfAKind() implements Combination {
  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (Dice dice : board.dices()) {
      count[dice.value()]++;
    }
    for (int i = 1; i < count.length; i++) {
      if (count[i] >= 4) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int score(Board board) {
    if (!valid(board)) {
      return 0;
    }
    int sum = 0;
    for (Dice dice : board.dices()) {
      sum += dice.value();
    }
    return sum;
  }

  @Override
  public String toString() {
    return "Three of A Kind";
  }
}
