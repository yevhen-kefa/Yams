package yams.model.game;

import java.util.Random;

public record Dice(int value) implements DiceModel {

  public Dice {
    if (value > 6 || value < 1) {
      throw new IllegalArgumentException("Value must be between 1 and 6.");
    }
  }

  public Dice() {
    this(new Random().nextInt(6) + 1);
  }

  @Override
  public Dice reroll() {
    return new Dice();
  }

  @Override
  public String toString() {
    return "-------\n" + "|  " + value + "  |\n" + "-------\n";
  }
}
