package yams.model.players;

import yams.model.combinations.CombinationModel;
import yams.model.game.Board;
import javafx.scene.paint.Color;
import yams.model.game.DiceFactory;
import yams.model.game.DiceModel;


import java.util.ArrayList;
import java.util.List;

import static yams.model.game.DiceType.*;

public interface PlayerModel {

    ArrayList<DiceModel> diceSet = new ArrayList<>();

    default List<DiceModel> getSet() {
        diceSet.add(DiceFactory.createDice(STANDARD));
        diceSet.add(DiceFactory.createDice(DEMONIC));
        diceSet.add(DiceFactory.createDice(STANDARD));
        diceSet.add(DiceFactory.createDice(STANDARD));
        diceSet.add(DiceFactory.createDice(STANDARD));
        diceSet.add(DiceFactory.createDice(STANDARD));
        return diceSet;
    }

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
    String chooseCombination();

}