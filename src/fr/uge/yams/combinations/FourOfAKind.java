package fr.uge.yams.combinations;

import fr.uge.yams.Board;

public record FourOfAKind() implements Combination {

  @Override
	public int score(Board board) {
		return 15;
	}

  @Override
	public String toString() {
		return "Three of A Kind";
	}

}
