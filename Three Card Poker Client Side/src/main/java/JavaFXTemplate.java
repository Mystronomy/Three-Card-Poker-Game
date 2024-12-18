import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

/**
 * Main JavaFX application for the 3 Card Poker client.
 * Manages scenes, communication with the server, and game state.
 */
public class JavaFXTemplate extends Application {
    private Stage primaryStage;            // Main application stage

    private Scene welcomeScene;            // Scene for the welcome screen
    private Scene gameScene;               // Scene for the game screen
    private Scene resultScene;             // Scene for the results screen

    private WelcomeController welcomeController; // Controller for the welcome scene
    private GameController gameController;       // Controller for the game scene
    private ResultController resultController;   // Controller for the results scene

    private GameState gameState;           // Holds the current state of the game
    private ClientThread clientThread;     // Manages communication with the server

    private boolean originalLook = true;   // Flag to track UI theme state

    /**
     * Entry point of the JavaFX application.
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.gameState = new GameState();

        // Load FXML for Welcome Scene
        FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/WelcomeScene.fxml"));
        Parent welcomeRoot = welcomeLoader.load();
        welcomeController = welcomeLoader.getController();
        welcomeController.setMainApp(this);
        welcomeScene = new Scene(welcomeRoot, 300, 250);

        primaryStage.setScene(welcomeScene); // Show the welcome scene
        primaryStage.setTitle("3 Card Poker Client - Welcome"); // Set window title
        primaryStage.show();
    }

    /**
     * Loads the game scene for the main gameplay.
     */
    public void loadGameScene() {
        try {
            FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/GameScene.fxml"));
            Parent gameRoot = gameLoader.load();
            gameController = gameLoader.getController();
            gameController.setMainApp(this);
            gameScene = new Scene(gameRoot, 600, 400);
            applyCurrentLook(gameScene); // Apply current UI theme
        } catch (Exception e) {
            showError("Error loading game scene: " + e.getMessage());
        }
    }

    /**
     * Loads the result scene for displaying game results.
     */
    public void loadResultScene() {
        try {
            FXMLLoader resultLoader = new FXMLLoader(getClass().getResource("/ResultScene.fxml"));
            Parent resultRoot = resultLoader.load();
            resultController = resultLoader.getController();
            resultController.setMainApp(this);
            resultScene = new Scene(resultRoot, 300, 200);
            applyCurrentLook(resultScene); // Apply current UI theme
        } catch (Exception e) {
            showError("Error loading result scene: " + e.getMessage());
        }
    }

    /**
     * Connects to the server using the given IP address and port.
     * @param ip The server IP address.
     * @param port The server port.
     */
    public void connectToServer(String ip, int port) {
        clientThread = new ClientThread(ip, port, this); // Initialize the client thread
        clientThread.start(); // Start the connection
    }

    /**
     * Called when successfully connected to the server.
     * Switches to the game scene.
     */
    public void onConnected() {
        loadGameScene();
        primaryStage.setScene(gameScene); // Switch to game scene
        primaryStage.setTitle("3 Card Poker Client - Game"); // Update window title
    }

    /**
     * Handles responses from the server and updates the game state accordingly.
     * @param info The PokerInfo object received from the server.
     */
    public void handleServerResponse(PokerInfo info) {
        if (info.getMessageType() == PokerInfo.MessageType.DEAL) {
            gameState.setCurrentInfo(info);
            gameController.showDealtCards(info.getPlayerHand()); // Show dealt cards
        } else if (info.getMessageType() == PokerInfo.MessageType.RESULT) {
            gameState.setCurrentInfo(info);
            processResults(info); // Process game results
        }
    }

    /**
     * Processes the game results and updates the game state and UI.
     * @param info The PokerInfo object containing the results.
     */
    private void processResults(PokerInfo info) {
        int result = info.getGameResult();
        int ante = info.getAnteBet();
        int pairPlus = info.getPairPlusBet();
        int play = info.getPlayBet();
        int ppWin = info.getPairPlusWinnings();

        int winnings = 0;
        if (result == 1) {
            winnings += (ante + play); // Player wins
        } else if (result == -1) {
            winnings -= (ante + play); // Player loses
        }
        winnings += (ppWin - pairPlus); // Calculate Pair Plus winnings

        gameState.addWinnings(winnings); // Update total winnings
        loadResultScene();
        resultController.showResult(result, winnings, gameState.getTotalWinnings());
        primaryStage.setScene(resultScene); // Switch to result scene
        primaryStage.setTitle("3 Card Poker Client - Results");
    }

    /**
     * Sends the player's bets to the server.
     * @param ante The ante bet amount.
     * @param pp The Pair Plus bet amount.
     */
    public void sendBetsToServer(int ante, int pp) {
        gameState.setAnteBet(ante);
        gameState.setPairPlusBet(pp);
        PokerInfo info = new PokerInfo();
        info.setMessageType(PokerInfo.MessageType.BETS);
        info.setAnteBet(ante);
        info.setPairPlusBet(pp);
        clientThread.sendMessage(info);
    }

    /**
     * Sends the play decision to the server.
     */
    public void sendPlayBet() {
        gameState.setPlayBet(gameState.getAnteBet());
        PokerInfo curr = gameState.getCurrentInfo();
        curr.setMessageType(PokerInfo.MessageType.PLAY);
        curr.setPlayBet(gameState.getPlayBet());
        clientThread.sendMessage(curr);
    }

    /**
     * Sends the fold decision to the server.
     */
    public void sendFold() {
        PokerInfo curr = gameState.getCurrentInfo();
        curr.setMessageType(PokerInfo.MessageType.FOLD);
        clientThread.sendMessage(curr);
    }

    /**
     * Resets the game state for a fresh start.
     */
    public void freshStart() {
        gameState.resetWinnings();
        returnToGameScene();
    }

    /**
     * Returns to the game scene and resets the UI.
     */
    public void returnToGameScene() {
        loadGameScene();
        primaryStage.setScene(gameScene); // Switch to game scene
        primaryStage.setTitle("3 Card Poker Client - Game");
        gameController.resetForNewGame(gameState.getTotalWinnings()); // Reset game UI
    }

    /**
     * Toggles the application's UI theme.
     */
    public void toggleLook() {
        originalLook = !originalLook; // Switch theme state
        applyCurrentLook(primaryStage.getScene()); // Apply the updated theme
    }

    /**
     * Applies the current UI theme to a given scene.
     * @param scene The scene to apply the theme to.
     */
    private void applyCurrentLook(Scene scene) {
        if (originalLook) {
            scene.getRoot().setStyle("-fx-background-color: white; -fx-font-family: Arial; -fx-font-size:14;");
        } else {
            scene.getRoot().setStyle("-fx-background-color: beige; -fx-font-family: 'Comic Sans MS'; -fx-font-size:16; -fx-text-fill: #333;");
        }
    }

    /**
     * Exits the program, disconnecting from the server if connected.
     */
    public void exitProgram() {
        if (clientThread != null) {
            clientThread.disconnect();
        }
        stopApp(); // Close the application
    }

    /**
     * Stops the application gracefully.
     */
    public void stopApp() {
        primaryStage.close(); // Close the primary stage
    }

    /**
     * Displays an error alert with the given message.
     * @param message The error message to display.
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
