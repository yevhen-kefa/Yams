package yams.controleur;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import yams.model.players.PlayerModel;

import java.io.IOException;
import java.util.List;

public class DiceCollectionController{
    NavAgent nav = new NavAgent();
    private List<PlayerModel> party;
    private PlayerModel currentPlayer;



    @FXML
    private Button btnPlay;

    @FXML
    private Button btnCancel;

    @FXML
    private Label playerNom;

    @FXML
    private HBox HBoxPlay;

    @FXML
    private HBox HBoxList;

    @FXML
    private void Play(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
            Parent root = loader.load();

            BoardController controller = loader.getController();
            controller.setParty(party);  // Передаємо той же список гравців

            Stage stage = (Stage) btnPlay.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        nav.goTo(stage, "/mode.fxml");
    }

    public void setParty(List<PlayerModel> party) {
        this.party = party;
        this.currentPlayer = party.get(0); // getFirst() для List не існує
        playerNom.setText(currentPlayer.getName()); // Оновлюємо label з ім'ям гравця
    }
}
