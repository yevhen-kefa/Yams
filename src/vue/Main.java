package vue;
import controleur.GameController;
import vue.ConsoleView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

 public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/menu.fxml"));
        StackPane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Yams Game");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); 
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
