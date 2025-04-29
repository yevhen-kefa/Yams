package fr.uge.yams.combinations;

import java.util.Random;

import fr.uge.yams.Board;

/* 
Représente une petite quinte dans le jeu de Yams.
Une petite suite est composée de quatre nombres consécutifs (par exemple, 1-2-3-4 ou 2-3-4-5).
Le score d'une petite suite est fixé à 30 points.
*/
public interface Combination {

  /*
  Vérifier si la combinaison est valable pour la carte donnée.
   */
  boolean valid(Board board);

  /*
   Renvoie le score de la combinaison.
   */
	int score(Board board);

  
  public static Combination of(String label) {
    return switch (label) {
      case "C" -> new Chance();
      case "T" -> new ThreeOfAKind();
      case "F" -> new FourOfAKind();
      case "S" -> new SmallStraight();
      case "L" -> new LargeStraight();
      case "H" -> new FullHouse();
      default -> throw new IllegalArgumentException("Unexpected value: " + label);
    };
  }

  /*
  Crée une instance de combinaison aléatoire.
  La méthode sélectionne au hasard l'une des combinaisons prédéfinies.
  Retourne une combinaison aléatoire
   */
  public static Combination of() {
    var pick = new Random().nextInt(6);
    return switch (pick) {
        case 0 -> new Chance();
        case 1 -> new ThreeOfAKind();
        case 2 -> new FourOfAKind();
        case 3 -> new SmallStraight();
        case 4 -> new LargeStraight();
        case 5 -> new FullHouse();
        default -> throw new AssertionError("Unexpected random value: " + pick);
    };
  }
}
