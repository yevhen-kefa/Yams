package yams.vue;

import yams.model.game.Board;

import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayBoard(Board board) {
        System.out.println(board);
    }

    public String askInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
