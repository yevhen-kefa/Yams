package fr.uge.yams;

import java.util.ArrayList;
import java.util.HashSet;

public record LargeStraight() implements Combination {
	@Override
	public int score(Board board) {

		return 40;
	}

	@Override
	public String toString() {

		return "Large Straight";
	}
	
	@Override
	public boolean amITheRightOne(ArrayList<Dice> dices) {
		HashSet<Integer> numset = new HashSet<Integer>();
		int i;
		
		for (i = 0; i < 5; i++) { 
			if (!numset.contains(dices.get(i).value())) {
				numset.add(dices.get(i).value());
			}
			
			else {
				return false;
			}
		}
		if(numset.contains(1) && numset.contains(6)) {
			return false;
		}
		return true;
	}
}
