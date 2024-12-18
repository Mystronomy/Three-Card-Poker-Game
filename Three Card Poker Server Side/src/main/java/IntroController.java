import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller for IntroScene.fxml.
 */
public class IntroController {
    @FXML private TextField portField; // TextField for user to enter server port
    @FXML private Button startButton;   // Button to start the server

    private ServerApp mainApp; // Reference to the main application

    /**
     * Sets the reference to the main application.
     *
     * @param mainApp The main ServerApp instance.
     */
    public void setMainApp(ServerApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller. Sets the default port number.
     */
    @FXML
    public void initialize() {
        portField.setText("5555"); // Set default port to 5555
    }

    /**
     * Handles the start button action. Validates the port and starts the server.
     */
    @FXML
    public void startServer() {
        String portText = portField.getText().trim(); // Get and trim the port input
        int port;
        try {
            port = Integer.parseInt(portText); // Parse port number
            if (port < 1024 || port > 65535) {  // Check if port is within valid range
                throw new NumberFormatException("Port out of range");
            }
        } catch (NumberFormatException e) {
            // Show error alert if port is invalid
            Alert alert = new Alert(AlertType.ERROR, "Please enter a valid port number (1024-65535).");
            alert.showAndWait();
            return; // Exit the method if validation fails
        }

        mainApp.startServer(port); // Start the server with the validated port
    }
}
