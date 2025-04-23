package fr.uge.yams;

public record Yam() implements Combination {
	@Override
	public int score(Board board) {

		return 50;
	}

	@Override
	public String toString() {

		return "Yam's";
	}
}
