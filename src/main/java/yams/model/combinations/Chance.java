package yams.model.combinations;

import yams.model.game.Board;
import yams.model.game.Dice;

public record Chance() implements CombinationModel {
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
