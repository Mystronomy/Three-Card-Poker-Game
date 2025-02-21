## Overview

This project is a networked implementation of Three Card Poker, where players connect to a server via client applications to play the game in real-time. The game logic, including card dealing, wagers, and winnings calculations, is managed by the server, while each client plays independently. The project focuses on event-driven programming and networking using Java Sockets, ensuring a smooth multiplayer experience.

## Features

- Multiplayer Support – Multiple players can connect and play separate hands of poker against the dealer.

- JavaFX GUI – A fully interactive client and server interface with custom scenes and styling.

- Java Sockets for Networking – Server handles multiple client connections, each on a separate thread.

- Serialized Data Transmission – PokerInfo class enables structured communication between server and clients.

- Game Logic on Server – All calculations, card shuffling, and win/loss determinations are processed on the server.

- Client Betting System – Players can place bets, play, or fold through the GUI.

- Dynamic Server Logging – Tracks active clients, game outcomes, and bet amounts in real-time.

- JUnit Testing – Unit tests included to verify game logic functionality.

- Customizable GUI Themes – Clients can change the game’s look and feel dynamically.

## Implementation Details

### Server

- Runs on a separate thread and manages each client connection on individual threads.

- Uses a deck of 52 cards, shuffled before each game.

- Handles all game calculations, including wagers and payouts.

- Provides a JavaFX GUI to monitor active clients, game states, and betting history.

- Allows users to start and stop the server dynamically.

- Client

### JavaFX-based GUI with three main screens:

- Welcome Screen – Connects to the server via IP and port.

- Game Play Screen – Displays player and dealer cards, wager options, and game status updates.

- Game Result Screen – Shows win/loss status and total earnings.

- Players can place Ante and Pair Plus wagers.

- Supports "Fresh Start" (reset winnings) and "New Look" (GUI theme customization).


### Networking

- Built using Java Sockets for client-server communication.

- Server assigns separate threads for each client connection.

- PokerInfo class (Serializable) is used for structured data exchange.
