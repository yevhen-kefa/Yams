package fr.uge.yams;

import java.util.ArrayList;

public record Chance() implements Combination {
	@Override
	public int score(Board board) {

		int sum = 0;
	    for (Dice dice : board.sendYourself()) {
	        sum += dice.value();
	    }
	    return sum;
	}

	@Override
	public String toString() {

		return "Chance";
	}
	
	@Override
	public boolean amITheRightOne(ArrayList<Dice> dices) {
		return true;
	}
}
