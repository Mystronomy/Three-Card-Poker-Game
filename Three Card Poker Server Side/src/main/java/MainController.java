import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for MainScene.fxml.
 */
public class MainController {
    @FXML private ListView<String> listView; // ListView to display game logs
    @FXML private Button stopButton;          // Button to stop the server
    @FXML private Label clientCountLabel;     // Label to show the number of connected clients

    private ServerApp mainApp; // Reference to the main server application

    /**
     * Sets the main application reference.
     *
     * @param mainApp The main ServerApp instance.
     */
    public void setMainApp(ServerApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller. Called after FXML fields are populated.
     */
    @FXML
    public void initialize() {
        // Initialization logic can be added here if needed
    }

    /**
     * Handles the stop button action to shut down the server.
     */
    @FXML
    public void stopServer() {
        mainApp.stopServer(); // Invoke the server stop method in the main application
    }

    /**
     * Updates the client count label with the current number of connected clients.
     *
     * @param count The current number of connected clients.
     */
    public void updateClientCount(int count) {
        clientCountLabel.setText(String.valueOf(count)); // Update the label text
    }
}
