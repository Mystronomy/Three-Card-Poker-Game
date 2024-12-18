import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Controller for managing the game logic and user interactions in the UI.
 */
public class GameController {
    @FXML private TextField anteField;           // Text field for ante bet input
    @FXML private TextField ppField;             // Text field for Pair Plus bet input
    @FXML private Label playerCardsLabel;        // Label to display player cards
    @FXML private Label dealerCardsLabel;        // Label to display dealer cards
    @FXML private Label gameInfoLabel;           // Label to display game status or instructions
    @FXML private Label totalWinningsLabel;      // Label to display total winnings
    @FXML private Button dealButton;             // Button to deal cards
    @FXML private Button playButton;             // Button to play the current hand
    @FXML private Button foldButton;             // Button to fold the current hand
    @FXML private Button exitButton;             // Button to exit the game
    @FXML private Button freshStartButton;       // Button to start a new game
    @FXML private Button newLookButton;          // Button to toggle UI theme or look

    private JavaFXTemplate mainApp;              // Reference to the main application

    /**
     * Sets the main application reference for communication.
     * @param mainApp The main JavaFXTemplate application instance.
     */
    public void setMainApp(JavaFXTemplate mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller and sets the UI for a new game.
     */
    @FXML
    public void initialize() {
        resetForNewGame(0); // Reset UI for a new game with zero winnings
    }

    /**
     * Resets the UI elements for a new game.
     * @param totalWinnings The player's total winnings to display.
     */
    public void resetForNewGame(int totalWinnings) {
        anteField.setText("5"); // Default ante value
        ppField.setText("0");   // Default Pair Plus value
        playerCardsLabel.setText("Player Cards:"); // Reset player cards display
        dealerCardsLabel.setText("Dealer Cards: (Face Down)"); // Reset dealer cards display
        gameInfoLabel.setText("Place your bets and press Deal."); // Default game info
        totalWinningsLabel.setText("Total Winnings: $" + totalWinnings); // Display total winnings
        dealButton.setDisable(false); // Enable Deal button
        playButton.setDisable(true);  // Disable Play button
        foldButton.setDisable(true);  // Disable Fold button
    }

    /**
     * Handles the Deal button action to process bets and send them to the server.
     */
    @FXML
    public void handleDeal() {
        int ante, pp;
        try {
            ante = Integer.parseInt(anteField.getText().trim()); // Parse ante bet
            pp = Integer.parseInt(ppField.getText().trim());     // Parse Pair Plus bet
        } catch (NumberFormatException e) {
            mainApp.showError("Invalid bet amounts."); // Show error for invalid input
            return;
        }

        // Validate ante bet range
        if ((ante < 5 || ante > 25) && ante != 0) {
            mainApp.showError("Ante must be between 5 and 25.");
            return;
        }
        // Validate Pair Plus bet range
        if (pp != 0 && (pp < 5 || pp > 25)) {
            mainApp.showError("Pair Plus must be 0 or between 5 and 25.");
            return;
        }

        mainApp.sendBetsToServer(ante, pp); // Send bets to the server
        gameInfoLabel.setText("Dealing cards..."); // Update game info
        dealButton.setDisable(true); // Disable Deal button after dealing
    }

    /**
     * Updates the UI to display the dealt cards to the player.
     * @param playerHand The player's hand of cards.
     */
    public void showDealtCards(List<Card> playerHand) {
        playerCardsLabel.setText("Player Cards: " + playerHand); // Show player cards
        dealerCardsLabel.setText("Dealer Cards: [Hidden]");      // Hide dealer cards
        gameInfoLabel.setText("Decide: Play or Fold?");          // Prompt player action
        playButton.setDisable(false);  // Enable Play button
        foldButton.setDisable(false);  // Enable Fold button
    }

    /**
     * Handles the Play button action to send the play decision to the server.
     */
    @FXML
    public void handlePlay() {
        mainApp.sendPlayBet(); // Notify server of Play decision
        gameInfoLabel.setText("Evaluating results..."); // Update game info
        playButton.setDisable(true);  // Disable Play button
        foldButton.setDisable(true);  // Disable Fold button
    }

    /**
     * Handles the Fold button action to send the fold decision to the server.
     */
    @FXML
    public void handleFold() {
        mainApp.sendFold(); // Notify server of Fold decision
        gameInfoLabel.setText("Folding..."); // Update game info
        playButton.setDisable(true);  // Disable Play button
        foldButton.setDisable(true);  // Disable Fold button
    }

    /**
     * Handles the Exit button action to close the application.
     */
    @FXML
    public void handleExit() {
        mainApp.exitProgram(); // Call main app's exit method
    }

    /**
     * Handles the Fresh Start button action to reset the game state.
     */
    @FXML
    public void handleFreshStart() {
        mainApp.freshStart(); // Reset the game state via main app
    }

    /**
     * Handles the New Look button action to toggle the UI theme or appearance.
     */
    @FXML
    public void handleNewLook() {
        mainApp.toggleLook(); // Toggle UI appearance via main app
    }
}
