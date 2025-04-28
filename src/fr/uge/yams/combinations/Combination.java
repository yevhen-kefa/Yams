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
}
