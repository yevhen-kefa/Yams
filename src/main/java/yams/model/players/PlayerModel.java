package yams.model.players;

import yams.model.combinations.CombinationModel;
import yams.model.game.Board;
import javafx.scene.paint.Color;


import java.util.ArrayList;

public interface PlayerModel {

    boolean isBot();

    //demande le nom du joueur
    void setName(String name);

    //recupere le nom du joueur
    String getName();

    //recupere la scoresheet du joueur
    Scoresheet getScoresheet();

    //ajoute au score des points
    default void updateScore(CombinationModel combination, Board board) {
        getScoresheet().updateScore(combination, board);
    }

    //adding color for players
    void setColor(Color color);
    Color getColor();

    //demande si on veut reroll, 0 pour arreter le reroll, 1-5 pour ajouter le de a la liste des reroll
    void chooseReroll(int number, ArrayList<Integer> choice);

    // demande la combination à jouer
    CombinationModel chooseCombination(String input);

}