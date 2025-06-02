package yams.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavAgent {
    public void goTo(Stage stage, String fxml) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goTo (Node sourceNode, String fxml){
        Stage stage = (Stage) sourceNode.getScene().getWindow();
        goTo(stage, fxml);
    }

}
