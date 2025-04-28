package fr.uge.yams.combinations;

import fr.uge.yams.Board;
import fr.uge.yams.Dice;

public record SmallStraight() implements Combination {

  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (Dice dice : board.dices()) {
      count[dice.value()]++;
    }
    return (count[1] > 0 && count[2] > 0 && count[3] > 0 && count[4] > 0)
        || (count[2] > 0 && count[3] > 0 && count[4] > 0 && count[5] > 0)
        || (count[3] > 0 && count[4] > 0 && count[5] > 0 && count[6] > 0);
  }

  @Override
  public int score(Board board) {
    if (!valid(board)) {
      return 0;
    }
    return 30;
  }

  @Override
  public String toString() {
    return "Small Straight";
  }
  
}
