package yams.model.combinations;

import yams.model.game.Board;
import yams.model.game.Dice;
import yams.model.game.DiceModel;

public record Yahtzee() implements CombinationModel {

  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (DiceModel dice : board.dices()) {
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
    for (DiceModel dice : board.dices()) {
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
