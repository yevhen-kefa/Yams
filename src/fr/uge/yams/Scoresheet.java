package fr.uge.yams;

import java.util.HashMap;
import java.util.Objects;

import fr.uge.yams.combinations.Combination;

public class Scoresheet {
  private final String playerName;
  private final HashMap<Combination, Integer> scoreMap = new HashMap<>();

  public Scoresheet(String playerName) {
    this.playerName = Objects.requireNonNull(playerName);
  }

  public String name() {
    return playerName;
  }

  public void updateScore(Combination pattern, Board board) {
    Objects.requireNonNull(pattern);
    if (scoreMap.containsKey(pattern)) {
      throw new IllegalArgumentException("already a score for this combination");
    }
    scoreMap.put(pattern, pattern.score(board));
  }

  public int scoreTotal() {
    return scoreMap.values().stream().mapToInt(Integer::intValue).sum();
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
