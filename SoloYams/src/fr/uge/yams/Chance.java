package fr.uge.yams;

public record Chance() implements Combination {
	@Override
	public int score(Board board) {

		return 14;
	}

	@Override
	public String toString() {

		return "Chance";
	}
}
