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
import yams.model.game.DiceFactory;
import yams.model.game.DiceModel;
import yams.model.game.DiceType;
import yams.model.players.PlayerModel;
import yams.vue.DiceView;

import java.io.IOException;
import java.util.List;

public class DiceCollectionController{
    NavAgent nav = new NavAgent();
    private List<PlayerModel> party;
    private PlayerModel currentPlayer;
    private int currentPlayerIndex = 0;
    @FXML
    private ComboBox<String> comboCombinations;





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
        // Якщо поточний гравець - людина, зберігаємо вибрані кубики
        if (!currentPlayer.isBot()) {
            List<DiceModel> selectedDice = HBoxPlay.getChildren().stream()
                    .filter(node -> node instanceof DiceView)
                    .map(node -> ((DiceView) node).getDice())
                    .toList();

            currentPlayer.getSet().clear();
            currentPlayer.getSet().addAll(selectedDice);
        } else {
            // Для бота додаємо 5 стандартних кубиків автоматично
            currentPlayer.getSet().clear();
            for (int i = 0; i < 5; i++) {
                currentPlayer.getSet().add(DiceFactory.createDice(DiceType.STANDARD));
            }
        }

        // Переходимо до наступного гравця
        currentPlayerIndex++;

        if (currentPlayerIndex < party.size()) {
            currentPlayer = party.get(currentPlayerIndex);
            playerNom.setText(currentPlayer.getName());
            HBoxPlay.getChildren().clear();
            HBoxList.getChildren().clear();

            if (currentPlayer.isBot()) {
                // Якщо наступний гравець - бот, одразу зробити вибір і перейти далі рекурсивно
                Play(null);  // Виклик самого себе без події, щоб одразу пройти бота
            } else {
                // Якщо людина - завантажуємо кубики для вибору
                loadAllDiceSamples();
                updatePlayButtonState();
            }
        } else {
            // Всі гравці вибрали — запускаємо гру
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameBoard.fxml"));
                Parent root = loader.load();

                BoardController controller = loader.getController();
                controller.setParty(party);

                Stage stage = (Stage) btnPlay.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void initialize() {
        loadAllDiceSamples();
        updatePlayButtonState();
    }

    private void loadAllDiceSamples() {
        addDiceSamples(DiceType.STANDARD, 5);
        addDiceSamples(DiceType.HOLY, 3);
        addDiceSamples(DiceType.DEMONIC, 3);
    }

    private void addDiceSamples(DiceType type, int count) {
        for (int i = 0; i < count; i++) {
            DiceModel dice = DiceFactory.createDice(type);
            DiceView diceView = new DiceView(dice, null); // Можна передати null або dummy controller, якщо потрібно
            diceView.setOnMouseClicked(event -> toggleCollectionPlacement(diceView));
            HBoxList.getChildren().add(diceView);
        }
    }

    private void toggleCollectionPlacement(DiceView die) {
        if (die.isPermanentlySaved()) {
            return;
        }

        if (HBoxPlay.getChildren().contains(die)) {
            HBoxPlay.getChildren().remove(die);
            HBoxList.getChildren().add(die);
            die.setSaved(false);
        } else {
            if (HBoxPlay.getChildren().size() >= 5) {
                return;
            }
            HBoxList.getChildren().remove(die);
            HBoxPlay.getChildren().add(die);
            die.setSaved(true);
        }

        updatePlayButtonState();
    }

    private void updatePlayButtonState() {
        btnPlay.setDisable(HBoxPlay.getChildren().size() != 5);
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        nav.goTo(stage, "/mode.fxml");
    }

    public void setParty(List<PlayerModel> party) {
        this.party = party;
        this.currentPlayerIndex = 0;
        this.currentPlayer = party.get(currentPlayerIndex);
        playerNom.setText(currentPlayer.getName());
    }
}
