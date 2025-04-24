package fr.uge.yams;

import java.util.ArrayList;

public record Yam() implements Combination {
	@Override
	public int score(Board board) {

		return 50;
	}

	@Override
	public String toString() {

		return "Yam's";
	}
	
	@Override
	public boolean amITheRightOne(ArrayList<Dice> dices) {
		int i;
		
		for (i = 0; i < 4; i++) { 
			if (dices.get(i) != dices.get(i+1)) {
				return false;
			}
		}
		return true;
	}
}
