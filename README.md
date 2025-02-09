# Hotel Plaza

This repository contains a Java-based text adventure game set in a hotel environment. Players navigate through various rooms, interact with items and animals, and execute commands to progress through the game.

## Features

- Navigate through different rooms within the hotel.
- Interact with various items and animals.
- Execute commands to perform actions and explore the environment.
- Text-based interface providing descriptive game play.

## Prerequisites

- Java Development Kit (JDK) installed on your system.
- An Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse (optional but recommended).

## Installation

1. **Clone this repository:**

   ```bash
   git clone https://github.com/GurvirSingh04/Hotel-Plaza.git
   cd Hotel-Plaza
   ```

2. **Compile the application:**

   If you're using an IDE, open the project and build it. If you prefer the command line:

   ```bash
   javac *.java
   ```

3. **Run the application:**

   ```bash
   java Game
   ```

## File Structure

- `Animal.java` - Defines the Animal class and its behaviors.
- `Command.java` - Handles player commands and their execution.
- `Game.java` - The main class that initializes and runs the game.
- `Item.java` - Represents items that players can interact with.
- `Parser.java` - Parses player input to understand commands.
- `Player.java` - Manages player-specific information and actions.
- `Room.java` - Defines the different rooms within the hotel and their connections.

## How It Works

- The game initializes by setting up rooms, items, and animals within the hotel.
- Players input text commands to navigate and interact with the environment.
- The parser interprets these commands and invokes the appropriate methods.
- The game provides textual feedback based on player actions and game state.
