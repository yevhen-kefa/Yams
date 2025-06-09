package yams.model.game;

public class DiceFactory {
    public static DiceModel createDice(DiceType type) {
        return switch (type) {
            case STANDARD -> new StandardDice();
            case HOLY -> new StUrielDice();
            case DEMONIC -> new DemonicDice();
        };
    }

    public static DiceModel createDice(DiceType type, int value) {
        return switch (type) {
            case STANDARD -> new StandardDice(value);
            case HOLY -> new StUrielDice(value);
            case DEMONIC -> new DemonicDice(value);
        };
    }
}
