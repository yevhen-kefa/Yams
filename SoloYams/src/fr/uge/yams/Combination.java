package fr.uge.yams;

import java.util.ArrayList;

public interface Combination {
	
	default int score(Board board) {
		return 0;
	}
	
	public boolean amITheRightOne(ArrayList<Dice> dices);
	
}
