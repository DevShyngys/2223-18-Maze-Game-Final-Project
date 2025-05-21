
# 🧩 Maze Game – Final Project

A desktop maze game built with Java and JavaFX, showcasing clean architecture and multiple design patterns. Developed as a final project for the 2223-18 course.

## 🎮 Overview

Maze Game is a logic-based puzzle where the player navigates through a randomly generated maze. The game emphasizes modular design, maintainability, and the application of software engineering principles.

## 🛠️ Features

* **Random Maze Generation**: Each playthrough offers a unique maze layout.
* **Player Movement**: Navigate using keyboard controls (WASD or arrow keys).
* **Design Patterns Implemented**:

  * **Command**: Encapsulates player actions for flexibility and undo/redo functionality.
  * **Factory**: Centralized creation of maze components like walls and paths.
  * **Strategy**: Allows interchangeable movement algorithms (e.g., random vs. deterministic).
  * **State**: Manages game states such as Playing and Game Over.
  * **Observer**: Updates UI components in response to game state changes.
  * **Facade**: Provides a simplified interface to the game's complex subsystems.
* **JavaFX UI**: Interactive and responsive user interface.
* **Modular Architecture**: Organized codebase with clear separation of concerns.

## 📁 Project Structure

```
├── src/
│   ├── controller/        # Handles user input and game logic
│   ├── model/             # Core game entities and logic
│   ├── view/              # JavaFX UI components
│   ├── patterns/          # Implementations of design patterns
│   └── Main.java          # Application entry point
├── pom.xml                # Maven configuration file
└── README.md              # Project documentation
```

## 🚀 Getting Started

### Prerequisites

* Java 17 or higher
* Maven 3.6 or higher

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/DevShyngys/2223-18-Maze-Game-Final-Project.git
   cd 2223-18-Maze-Game-Final-Project
   ```

2. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn javafx:run
   ```

## 🧪 Testing

Unit tests are located in the `src/test/` directory. To execute tests:

```bash
mvn test
```

## 👥 Contributors

* **Galymzhan** – Game logic and maze generation
* **Shyngys** – UI development and design pattern implementation

## 📄 License

This project is licensed under the [MIT License](LICENSE).

