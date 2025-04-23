package fr.uge.yams;

public record LargeStraight() implements Combination {
	@Override
	public int score(Board board) {

		return 40;
	}

	@Override
	public String toString() {

		return "Large Straight";
	}
}
