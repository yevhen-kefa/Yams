# Farky (Yams & Farkle Fusion) 🎲

**Farky** is a unique desktop strategy game developed in Java (JavaFX). It reimagines the classic **Yams (Yahtzee)** by infusing it with mechanics from **Farkle**, wrapped in a charming pixel-art interface.

> **Status:** Completed (University Project BUT1)
> **Team:** 2 Developers

![Project Banner](path/to/screenshot.png)
*(Insert game screenshot here)*

## 📖 Overview

The main objective of Farky was to move beyond a simple console-based dice game. We aimed to create an engaging graphical experience that teaches OOP concepts and UI design. Inspired by dice mini-games in titles like *Kingdom Come: Deliverance*, we implemented a "Special Dice" system that adds strategic depth and RNG manipulation to the classic rules.

The project is built strictly following the **MVC (Model-View-Controller)** architectural pattern.

### ✨ Key Features

* **Hybrid Gameplay:** Classic Yams rules enhanced with Farkle risk/reward mechanics.
* **Special Dice System:**
    *  *Standard Dice*
    *  *St. Uriel (Holy) Dice:* Low risk, consistency focused.
    *  *Demonic Dice:* High risk, high reward.
* **Dynamic Opponents:** Play Solo, PvP (Local), or Watch Mode (Bot vs. Bot).
* **Customization:** Dynamic player addition, custom names, and color selection.
* **Pixel Art UI:** Retro aesthetic with animated rolls and intuitive "hold" mechanics.

---

## 🏗 Technical Architecture

The application leverages **JavaFX** for the frontend and separates concerns using **MVC**.

### Core Modules

| Module | Description |
| :--- | :--- |
| **Model** | Game state, dice logic (`DiceFactory`), player data, and combination rules (`FullHouse`, `Yahtzee`). |
| **View** | Visual representation using FXML layouts, CSS styling, and View classes (`DiceView`). |
| **Controller** | Handles user input, scene switching, and game flow (`BoardController`, `NavAgent`). |

### Project Structure
```text
src/main/java/yams/
├── controleur/       # Interaction logic (Board, Menu, Navigation)
├── model/
│   ├── combinations/ # Scoring logic (Chance, Poker, etc.)
│   ├── game/         # Core mechanics (DiceFactory, GameAgent)
│   └── players/      # Entities (Bot, Human, Scoresheet)
└── vue/              # UI Components (FXML, CSS, View Classes)
```

## 🚀 Installation & Run

### Prerequisites:

* Java JDK 17+ (with JavaFX support)

* Maven / Gradle

1. Clone the repository.
2. Open the project in your preferred IDE (IntelliJ IDEA recommended).
3. Run the main entry point: src/main/java/yams/vue/Main.java. 


## 🔮 Future Roadmap

[ ] Progression System: Unlock better/rare dice types by winning matches.

[ ] Advanced AI: Implement smarter decision-making algorithms for Bots.

[ ] Network Play: Add support for online multiplayer.

👥 Authors

* Kefa Yevhen

* Nachnouchi Adam

> **Developed as part of the "Graphical Interface Development" course at IUT.**