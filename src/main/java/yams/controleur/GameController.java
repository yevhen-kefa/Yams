package yams.controleur;

import yams.model.combinations.CombinationModel;
import yams.model.game.Board;
import yams.model.players.Bot;
import yams.model.players.Human;
import yams.model.players.PlayerModel;
import yams.vue.ConsoleView;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Integer.parseInt;

public class GameController {
    private final ConsoleView view;

    public GameController(ConsoleView view) {
        this.view = view;
    }
    public GameController() {
        this.view = new ConsoleView(); // або null, або якийсь дефолт
    }

    private boolean handleReroll(PlayerModel player, Board board) {
        ArrayList<Integer> choice = new ArrayList<>();
        if (player.isBot()) {
            view.displayMessage("Bot is deciding which dice to reroll...");
            player.chooseReroll(0, choice);
            view.displayMessage("Bot will rerol dice: " + choice + "\n");
        }
        else {
            view.displayMessage("Please choose which dice to reroll by entering their index (1 to 5) or 0 to end the reroll.");
            do {
                try {
                    player.chooseReroll(parseInt(view.askInput("Enter dice index:")), choice);
                } catch (Exception e) {
                    view.displayMessage("Error : Invalid dice index. Please try again.");
                    continue;
                }
                System.out.println(choice);
            } while (choice.isEmpty()  || (choice.getLast() != 0 && choice.size() < 5));
        }

        if (choice.getFirst() == 0) {
            return false;

        }
        else {
            int j = 0;
            while (j < 5 && choice.get(j) != 0) {
                board.reroll(choice.get(j));
                j++;
            }
        }
        return true;
    }

    private CombinationModel handleCombination(PlayerModel player) {
        //demande la combinaison à jouer
        CombinationModel combinationChoice;
        if (player.isBot()) {
            view.displayMessage("Bot is choosing a combination to score...");
            combinationChoice = player.chooseCombination("");
            if (combinationChoice == null) {
                view.displayMessage("Bot has no more combinations to score. Game over!");
                return null;
            }
            else {
                view.displayMessage("Bot chooses combination: " + combinationChoice + "\n");
            }
        }
        else {
            view.displayMessage("Please choose a combination to score in your score sheet by entering its first letter.");
            view.displayMessage("Valid combinations are: ");
            for (CombinationModel combination : CombinationModel.values()) {
                view.displayMessage("- " + combination);
            }
            String input;
            while (true) {
                try {
                    input = view.askInput("").trim();
                    //Choose a combination from a list of valid combinations
                    combinationChoice = player.chooseCombination(input);
                    break;
                } catch (IllegalArgumentException e) {
                    view.displayMessage("Invalid combination. Please enter a valid combination.");
                }
            }
        }

        return combinationChoice;
    }

    public void playTurn(PlayerModel player, ConsoleView view) {

        //creer un nouveau plateau
        var board = new Board();

        //système de reroll des joueurs
        for (var i = 0; i < 3; i++) {
            //afficher le plateau avant le reroll
            view.displayBoard(board);

            if (!handleReroll(player, board)) {
                break;
            }

        }

        view.displayBoard(board);

        CombinationModel combinationChoice = handleCombination(player);
        //ajoute au score les points realises durant ce tour
        player.updateScore(combinationChoice, board);

        //afficher le score du joueur
        view.displayMessage(player.getScoresheet() + "\n" + "Total : " + player.getScoresheet().scoreTotal() + "\n" + "-----------------\n");
    }

    public ArrayList<PlayerModel> createParty() {
        ArrayList<PlayerModel> party = new ArrayList<>();
        int nbPlayers;
        while (true) {
            try {
                nbPlayers = Integer.parseInt(view.askInput("How many players? :"));
                break;
            } catch (Exception e) {
                view.displayMessage("Error : Invalid number of players. Please try again.");
            }
        }

        for (int i = 0; i < nbPlayers; i++) {
            while (true) {
                String type = view.askInput("What is Player " + (i + 1) + " ? H for Human or B for Bot :").trim();
                PlayerModel player;
                if (type.equalsIgnoreCase("B")) {
                    player = new Bot();
                } else if (type.equalsIgnoreCase("H")) {
                    player = new Human();
                } else {
                    view.displayMessage("Error : Invalid player type. Please try again.");
                    continue;
                }

                if (player.isBot()) {
                    view.displayMessage("Bot created!");
                    player.setName("Bot");
                }
                else {
                    String name;
                     do {
                         name = view.askInput("Please enter the player's name:").trim();
                         if (name.isEmpty()) {
                             view.displayMessage("Name cannot be empty. Please try again.");
                         }
                    } while (name.isEmpty());
                    player.setName(name);
                    view.displayMessage("Player created!");
                }
                party.add(player);
                break;
            }
        }
        return party;
    }

    public void playGame() {
        var party = createParty();
        view.displayMessage("Game started!");
        for (int i = 0; i < 7; i++) {
            view.displayMessage("----------------- Round " + (i + 1) + " -----------------");
            for (PlayerModel player : party) {
                playTurn(player, view);
            }
        }
        view.displayMessage("Game Over!\nFinal Scores:");
        for (PlayerModel player : party) {
            view.displayMessage(player.getName() + ": " + player.getScoresheet().scoreTotal() + " points");
        }
        party.stream()
                .max(Comparator.comparingInt(p -> p.getScoresheet().scoreTotal()))
                .ifPresent(winner -> view.displayMessage("\nThe winner is " + winner.getName() + " with " + winner.getScoresheet().scoreTotal() + " points!"));
    }
}