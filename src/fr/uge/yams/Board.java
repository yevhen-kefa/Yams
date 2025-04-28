package fr.uge.yams;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private final List<Dice> dices = new ArrayList<Dice>();

	public Board() {
		for (var i = 1; i <= 5; i++) {
			dices.add(new Dice());
		}
	}

  public List<Dice> dices() {
    return List.copyOf(dices);
  }

	@Override
	public String toString() {
		var builder = new StringBuilder();
		for (var i = 1; i <= 5; i++) {
			builder.append(dices.get(i - 1).toString());
		}
		builder.append("\n").append("-----------------\n");

		return builder.toString();
	}

	public void reroll(int pos) {
		if (pos < 1 || pos > 5) {
			throw new IllegalArgumentException();
		}
		dices.set(pos, new Dice());
	}
}
