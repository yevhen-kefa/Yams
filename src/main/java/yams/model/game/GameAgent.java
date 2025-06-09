package yams.model.game;

import yams.model.players.PlayerModel;
import java.util.List;

public class GameAgent {
    private static final int MAX_ROUNDS = 7;

    public static boolean isGameFinished(int currentRound) {
        return currentRound > MAX_ROUNDS;
    }

    public static PlayerModel findWinner(List<PlayerModel> players) {
        PlayerModel winner = players.get(0);
        int maxScore = winner.getScoresheet().scoreTotal();

        for (PlayerModel player : players) {
            int score = player.getScoresheet().scoreTotal();
            if (score > maxScore) {
                maxScore = score;
                winner = player;
            }
        }
        return winner;
    }

    public static int getNextPlayerIndex(List<PlayerModel> players, PlayerModel currentPlayer) {
        int currentIndex = players.indexOf(currentPlayer);
        return (currentIndex + 1) % players.size();
    }
}
