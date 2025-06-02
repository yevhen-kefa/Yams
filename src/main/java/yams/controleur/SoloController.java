package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import yams.model.NavAgent;

import java.util.function.UnaryOperator;

public class SoloController {
    NavAgent nav = new NavAgent();

    @FXML
    private Button btnReturn;
    @FXML
    private Button btnPlay;
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
        String numberOfBots = inputBots.getText();
        Stage stage = (Stage) btnPlay.getScene().getWindow();
        nav.goTo(stage, "/gameBoard.fxml");
    }
}
