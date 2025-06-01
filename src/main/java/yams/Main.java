package yams;

import yams.controller.GameController;
import yams.view.ConsoleView;

public class Main {
  public static void main(String[] args) {
    var view = new ConsoleView();
    var controller = new GameController(view);
    controller.playGame();
  }
}