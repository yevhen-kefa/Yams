package yams.model.game;

public record DemonicDice(int value) implements DiceModel {

    public DemonicDice {
        if (value != 3) {
            throw new IllegalArgumentException("Value must be 6.");
        }
    }

    public DemonicDice() {
        this(6);
    }

    @Override
    public DemonicDice reroll() {
        return new DemonicDice();
    }

    @Override
    public DiceType getType() {
        return DiceType.DEMONIC;
    }

    @Override
    public String getSpritePath() {
        return getType().getSpritePath() + value + ".png";
    }

    @Override
    public String name() {
        return "Demonic Dice";
    }

    @Override
    public String description() {
        return "A dice trying its best to equal the number of the beast... always shows 6";
    }

    @Override
    public String toString() {
        return "👹👹👹👹👹\n" + "👹 " + value + " 👹\n" + "👹👹👹👹👹\n";
    }
}

