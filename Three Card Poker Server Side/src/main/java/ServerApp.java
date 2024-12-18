import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Main server application that starts the JavaFX GUI and server logic.
 */
public class ServerApp extends Application {
    private Stage primaryStage; // The main stage for the application
    private Scene introScene;   // Scene for the intro screen
    private Scene mainScene;    // Scene for the main screen

    private ServerThread serverThread; // Thread handling the server logic
    private GameManager gameManager;   // Manages game state and logs

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Initialize the primary stage
        loadIntroScene(); // Load the introductory scene
    }

    /**
     * Loads the Intro Scene where the user can input the server port.
     */
    private void loadIntroScene() {
        try {
            // Load FXML for Intro Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IntroScene.fxml"));
            Parent root = loader.load();
            IntroController controller = loader.getController();
            controller.setMainApp(this); // Link controller to this app
            introScene = new Scene(root, 400, 300); // Create the intro scene
            primaryStage.setScene(introScene); // Set the stage to show the intro scene
            primaryStage.setTitle("3 Card Poker Server - Intro"); // Set the window title
            primaryStage.show(); // Show the stage
        } catch (Exception e) {
            showError("Failed to load Intro Scene: " + e.getMessage()); // Show error if loading fails
            e.printStackTrace();
        }
    }

    /**
     * Starts the server with the specified port, initializes GameManager, and loads the Main Scene.
     * @param port The port number on which the server listens.
     */
    public void startServer(int port) {
        // Initialize game logs and game manager
        ObservableList<String> gameLogs = FXCollections.observableArrayList();
        gameManager = new GameManager(gameLogs);
        // Start the server thread
        serverThread = new ServerThread(port, gameManager);
        serverThread.start();
        // Load the main scene for server management
        loadMainScene(gameLogs);
    }

    /**
     * Loads the Main Scene which displays connected clients and logs.
     * @param gameLogs The observable list containing game logs.
     */
    private void loadMainScene(ObservableList<String> gameLogs) {
        try {
            // Load FXML for Main Scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
            Parent root = loader.load();
            MainController controller = loader.getController();
            controller.setMainApp(this); // Link controller to this app
            mainScene = new Scene(root, 600, 400); // Create the main scene
            primaryStage.setScene(mainScene); // Set the stage to show the main scene
            primaryStage.setTitle("3 Card Poker Server - Main"); // Set the window title
            gameManager.setMainController(controller); // Connect game manager to controller
        } catch (Exception e) {
            showError("Failed to load Main Scene: " + e.getMessage()); // Show error if loading fails
            e.printStackTrace();
        }
    }

    /**
     * Stops the server and notifies the user.
     */
    public void stopServer() {
        if (serverThread != null && serverThread.isAlive()) {
            // Stop the server thread and log the result
            serverThread.stopServer();
            gameManager.logResult("Server has been stopped.");
            // Show information alert to the user
            Alert alert = new Alert(AlertType.INFORMATION, "Server has been stopped.");
            alert.showAndWait();
        } else {
            // Show warning alert if the server is not running
            Alert alert = new Alert(AlertType.WARNING, "Server is not running.");
            alert.showAndWait();
        }
    }

    /**
     * Displays an error alert with the provided message.
     * @param message The error message to display.
     */
    public void showError(String message) {
        // Create and display an error alert
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
