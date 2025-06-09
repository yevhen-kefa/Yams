package yams.vue;

import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class PlayerNameView {
    public static String promptPlayerName(int playerNumber) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter name for Player " + playerNumber + ":");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }
}

