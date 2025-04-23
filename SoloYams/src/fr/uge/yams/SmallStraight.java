package fr.uge.yams;

public record SmallStraight() implements Combination {
	@Override
	public int score(Board board) {

		return 30;
	}

	@Override
	public String toString() {

		return "Small Straight";
	}
}
