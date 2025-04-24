package fr.uge.yams;

import java.util.HashMap;
import java.util.Objects;

public class ScoreSheet {

	private final HashMap<Combination, Integer> scoreMap = new HashMap<>();

	public void updateScore(CombinationResult pattern, Board board) {
		Objects.requireNonNull(pattern);
		System.out.println(pattern);
		if (!scoreMap.containsKey(pattern.combination()) && pattern.success()) {
			scoreMap.put(pattern.combination(), pattern.combination().score(board));
		}
		else if (scoreMap.containsKey(pattern.combination()) | !pattern.success()) {
			scoreMap.put(pattern.combination(), 0);
			System.out.println("Your combination could not be added to the board, you thus sacrificed the " + pattern.combination() + " cell.");
		}
		
		
	}
	
	public HashMap<Combination, Integer> sendScoreMap() {
		return scoreMap;
	}

	public int scoreTotal() {

		return scoreMap.values().stream().mapToInt(Integer::intValue).sum();
	}

	@Override
	public String toString() {
		return scoreMap.toString();
	}

}
