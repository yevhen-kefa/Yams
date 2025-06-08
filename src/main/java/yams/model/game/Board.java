package yams.model.game;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private final List<Dice> dices = new ArrayList<>();

  public Board() {
    for (int i = 0; i < 5; i++) {
      dices.add(new Dice());
    }
  }

  public List<Dice> dices() {
    return List.copyOf(dices);
  }

  public Dice getDice(int index) {
    return dices.get(index);
  }

  public List<Dice> getDice() {
    return dices;
  }

  public Dice reroll(int index) {
    Dice newDice = dices.get(index).reroll();
    dices.set(index, newDice);
    return newDice;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Dice dice : dices) {
      builder.append(dice.toString());
    }
    builder.append("\n").append("-----------------\n");
    return builder.toString();
  }
}

