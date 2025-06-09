package yams.vue;

import javafx.scene.control.Alert;
import yams.model.players.PlayerModel;
import java.util.List;

public class GameResultView {
    public static void showGameResults(List<PlayerModel> players, PlayerModel winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Finished!");

        StringBuilder results = new StringBuilder("Final Scores:\n\n");
        for (PlayerModel player : players) {
            results.append(player.getName())
                    .append(": ")
                    .append(player.getScoresheet().scoreTotal())
                    .append(" points\n");
        }
        results.append("\nWinner: ")
                .append(winner.getName())
                .append(" with ")
                .append(winner.getScoresheet().scoreTotal())
                .append(" points!");

        alert.setContentText(results.toString());
        alert.showAndWait();
    }
}
