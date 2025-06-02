package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import yams.model.NavAgent;

public class SoloController {
    NavAgent nav = new NavAgent();

    @FXML
    private Button btnReturn;
    @FXML
    private Button btnPlay;
    @FXML
    private TextField inputBots;

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
