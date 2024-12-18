import javafx.application.Platform;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Manages information about games played and updates the UI ListView on the main scene.
 */
public class GameManager {
    private ObservableList<String> gameLog; // Observable list to store game log messages
    private int clientCount = 0; // Tracks the number of connected clients
    private MainController mainController; // Reference to MainController for UI updates

    // Formatter for timestamps in log messages
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor to initialize GameManager with the game log.
     *
     * @param gameLog The observable list to log game events.
     */
    public GameManager(ObservableList<String> gameLog) {
        this.gameLog = gameLog;
    }

    /**
     * Sets the MainController reference to allow updating the client count label.
     *
     * @param controller The MainController instance.
     */
    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    /**
     * Logs a client connection, increments client count, and updates the GUI.
     *
     * @param clientInfo Information about the connected client.
     */
    public synchronized void logConnection(String clientInfo) {
        clientCount++; // Increment client count
        String timeStamp = LocalDateTime.now().format(formatter); // Get current timestamp
        String logMessage = "[" + timeStamp + "] New client connected: " + clientInfo;
        System.out.println(logMessage); // Print to console for debugging

        // Update the game log and UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            gameLog.add(logMessage); // Add log message to the observable list
            if (mainController != null) {
                mainController.updateClientCount(clientCount); // Update client count in UI
                System.out.println("Updated client count to: " + clientCount); // Debug statement
            } else {
                System.out.println("MainController is null."); // Debug if controller is not set
            }
        });
    }

    /**
     * Logs a client disconnection, decrements client count, and updates the GUI.
     *
     * @param clientInfo Information about the disconnected client.
     */
    public synchronized void logDisconnection(String clientInfo) {
        if (clientCount > 0) {
            clientCount--; // Decrement client count if above zero
        }
        String timeStamp = LocalDateTime.now().format(formatter); // Get current timestamp
        String logMessage = "[" + timeStamp + "] Client disconnected: " + clientInfo;
        System.out.println(logMessage); // Print to console for debugging

        // Update the game log and UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            gameLog.add(logMessage); // Add log message to the observable list
            if (mainController != null) {
                mainController.updateClientCount(clientCount); // Update client count in UI
                System.out.println("Updated client count to: " + clientCount); // Debug statement
            } else {
                System.out.println("MainController is null."); // Debug if controller is not set
            }
        });
    }

    /**
     * Logs a generic result or event with a timestamp.
     *
     * @param resultInfo The result or event information to log.
     */
    public synchronized void logResult(String resultInfo) {
        String timeStamp = LocalDateTime.now().format(formatter); // Get current timestamp
        String logMessage = "[" + timeStamp + "] " + resultInfo;
        System.out.println(logMessage); // Print to console for debugging

        // Add the log message to the observable list on the JavaFX Application Thread
        Platform.runLater(() -> {
            gameLog.add(logMessage);
        });
    }
}
