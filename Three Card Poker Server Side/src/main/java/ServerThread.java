import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerThread listens on a chosen port and accepts incoming client connections.
 */
public class ServerThread extends Thread {
    private int port;               // Port number for the server to listen on
    private boolean running;        // Flag to control the server's running state
    private GameManager gameManager; // Reference to the GameManager for logging and coordination

    /**
     * Constructor to initialize the server thread.
     * @param port The port number on which the server listens.
     * @param gm The GameManager instance for handling game operations and logs.
     */
    public ServerThread(int port, GameManager gm) {
        this.port = port;             // Set the server port
        this.gameManager = gm;        // Set the GameManager reference
        running = true;               // Initialize the server as running
    }

    /**
     * The main execution method of the server thread.
     * Listens for client connections and starts a ClientHandler thread for each.
     */
    @Override
    public void run() {
        try (ServerSocket serverSock = new ServerSocket(port)) {
            gameManager.logResult("Server started on port: " + port); // Log server start
            while (running) {
                // Accept incoming client connections
                Socket clientSocket = serverSock.accept();
                ClientHandler ch = new ClientHandler(clientSocket, gameManager); // Create handler
                ch.start(); // Start a new thread to handle the client
            }
        } catch (Exception e) {
            if (running) { // Log errors only if the server is not stopped intentionally
                gameManager.logResult("Server encountered error: " + e.getMessage());
                e.printStackTrace();
            } else {
                gameManager.logResult("Server stopped."); // Log intentional server stop
            }
        }
    }

    /**
     * Stops the server by setting the running flag to false and unblocking accept().
     */
    public void stopServer() {
        running = false; // Update running state to stop the server loop
        try {
            // Open a dummy connection to unblock the serverSocket's accept() method
            new Socket("localhost", port).close();
        } catch (Exception e) {
            // Ignore exceptions during dummy connection
        }
    }
}
