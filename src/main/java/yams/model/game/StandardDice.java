package yams.model.game;

import java.util.Random;

public record StandardDice(int value) implements DiceModel {

  public StandardDice {
    if (value > 6 || value < 1) {
      throw new IllegalArgumentException("Value must be between 1 and 6.");
    }
  }

  public StandardDice() {
    this(new Random().nextInt(6) + 1);
  }

  @Override
  public StandardDice reroll() {
    return new StandardDice();
  }

  @Override
  public DiceType getType() {
    return DiceType.STANDARD;
  }

  @Override
  public String getSpritePath() {
    return getType().getSpritePath() + value + ".png";
  }

  @Override
  public String name() {
    return "Standard dice";
  }

  @Override
  public String description() {
    return "A very normal dice, averages to 3OU je ne sais pas comment faire mieux";
  }

  @Override
  public String toString() {
    return "-------\n" + "|  " + value + "  |\n" + "-------\n";
  }
}
