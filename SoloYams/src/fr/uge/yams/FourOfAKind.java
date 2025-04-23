package fr.uge.yams;

public record FourOfAKind() implements Combination {
	@Override
	public int score(Board board) {

		return 24;
	}

	@Override
	public String toString() {

		return "Four of a Kind";
	}
}
