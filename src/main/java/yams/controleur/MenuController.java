package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import yams.model.NavAgent;

public class MenuController {
    NavAgent nav = new NavAgent();

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnQuit;

    @FXML
    void Play(ActionEvent event) {
        nav.goTo(btnPlay, "/mode.fxml");
    }

    @FXML
    void Quit(ActionEvent event) {
        System.exit(0);
    }
}
