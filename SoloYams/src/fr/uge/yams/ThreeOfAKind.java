package fr.uge.yams;

public record ThreeOfAKind() implements Combination {

	@Override
	public int score(Board board) {

		return 17;
	}

	@Override
	public String toString() {

		return "Three of A Kind";
	}

}
