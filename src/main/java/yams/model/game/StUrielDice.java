package yams.model.game;

public record StUrielDice(int value) implements DiceModel {

    public StUrielDice {
        if (value != 3) {
            throw new IllegalArgumentException("Value must be 3.");
        }
    }

    public StUrielDice() {
        this(3);
    }

    @Override
    public StUrielDice reroll() {
        return new StUrielDice();
    }

    @Override
    public DiceType getType() {
        return DiceType.HOLY;
    }

    @Override
    public String getSpritePath() {
        return getType().getSpritePath() + value + ".png";
    }

    @Override
    public String name() {
        return "Saint Uriel Dice";
    }

    @Override
    public String description() {
        return "A very particular dice that always shows 3. It was blessed by St Uriel HIMSELF.";
    }

    @Override
    public String toString() {
        return "✨✨✨✨✨\n" + "✨ " + value + " ✨\n" + "✨✨✨✨✨\n";
    }
}

