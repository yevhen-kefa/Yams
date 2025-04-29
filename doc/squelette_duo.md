# 🧩 Document de Travail – Développement d’un Yams Duo

**Noms des étudiants :**  
**Date :**

---

## ✨ Tâche a. – Analyse du Code Produit pour l’Objectif 1

### 1. Démarche suivie  
Après avoir terminé la première phase et commencé à mettre en œuvre plusieurs joueurs, nous avons rencontré des problèmes de structure et de qualité du code, ce qui nous a obligés à réécrire partiellement le code, complétant ainsi le code pour la deuxième partie. 


---

### 2. Liste des éléments réutilisables

> Indiquez ici ce qui peut rester inchangé ou être réutilisé tel quel dans la version duo.

- [ ] Classe `Dice`  
- [ ] Toutes les combinaisons de dés
- [ ] Classe `Board`  
- [ ] Classe `ScoreSheet`  peuvent être réécrits et révisés


---

### 3. Liste des manques par rapport à un Yams duo

> Listez ici les fonctionnalités ou éléments manquants pour avoir un jeu jouable à deux, humain ou IA.

- 🔲 Gestion de deux joueurs  
- 🔲 Possibilité de choisir entre les versions solo et duo
- 🔲 Choisir entre un ordinateur et un joueurs humains  
- 🔲 Possibilité de faire jouer un ordinateur
- 🔲 Enregistrement de deux joueurs en tant qu'individus distincts

---

## 🛠️ Tâche b. – Proposition de Solution

### 1. Cahier des charges simplifié

> Listez les fonctionnalités que vous comptez développer ou modifier.

- [ ] Permettre à deux joueurs de jouer à tour de rôle  
- [ ] Possibilité de choisir entre les versions solo et duo
- [ ] Choisir entre un ordinateur et un joueurs humains  
- [ ] Retrait de points pour les joueurs individuels



---

### 2. Choix techniques importants

> Décrivez ici vos grandes orientations de conception.

- Au lancement, le joueur choisit le mode de jeu, en fonction de son choix, il y a 2 modes, simple et avec un ordinateur ou avec une autre personne.
Si le jeu est joué avec un ordinateur, le joueur peut, pendant son tour, choisir au hasard les dés qu'il veut relancer, le nombre de fois et la combinaison qu'il veut jouer.
---

### 3. Schéma simple de l’organisation du programme

```
Yams (main)
 ├── chooseGameMode
 ├── init
 ├── friend
 ├── askReroll
 ├── askCombination
 ├── makeRandomMove
 └── main(String[])
      ├── Board
      │    └── Dice x5
      ├── ScoreSheet player
      ├── ScoreSheet friend 
      └── Combination
            ├── FullHouse
            ├── ThreeOfAKind
            ├── FourOfAKind
            ├── SmallStraight
            ├── LargeStraight
            ├── FullHouse
            ├── Yams
            └── Chance
```

---

## 💻 Tâche c. – Programmation

> Détaillez les ajouts/modifications apportés au code.

- Ajouts : 
- [ ] Sélection du mode de jeu 
- [ ] la possibilité de jouer à deux
- [ ] la possibilité de choisir entre un joueur et un ordinateur a été ajoutée
- [ ] la fonction de gestion de la file d'attente pour le joueur

- Modifications : 
- [ ] Système de notation pour chaque joueur
- [ ] cycle de la file d'attente du joueur
- [ ] Affichage du résultat dans la console prise en compte du mode de jeu
- [ ] Interaction avec le joueur au début du jeu
- [ ] Gestion des dés
---

## 📦 Tâche d. – Livraison

> Cochez ce qui a été fait.

 
- [ ] Partie jouable en ligne de commande à deux joueurs  
- [ ] Ce document rempli
- [ ] … (à compléter) 

---

## ✍️ Commentaire personnel 

> Vous pouvez écrire ici ce que vous avez appris, aimé ou trouvé difficile dans cette version duo.
