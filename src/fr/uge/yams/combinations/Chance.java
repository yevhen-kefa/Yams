package fr.uge.yams.combinations;

import fr.uge.yams.Board;
import fr.uge.yams.Dice;

public record Chance() implements Combination {
  @Override
  public boolean valid(Board board) {
    return true;
  }

  @Override
  public int score(Board board) {
    int sum = 0;
    for (Dice dice : board.dices()) {
      sum += dice.value();
    }
    return sum;
  }

  @Override
  public String toString() {
    return "Chance";
  }
}
