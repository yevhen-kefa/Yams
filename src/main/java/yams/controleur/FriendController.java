
package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import yams.model.NavAgent;
import yams.model.players.Bot;
import yams.model.players.Human;
import yams.model.players.PlayerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class FriendController {
    NavAgent nav = new NavAgent();

    @FXML
    private Button btnReturn;
    @FXML
    private Button btnPlay;
    @FXML
    private TextField inputFriends;
    @FXML
    private TextField inputBots;

    @FXML
    void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                try {
                    if (newText.isEmpty()) return change;
                    int value = Integer.parseInt(newText);
                    if (value <= 10) {
                        return change;
                    }
                } catch (NumberFormatException e) {
                }
            }
            return null;
        };

        inputFriends.setTextFormatter(new TextFormatter<>(filter));
        btnPlay.disableProperty().bind(inputBots.textProperty().isEmpty());
    }


    @FXML
    void Return() {
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        nav.goTo(stage, "/mode.fxml");
    }

    @FXML
    void Play() {
        try {
            int numberOfFriends = Integer.parseInt(inputFriends.getText());
            int numberOfBots = Integer.parseInt(inputBots.getText());
            int totalPlayers = numberOfFriends + numberOfBots;

            List<PlayerModel> players = new ArrayList<>();

            // List of up to 10 unique colors
            List<Color> playerColors = List.of(
                    Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE,
                     Color.BROWN, Color.DARKCYAN, Color.DARKMAGENTA, Color.GOLD, Color.OLIVE
            );

            // Ask names for human players
            for (int i = 0; i < numberOfFriends; i++) {
                String name = promptPlayerName(i + 1);
                if (name == null || name.trim().isEmpty()) {
                    showError("Player " + (i + 1) + "'s name cannot be empty.");
                    return;
                }

                Human human = new Human();
                human.setName(name.trim());
                human.setColor(playerColors.get(i));
                players.add(human);
            }

            // Bots
            for (int i = 0; i < numberOfBots; i++) {
                Bot bot = new Bot();
                bot.setName("Bot" + (i + 1));
                bot.setColor(playerColors.get(numberOfFriends + i));
                players.add(bot);
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
            Parent root = loader.load();

            BoardController controller = loader.getController();
            controller.setParty(players);

            Stage stage = (Stage) btnPlay.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException | NumberFormatException | IndexOutOfBoundsException e) {
            e.printStackTrace(); // Error handling
        }
    }

    private String promptPlayerName(int playerNumber) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Player Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter name for Player " + playerNumber + ":");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}