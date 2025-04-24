# 🧩 Document de Travail – Développement d’un Yams Solo

**Noms des étudiants :NACHNOUCHI Adam,KEFA Yevhen**  
**Date :**

---

## ✨ Tâche a. – Analyse du Code Existant

### 1. Démarche suivie  

_Pour explorer le code fourni sue l'E-learning, nous avons tout d'abord joue au jeu, et assaye differents petits tests pour en observer ses limites_

_Ensuite, nous avons explore la classe Main ou Yams,la classe main, et avons lu la suite d'instructuctions on nous basant sur les noms des methodes et des classes pour comprendre le conctionnement general._

_Apres quoi nous avons plonge individuellement dans chaque methode statique et classes afin de comprendre leurs roles plus en profondeur._
---

### 2. Liste des fonctionnalités déjà implémentées

> Listez ici ce qui fonctionne déjà dans le programme.

- [ ] Affichage des dés  
- [ ] Relance d’un dé  
- [ ] Passer la phase de relance 
- [ ] Entree d'un pseudonyme (partant du principe qu'un pseudo vide est valide)
- [ ] Combinaisons FullHouse et ThreeOfAKind
- [ ] Interface textuelle selon differents cas
- [ ] Affichage de la table de scores
- [ ] Ne pas pouvoir entrer deux fois la meme combinaisons
- [ ] Debut d'un nouveau round a la fin du precedent
- [ ] Fin du jeu quand le nombre de rounds assignes est atteint

---

### 3. Liste des manques

> Identifiez ce qui manque pour que le Yams soit complet et jouable correctement en solo.

- 🔲 Choisir Combien et quels des relancer
- 🔲 Presence de toutes les combinaisons du jeu original
- 🔲 Verification de la validite des entrees du joueur (format, antitriche, etc...)
- 🔲 Mauvais nombre de rounds
- 🔲 Mauvais nombre de relances
- 🔲 Possibilite de sacrifier une case
- 🔲 Verification des combinaisons entrees par le joueur (pas de doubles, existance)



---

## 🛠️ Tâche b. – Proposition de Solution

### 1. Cahier des charges simplifié

> Listez ici les fonctionnalités que vous comptez ajouter ou améliorer.
 
- [ ] Permettre de relancer plusieurs dés à la fois  
- [ ] Permettre de choisir quels des relancer
- [ ] Changer le nombre de rounds a 7
- [ ] Changer le nombre de relances a 2
- [ ] Verification de la validite des entrees du joueur
- [ ] Verification des combinaisons entree par le joueur
- [ ] Permettre de sacrifier une case
- [ ] Ajouter toutes les combinaisons du jeu
- [ ] Permettre de jouer en "guest" si le pseudo est vide


---

### 2. Choix techniques importants

> Expliquez ici brièvement comment vous comptez vous y prendre techniquement (nouvelle classe, refactorisation, etc.)

- Ajout d'un record par combinaison ajoutee
- Ajout de nouvelles methodes statiques
- Usage du polymorphisme
- Ajout de "try...catch" afin de detecter des entrees invalides et d'y repondre de maniere appropriee
- Modification de methodes existantes et creation de nouvelles methodes

---

### 3. Schéma simple de l’organisation du programme

> Ajoutez ici un schéma type UML ou une structure en texte brut pour montrer les classes et leurs relations.

```
Yams (main)
 ├── Board
 │    └── Dice x5
 ├── ScoreSheet
       └── CombinationResult
 └── Combination (interface)
       ├── FullHouse
       ├── ThreeOfAKind
       ├── Chance
       ├── FourOfAKind
       ├── SmallStraight
       ├── LargeStraight
       └── Yam
       
```

---

## 💻 Tâche c. – Programmation

> Listez ici les classes ou méthodes que vous avez créées ou modifiées pour répondre au cahier des charges.

- Création : 
- [ ] askReroll(Scanner scanner)
- [ ] askWhichDices(int choice, Scanner scanner)
- [ ] Chance.class
- [ ] FourOfAKind.class
- [ ] SmallStraight.class
- [ ] LargeStraight.class
- [ ] Yam.class
- [ ] CombinationResult.class

- Modification :
- [ ] main(String[] args) 
- [ ] parseCombination(String combinationName)
- [ ] parseCombination()
- [ ] scoreSheet.class
- [ ] Board.class

- Tests réalisés :
- [ ] Rentrer des valeures null ou invalides lors du choix des des et des relances
- [ ] Rentrer des combinaisons invalides ou null dans le choix des combinaisons
- [ ] Rentrer des combinaisons deja presentes mais pas appropriees dans le choix des combinaisons
- [ ] Rentrer des combinaisons deja presentes appropriees dans le choix des combinaisons
- [ ] Rentrer des combinaisons non presentes et pas appropriees dans le choix des combinaisons


---

## 📦 Tâche d. – Livraison

> Vérifiez que tout est prêt pour la livraison.

- [ ] Code fonctionnel  
- [ ] Partie ligne de commande jouable sur 13 tours  
- [ ] Combinaisons jouables au choix (et pas deux fois !)  
- [ ] Affichage du score total  
- [ ] Ce document rempli  
- [ ] … (à compléter)
---

## ✍️ Commentaires personnels 

> Vous pouvez expliquer ici ce que vous avez appris, aimé ou trouvé difficile dans l’exercice.

trouver des solutions elegantes et en accord avec les habitudes enseignees en TP et en TD etait enrichissant.