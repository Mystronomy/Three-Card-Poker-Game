import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the results screen, managing the display of game results and user actions.
 */
public class ResultController {
    @FXML private Label resultLabel;            // Label to display the game result (win, lose, tie)
    @FXML private Label resultWinningsLabel;    // Label to display round and total winnings
    @FXML private Button anotherGameButton;     // Button to start another game
    @FXML private Button exitButton;            // Button to exit the application

    private JavaFXTemplate mainApp;             // Reference to the main application

    /**
     * Sets the main application reference for communication.
     * @param mainApp The main JavaFXTemplate application instance.
     */
    public void setMainApp(JavaFXTemplate mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Handles the "Another Game" button action.
     * Returns to the game scene for a new round.
     */
    @FXML
    public void handleAnotherGame() {
        mainApp.returnToGameScene(); // Switch back to the game scene
    }

    /**
     * Handles the "Exit" button action.
     * Exits the application.
     */
    @FXML
    public void handleExit() {
        mainApp.exitProgram(); // Close the application
    }

    /**
     * Displays the game result and updates the winnings information.
     * @param gameResult The result of the game (1=win, -1=lose, 0=tie or dealer not qualified).
     * @param roundWinnings The winnings for the current round.
     * @param totalWinnings The player's total winnings.
     */
    public void showResult(int gameResult, int roundWinnings, int totalWinnings) {
        if (gameResult == 1) {
            resultLabel.setText("You Won!"); // Display win message
        } else if (gameResult == -1) {
            resultLabel.setText("You Lost!"); // Display loss message
        } else {
            resultLabel.setText("Tie or Dealer Not Qualified"); // Display tie message
        }
        resultWinningsLabel.setText("This Game: $" + roundWinnings + " | Total: $" + totalWinnings); // Update winnings
    }
}
