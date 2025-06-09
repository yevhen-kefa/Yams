package yams.model.game;

import java.util.ArrayList;
import java.util.List;

public class Board {
  private final List<DiceModel> dices = new ArrayList<>();

  public Board(List<DiceModel> set) {
    for (DiceModel dice : set) {
      dices.add(dice);
    }
  }

  public List<DiceModel> dices() {
    return List.copyOf(dices);
  }

  public DiceModel getDice(int index) {
    return dices.get(index);
  }

  public List<DiceModel> getDice() {
    return dices;
  }

  public DiceModel reroll(int index, DiceType type) {
    DiceModel newDice = DiceFactory.createDice(type);
    dices.set(index, newDice);
    return newDice;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (DiceModel dice : dices) {
      builder.append(dice.toString());
    }
    builder.append("\n").append("-----------------\n");
    return builder.toString();
  }
}
