package fr.uge.yams;

import fr.uge.yams.combinations.Combination;

import java.util.ArrayList;
import java.util.Scanner;

public interface Player {

    //demande le nom du joueur
    void askName();

    //recupere le nom du joueur
    String getName();

    //check si le joueur est un bot
    default boolean isBot() {
        return getName().equals("Bot");
    }

    //recupere la scoresheet du joueur
    Scoresheet getScoresheet();

    //ajoute au score des points
    default void updateScore(Combination combination, Board board) {
        getScoresheet().updateScore(combination, board);
    }

    //demande si on veut reroll, 0 pour arreter le reroll, 1-5 pour ajouter le de a la liste des reroll
    ArrayList<Integer> askReroll(Scanner scanner);

    // demande la combination à jouer
    Combination askCombination(Scanner scanner);

    // joue un tour
    default void playTurn() {

        //creer un nouveau plateau
        var board = new Board();

        //creer le scanner d'entrées du joueur Humain
        var scanner = new Scanner(System.in);

        //afficher le plateau
        System.out.println(board);

        //système de reroll des joueurs
        for (var i = 0; i < 3; i++) {

            var choice = askReroll(scanner);

            if (choice.getFirst() == 0) {
                break;

            } else {
                int j = 0;
                while (choice.get(j) != 0) {
                    board.reroll(choice.get(j));
                    j++;
                }
            }

        }

        //système de choix de la combinaison
        if (!isBot()) {
            System.out.println("Enter combination to score in your score sheet");
        }

        //demande la combinaison à jouer
        var combinationChoice = askCombination(scanner);

        //ajoute au score les points realises durant ce tour
        updateScore(combinationChoice, board);

        //afficher le score du joueur
        System.out.println(getScoresheet() + "\n" + "Total : " + getScoresheet().scoreTotal() + "\n" + "-----------------\n");
    }
}
