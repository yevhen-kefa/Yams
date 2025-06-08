package yams.vue;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import yams.model.combinations.CombinationModel;

import java.util.Optional;

public class CombinationSelectionView {
    public static String chooseCombination() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Combination");
        dialog.setHeaderText("Choose a combination \n (C)hance, (T)hree of a Kind, (F)our of a Kind, (S)mall Straight, (L)arge Straight, (H)ouse, (Y)ahtzee");

        for (CombinationModel combination : CombinationModel.values()) {
            ButtonType buttonType = new ButtonType(String.valueOf(combination.toString().charAt(0)));
            dialog.getDialogPane().getButtonTypes().add(buttonType);
        }
        // Gérer le cas où l'utilisateur ferme la boîte de dialogue
        Optional<ButtonType> result = dialog.showAndWait();
        return result.isPresent() ? result.get().getText() : "";
    }
}
