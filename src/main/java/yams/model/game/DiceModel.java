package yams.model.game;

public interface DiceModel {
    int value();
    DiceModel reroll();
    DiceType getType();
    String getSpritePath();
    String name();
    String description();


}

