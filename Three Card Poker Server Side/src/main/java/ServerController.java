import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controls the GUI and server operations.
 */
public class ServerController {
    private Stage primaryStage;       // Main stage for the application
    private Scene introScene;         // Scene for the intro screen
    private Scene mainScene;          // Scene for the main server screen
    private TextField portField;      // Text field for entering the server port
    private Button startButton;       // Button to start the server
    private Button stopButton;        // Button to stop the server
    private ListView<String> listView; // ListView to display server logs

    private ServerThread serverThread; // Thread managing server operations
    private GameManager gameManager;   // Manages game state and logs
    private ObservableList<String> gameLogs; // Observable list for game logs

    /**
     * Constructor to initialize the controller with the primary stage.
     * @param stage The primary stage for the application.
     */
    public ServerController(Stage stage) {
        this.primaryStage = stage;
        setupIntroScene(); // Set up the introductory scene
    }

    /**
     * Sets up the intro scene where the user can enter the server port.
     */
    public void setupIntroScene() {
        Label portLabel = new Label("Enter Port Number:"); // Label for port input
        portField = new TextField("5555");                // Default port value
        startButton = new Button("Start Server");         // Button to start server
        startButton.setOnAction(e -> startServer());      // Attach event handler

        VBox vbox = new VBox(10, portLabel, portField, startButton); // Arrange elements vertically
        vbox.setMinSize(300, 200);                       // Set minimum size for the scene

        introScene = new Scene(vbox, 300, 200);          // Create the intro scene
        primaryStage.setScene(introScene);               // Set the scene to the stage
        primaryStage.setTitle("Server Intro");           // Set the window title
        primaryStage.show();                             // Show the stage
    }

    /**
     * Sets up the main scene for server operation, including logs and stop button.
     */
    public void setupMainScene() {
        gameLogs = FXCollections.observableArrayList(); // Initialize game logs
        listView = new ListView<>(gameLogs);            // Create ListView for logs

        gameManager = new GameManager(gameLogs);        // Initialize game manager

        stopButton = new Button("Stop Server");         // Button to stop server
        stopButton.setOnAction(e -> stopServer());      // Attach event handler

        HBox topBar = new HBox(10, new Label("Server Running"), stopButton); // Top bar with label and button

        BorderPane root = new BorderPane();             // Layout container
        root.setTop(topBar);                            // Place top bar
        root.setCenter(listView);                       // Place log view in the center

        mainScene = new Scene(root, 600, 400);          // Create the main scene
    }

    /**
     * Starts the server and switches to the main scene.
     */
    public void startServer() {
        try {
            int port = Integer.parseInt(portField.getText().trim()); // Parse port input
            setupMainScene();                       // Set up the main scene
            primaryStage.setScene(mainScene);       // Switch to main scene
            primaryStage.setTitle("Server Main");   // Update the window title
            serverThread = new ServerThread(port, gameManager); // Start server thread
            serverThread.start();
        } catch (NumberFormatException ex) {
            // Show error alert for invalid port input
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid port number.");
            alert.showAndWait();
        }
    }

    /**
     * Stops the server and logs the action.
     */
    public void stopServer() {
        if (serverThread != null) {
            serverThread.stopServer();               // Stop the server thread
            gameManager.logResult("Server Stopped"); // Log the server stop action
        }
    }
}
