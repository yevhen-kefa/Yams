package model.combinations;

import model.game.Board;

import java.util.List;
import java.util.Random;

/* 
Représente une petite quinte dans le jeu de Yams.
Une petite suite est composée de quatre nombres consécutifs (par exemple, 1-2-3-4 ou 2-3-4-5).
Le score d'une petite suite est fixé à 30 points.
*/
public interface CombinationModel {

  /*
  Vérifier si la combinaison est valable pour la carte donnée.
   */
  boolean valid(Board board);

  /*
   Renvoie le score de la combinaison.
   */
	int score(Board board);

  
  static CombinationModel of(String label) {
    return switch (label) {
      case "C" -> new Chance();
      case "T" -> new ThreeOfAKind();
      case "F" -> new FourOfAKind();
      case "S" -> new SmallStraight();
      case "L" -> new LargeStraight();
      case "H" -> new FullHouse();
      case "Y" -> new Yahtzee();
      default -> throw new IllegalArgumentException("Unexpected value: " + label);
    };
  }

  static List<CombinationModel> values() {
      return List.of(
        new Chance(),
        new ThreeOfAKind(),
        new FourOfAKind(),
        new SmallStraight(),
        new LargeStraight(),
        new FullHouse(),
        new Yahtzee()
      );
  }

  /*
  Crée une instance de combinaison aléatoire.
  La méthode sélectionne au hasard l'une des combinaisons prédéfinies.
  Retourne une combinaison aléatoire
   */
  static CombinationModel of() {
    var pick = new Random().nextInt(7);
    return switch (pick) {
        case 0 -> new Chance();
        case 1 -> new ThreeOfAKind();
        case 2 -> new FourOfAKind();
        case 3 -> new SmallStraight();
        case 4 -> new LargeStraight();
        case 5 -> new FullHouse();
        case 6 -> new Yahtzee();
        default -> throw new AssertionError("Unexpected random value: " + pick);
    };
  }
}
