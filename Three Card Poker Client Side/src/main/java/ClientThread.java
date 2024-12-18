import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Platform;

/**
 * Handles communication between the client and server in a separate thread.
 */
public class ClientThread extends Thread {
    private String ip;                  // Server IP address
    private int port;                   // Server port
    private Socket socket;              // Socket for communication
    private ObjectOutputStream oos;     // Output stream for sending data
    private ObjectInputStream ois;      // Input stream for receiving data
    private boolean running = true;     // Flag to control the thread loop
    private JavaFXTemplate mainApp;     // Reference to the main application

    /**
     * Constructs the client thread with the server's IP, port, and main application.
     * @param ip The server IP address.
     * @param port The server port.
     * @param mainApp Reference to the JavaFXTemplate application.
     */
    public ClientThread(String ip, int port, JavaFXTemplate mainApp) {
        this.ip = ip;
        this.port = port;
        this.mainApp = mainApp;
    }

    /**
     * Main thread execution method. Handles connection setup and communication loop.
     */
    @Override
    public void run() {
        try {
            socket = new Socket(ip, port); // Establish connection to the server
            oos = new ObjectOutputStream(socket.getOutputStream()); // Initialize output stream
            ois = new ObjectInputStream(socket.getInputStream());   // Initialize input stream

            // Notify the main application of a successful connection
            Platform.runLater(() -> mainApp.onConnected());

            // Communication loop to handle incoming messages from the server
            while (running) {
                Object obj = ois.readObject(); // Read object from server
                if (obj instanceof PokerInfo) {
                    PokerInfo info = (PokerInfo) obj; // Cast object to PokerInfo
                    Platform.runLater(() -> mainApp.handleServerResponse(info)); // Handle the response in the UI thread
                }
            }

        } catch (Exception e) {
            // Notify the main application of a connection error
            Platform.runLater(() -> mainApp.showError("Connection Error: " + e.getMessage()));
        } finally {
            // Ensure socket is closed when thread exits
            try { if (socket != null) socket.close(); } catch (Exception ignore) {}
        }
    }

    /**
     * Sends a PokerInfo message to the server.
     * @param info The PokerInfo object to send.
     */
    public void sendMessage(PokerInfo info) {
        try {
            oos.writeObject(info); // Send the PokerInfo object
            oos.flush();           // Flush the output stream
        } catch (Exception e) {
            // Notify the main application of a send error
            Platform.runLater(() -> mainApp.showError("Send Error: " + e.getMessage()));
        }
    }

    /**
     * Disconnects from the server by sending a DISCONNECT message and closing the socket.
     */
    public void disconnect() {
        running = false; // Stop the communication loop
        try {
            PokerInfo dis = new PokerInfo(); // Create a disconnect message
            dis.setMessageType(PokerInfo.MessageType.DISCONNECT);
            sendMessage(dis); // Send the disconnect message
            socket.close();   // Close the socket
        } catch (Exception ignore) {}
    }
}
