package fr.uge.yams;

import java.util.ArrayList;

public class Board {
	private final ArrayList<Dice> fiveDice = new ArrayList<Dice>();

	public Board() {
		for (var i = 1; i <= 5; i++) {
			fiveDice.add(new Dice());
		}
	}

	@Override
	public String toString() {
		var builder = new StringBuilder();
		for (var i = 1; i <= 5; i++) {
			builder.append(fiveDice.get(i - 1).toString());
		}
		builder.append("\n").append("-----------------\n");

		return builder.toString();
	}

	public void reroll(int pos) {
		if (pos < 1 || pos > 5) {
			throw new IllegalArgumentException();
		}
		fiveDice.set(pos - 1, new Dice());
	}
}
