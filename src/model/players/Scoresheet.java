package model.players;

import model.combinations.CombinationModel;
import model.game.Board;

import java.util.HashMap;
import java.util.Objects;

public class Scoresheet {
  private final String playerName;
  private final HashMap<CombinationModel, Integer> scoreMap = new HashMap<>();

  public Scoresheet(String playerName) {
    this.playerName = Objects.requireNonNull(playerName);
  }

  public String name() {
    return playerName;
  }

  public void updateScore(CombinationModel pattern, Board board) {
    Objects.requireNonNull(pattern);
    if (scoreMap.containsKey(pattern)) {
      throw new IllegalArgumentException("already a score for this combination");
    }
    scoreMap.put(pattern, pattern.score(board));
  }

  public int scoreTotal() {
    return scoreMap.values().stream().mapToInt(Integer::intValue).sum();
  }

  public boolean containsCombination(CombinationModel pattern) {
    return scoreMap.containsKey(pattern);
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();
    builder.append(name()).append(System.lineSeparator());
    for (var entry : scoreMap.entrySet()) {
      builder.append(entry.getKey()).append(" : ").append(entry.getValue()).append(System.lineSeparator());
    }
    builder.append("Total : ").append(scoreTotal()).append(System.lineSeparator());
    return builder.toString();
  }
}
