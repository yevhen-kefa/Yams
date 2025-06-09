package yams.model.game;

public enum DiceType {
    STANDARD("Standard Dice", "/img/dice/standard/"),
    HOLY("St. Uriel Dice", "/img/dice/holy/"),
    DEMONIC("Demonic Dice", "/img/dice/demon/");

    private final String displayName;
    private final String spritePath;

    DiceType(String displayName, String spritePath) {
        this.displayName = displayName;
        this.spritePath = spritePath;
    }

    public String getDisplayName() { return displayName; }
    public String getSpritePath() { return spritePath; }
}