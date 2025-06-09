package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ModeController {
    NavAgent nav = new NavAgent();

    @FXML
    private Button btnSolo;
    @FXML
    private Button btnFriend;
    @FXML
    private Button btnReturn;

    @FXML
    void Solo(ActionEvent event) {
        nav.goTo(btnSolo, "/solo.fxml");
    }
    @FXML
    void Friend(ActionEvent event) {
        nav.goTo(btnFriend, "/friend.fxml");
    }

    @FXML
    void Return(ActionEvent event) {
        nav.goTo(btnReturn, "/menu.fxml");
    }
}
