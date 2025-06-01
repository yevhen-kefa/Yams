package yams.model.combinations;

import yams.model.game.Board;
import yams.model.game.Dice;

public record ThreeOfAKind() implements CombinationModel {

  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (Dice dice : board.dices()) {
      count[dice.value()]++;
    }
    for (int i = 1; i < count.length; i++) {
      if (count[i] >= 3) {
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
    return "Three of a Kind";
  }
}
