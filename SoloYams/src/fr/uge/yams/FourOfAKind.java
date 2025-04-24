package fr.uge.yams;

import java.util.ArrayList;
import java.util.HashSet;

public record FourOfAKind() implements Combination {
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

		return "Four of a Kind";
	}
	
	@Override
	public boolean amITheRightOne(ArrayList<Dice> dices) {
	    var valueCounts = new int[7];

	    for (Dice dice : dices) {
	        valueCounts[dice.value()]++;
	    }

	    for (int count : valueCounts) {
	        if (count >= 4) {
	            return true;
	        }
	    }
	    return false;
	}
}
