 package fr.uge.yams;

import java.util.ArrayList;
import java.util.HashSet;

public record ThreeOfAKind() implements Combination {

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

		return "Three of A Kind";
	}
	
	@Override
	public boolean amITheRightOne(ArrayList<Dice> dices) {
	    var valueCounts = new int[7];

	    for (Dice dice : dices) {
	        valueCounts[dice.value()]++;
	    }

	    for (int count : valueCounts) {
	        if (count >= 3) {
	            return true;
	        }
	    }
	    return false;
	}

}
