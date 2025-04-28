package fr.uge.yams.combinations;

import fr.uge.yams.Board;

/**
 * Represents a small straight combination in the game of Yams.
 * A small straight consists of four consecutive numbers (e.g., 1-2-3-4 or 2-3-4-5).
 * The score for a small straight is fixed at 30 points.
 */
public interface Combination {

  /**
   * Check if the combination is valid for the given board.
   *
   * @param board the board to check
   * @return true if the combination is valid, false otherwise
   */
  boolean valid(Board board);

  /**
   * Returns the score of the combination.
   *
   * @param board the board to check
   * @return the score of the combination
   */
	int score(Board board);

  /**
   * Creates a {@link Combination} instance based on the given label.
   * <p>
   * The label corresponds to a predefined type of combination:
   * <ul>
   *   <li>"C" - Chance</li>
   *   <li>"T" - Three of a Kind</li>
   *   <li>"F" - Four of a Kind</li>
   *   <li>"S" - Small Straight</li>
   *   <li>"L" - Large Straight</li>
   *   <li>"H" - Full House</li>
   * </ul>
   * 
   * @param label the label representing a specific combination
   * @return a {@link Combination} corresponding to the given label
   * @throws IllegalArgumentException if the label does not correspond to any known combination
   */
  public static Combination of(String label) {
    return switch (label) {
      case "C" -> new Chance();
      case "T" -> new ThreeOfAKind();
      case "F" -> new FourOfAKind();
      case "S" -> new SmallStraight();
      case "L" -> new LargeStraight();
      case "H" -> new FullHouse();
      default -> throw new IllegalArgumentException("Unexpected value: " + label);
    };
  }
}
