package model.combinations;

import model.game.Board;

public record FullHouse() implements CombinationModel {

  @Override
  public boolean valid(Board board) {
    int[] count = new int[7];
    for (var dice : board.dices()) {
      count[dice.value()]++;
    }
    boolean three = false;
    boolean pair = false;
    for (int i = 1; i < count.length; i++) {
      if (count[i] == 3) {
        three = true;
      } else if (count[i] == 2) {
        pair = true;
      }
    }
    return three && pair;
  }

  @Override
  public int score(Board board) {
    if (!valid(board)) {
      return 0;
    }
    return 25;
  }

  @Override
  public String toString() {
    return "Full House";
  }
}
