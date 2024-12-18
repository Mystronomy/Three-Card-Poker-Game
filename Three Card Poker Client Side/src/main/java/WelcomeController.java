import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

/**
 * Controller for the welcome screen, managing server connection inputs and actions.
 */
public class WelcomeController {
    @FXML private TextField ipField;         // Text field for entering the server IP address
    @FXML private TextField portField;       // Text field for entering the server port
    @FXML private Button connectButton;      // Button to connect to the server

    private JavaFXTemplate mainApp;          // Reference to the main application

    /**
     * Sets the main application reference for communication.
     * @param mainApp The main JavaFXTemplate application instance.
     */
    public void setMainApp(JavaFXTemplate mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller and sets default values for IP and port fields.
     */
    @FXML
    public void initialize() {
        ipField.setText("127.0.0.1"); // Default IP address
        portField.setText("5555");    // Default port number
    }

    /**
     * Handles the connect button action to connect to the server.
     * Validates the input and passes it to the main application for connection.
     */
    @FXML
    public void connectToServer() {
        String ip = ipField.getText().trim(); // Get the IP address
        int port;
        try {
            port = Integer.parseInt(portField.getText().trim()); // Parse the port number
        } catch (NumberFormatException e) {
            mainApp.showError("Invalid port number."); // Show error for invalid port
            return;
        }
        mainApp.connectToServer(ip, port); // Connect to the server
    }
}
