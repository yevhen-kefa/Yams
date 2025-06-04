package yams.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yams.model.NavAgent;
import yams.model.game.Board;
import yams.model.game.Dice;
import yams.vue.DiceView;

import java.util.ArrayList;
import java.util.List;

public class BoardController {
    // Agent de navigation pour changer de scène
    NavAgent nav = new NavAgent();

    // Bouton pour relancer les dés
    @FXML
    private Button btnReroll;

    // Bouton pour revenir à l'écran précédent
    @FXML
    private Button btnReturn;

    // Label pour afficher le nom du joueur
    @FXML
    private Label nomPlayer;

    // Label pour afficher le score du joueur
    @FXML
    private Label scrPlayer;

    // Paneau où les dés seront affichés
    @FXML
    private AnchorPane anchorDice;

    // Liste des vues des dés (DiceView)
    private List<DiceView> diceViews = new ArrayList<>();

    // Modèle de la planche de jeu
    private Board board;

    // Méthode d'initialisation appelée automatiquement après le chargement du FXML
    @FXML
    public void initialize() {
        board = new Board();

        // Exécuter le code après que la scène soit complètement chargée
        javafx.application.Platform.runLater(() -> {
            double maxWidth = anchorDice.getWidth() - 80;   // Largeur maximale
            double maxHeight = anchorDice.getHeight() - 80; // Hauteur maximale
            double diceSize = 80; // Taille supposée du dé
            double diceCollision = 150;

            for (int i = 0; i < 5; i++) {
                Dice dice = board.getDice(i);
                DiceView diceView = new DiceView(dice);

                boolean overlap;
                int attempts = 0;
                double randomX = 0;
                double randomY = 0;

                // Générer une position sans chevauchement
                do {
                    overlap = false;
                    randomX = Math.random() * maxWidth;
                    randomY = Math.random() * maxHeight;

                    for (DiceView existing : diceViews) {
                        double x1 = existing.getLayoutX();
                        double y1 = existing.getLayoutY();
                        double x2 = randomX;
                        double y2 = randomY;

                        if (x1 < x2 + diceCollision && x1 + diceCollision > x2 &&
                                y1 < y2 + diceCollision && y1 + diceCollision > y2) {
                            overlap = true;
                            break;
                        }
                    }
                    attempts++;
                } while (overlap && attempts < 100);

                // Appliquer une rotation aléatoire entre -30 et +30 degrés
                double randomRotation = -30 + Math.random() * 60;
                diceView.setRotate(randomRotation);

                // Positionner et ajouter à l'interface
                diceView.setLayoutX(randomX);
                diceView.setLayoutY(randomY);
                diceViews.add(diceView);
                anchorDice.getChildren().add(diceView);
            }
        });
    }


    // Action liée au bouton de relance des dés
    @FXML
    void btnReroll(ActionEvent event) {
        double maxWidth = anchorDice.getWidth() - 80;
        double maxHeight = anchorDice.getHeight() - 80;
        double diceSize = 80;

        for (int i = 0; i < 5; i++) {
            // Relancer le dé côté modèle
            Dice newDice = board.reroll(i);
            DiceView diceView = diceViews.get(i);

            // Mettre à jour la valeur du dé dans la vue
            diceView.updateDice(newDice);

            // Générer une nouvelle position aléatoire sans chevauchement
            boolean overlap;
            int attempts = 0;
            double randomX = 0;
            double randomY = 0;

            do {
                overlap = false;
                randomX = Math.random() * maxWidth;
                randomY = Math.random() * maxHeight;

                for (int j = 0; j < diceViews.size(); j++) {
                    if (j == i) continue; // Ne pas comparer avec soi-même
                    DiceView other = diceViews.get(j);

                    double x1 = other.getLayoutX();
                    double y1 = other.getLayoutY();
                    double x2 = randomX;
                    double y2 = randomY;

                    if (x1 < x2 + diceSize && x1 + diceSize > x2 &&
                            y1 < y2 + diceSize && y1 + diceSize > y2) {
                        overlap = true;
                        break;
                    }
                }
                attempts++;
            } while (overlap && attempts < 100);
            // Appliquer une rotation aléatoire entre -30 et +30 degrés
            double randomRotation = -30 + Math.random() * 60;
            diceView.setRotate(randomRotation);

            // Appliquer la nouvelle position
            diceView.setLayoutX(randomX);
            diceView.setLayoutY(randomY);
        }
    }


    // Action liée au bouton retour, pour retourner à l'écran de sélection de mode
    @FXML
    void btnReturn(ActionEvent event) {
        Stage stage = (Stage) btnReturn.getScene().getWindow(); // Récupérer la fenêtre actuelle
        nav.goTo(stage, "/mode.fxml");                          // Naviguer vers l'écran mode
    }
}
