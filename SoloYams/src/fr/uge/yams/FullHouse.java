package fr.uge.yams;

import java.util.ArrayList;
import java.util.HashSet;

public record FullHouse() implements Combination {

	@Override
	public int score(Board board) {

		return 25;
	}

	@Override
	public String toString() {

		return "Full House";
	}
	
	@Override
	public boolean amITheRightOne(ArrayList<Dice> dices) {
	    int[] counts = new int[7];

	    for (Dice dice : dices) {
	        counts[dice.value()]++;
	    }

	    boolean hasThree = false;
	    boolean hasTwo = false;

	    for (int i = 1; i <= 6; i++) {
	        if (counts[i] == 3) {
	            hasThree = true;
	        } else if (counts[i] == 2) {
	            hasTwo = true;
	        }
	    }

	    return hasThree && hasTwo;
	}
}
