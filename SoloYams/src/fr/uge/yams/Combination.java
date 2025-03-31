package fr.uge.yams;

public interface Combination {
	
	default int score(Board board) {
		return 0;
	}

}
