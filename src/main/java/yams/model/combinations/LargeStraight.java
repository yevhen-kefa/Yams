package yams.model.combinations;

import yams.model.game.Board;
import yams.model.game.Dice;
import yams.model.game.DiceModel;

public record LargeStraight() implements CombinationModel {

  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (DiceModel dice : board.dices()) {
      count[dice.value()]++;
    }
    return (count[1] > 0 && count[2] > 0 && count[3] > 0 && count[4] > 0 && count[5] > 0)
        || (count[2] > 0 && count[3] > 0 && count[4] > 0 && count[5] > 0 && count[6] > 0);
  }

  @Override
  public int score(Board board) {
    if (!valid(board)) {
      return 0;
    }
    return 40;
  }

  @Override
  public String toString() {
    return "Large Straight";
  }
}