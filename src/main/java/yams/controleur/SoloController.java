package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import yams.model.players.Bot;
import yams.model.players.Human;
import yams.model.players.PlayerModel;
import yams.vue.ErrorView;
import yams.vue.PlayerNameView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class SoloController {
    NavAgent nav = new NavAgent();

    @FXML
    private Button btnReturn;
    @FXML private TextField inputBots;
    @FXML private Button btnPlay;

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

        inputBots.setTextFormatter(new TextFormatter<>(filter));

        btnPlay.disableProperty().bind(inputBots.textProperty().isEmpty());
    }

    @FXML
    void Return(ActionEvent event) {
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        nav.goTo(stage, "/mode.fxml");
    }

    @FXML
    void Play(ActionEvent event) {
        try {
            int numberOfBots = Integer.parseInt(inputBots.getText());

            List<PlayerModel> players = new ArrayList<>();

            // List of up to 10 unique colors
            List<Color> playerColors = List.of(
                    Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE,
                    Color.BROWN, Color.DARKCYAN, Color.DARKMAGENTA, Color.GOLD, Color.OLIVE
            );


            String name = promptPlayerName(1);
            if (name == null || name.trim().isEmpty()) {
                ErrorView.showError("Invalid name","Player " + (1) + "'s name cannot be empty.");
                return;
            }

            Human human = new Human();
            human.setName(name.trim());
            human.setColor(playerColors.get(1));
            players.add(human);

            // add bots
            for (int i = 0; i < numberOfBots; i++) {
                Bot bot = new Bot();
                bot.setName("Bot" + (i + 1));
                int colorIndex = i + 1; // Shift by 1 since 0 is taken by Player1
                if (colorIndex < playerColors.size()) {
                    bot.setColor(playerColors.get(colorIndex));
                } else {
                    bot.setColor(Color.GRAY); // fallback color if > 10 players
                }
                players.add(bot);
            }

            // Loading the playing field
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
            Parent root = loader.load();

            BoardController controller = loader.getController();
            controller.setParty(players);

            Stage stage = (Stage) btnPlay.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Обробка помилок
        }
    }
    private String promptPlayerName(int playerNumber) {
        return PlayerNameView.promptPlayerName(playerNumber);
    }
}
