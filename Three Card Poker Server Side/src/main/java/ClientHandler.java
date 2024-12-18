import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Handles a single client connection. Runs on its own thread.
 */
public class ClientHandler extends Thread {
    private Socket socket; // Client socket connection
    private GameManager gameManager; // Reference to the game manager
    private boolean running; // Flag to control the thread loop
    private Deck deck; // Deck of cards for the game
    private ObjectOutputStream oos; // Output stream to send objects to client
    private ObjectInputStream ois; // Input stream to receive objects from client
    private String clientName; // Identifier for the client

    /**
     * Constructor to initialize ClientHandler with the client socket and GameManager.
     *
     * @param socket The client socket.
     * @param gm     The GameManager instance.
     */
    public ClientHandler(Socket socket, GameManager gm) {
        this.socket = socket;
        this.gameManager = gm;
        this.running = true;
        this.deck = new Deck();
    }

    @Override
    public void run() {
        try {
            // Initialize streams for communication
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            clientName = socket.getRemoteSocketAddress().toString();
            gameManager.logConnection(clientName); // Log new connection

            while (running) {
                // Read incoming message from client
                PokerInfo info = (PokerInfo) ois.readObject();
                
                // Handle disconnect message
                if (info.getMessageType() == PokerInfo.MessageType.DISCONNECT) {
                    running = false;
                    gameManager.logDisconnection(clientName); // Log disconnection
                    break;
                }

                // Handle different message types
                switch (info.getMessageType()) {
                    case BETS:
                        handleBets(info);
                        break;
                    case PLAY:
                        handlePlay(info);
                        break;
                    case FOLD:
                        handleFold(info);
                        break;
                    default:
                        // Unknown message type
                        gameManager.logResult("Unknown message type from " + clientName);
                        break;
                }
            }

        } catch (Exception e) {
            // Log any exceptions that occur
            gameManager.logResult("Exception in client handler (" + clientName + "): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // Ensure socket is closed
            } catch (Exception e) {
                // Ignore exceptions on close
            }
            if (running) {
                gameManager.logDisconnection(clientName); // Log disconnection if not already
            }
        }
    }

    /**
     * Handles BETS message type by dealing and sending hands to the client.
     *
     * @param info The PokerInfo message received.
     * @throws Exception If an error occurs during handling.
     */
    private void handleBets(PokerInfo info) throws Exception {
        deck = new Deck();
        deck.shuffle(); // Shuffle the deck
        ArrayList<Card> playerHand = new ArrayList<>(deck.dealHand()); // Deal player's hand
        ArrayList<Card> dealerHand = new ArrayList<>(deck.dealHand()); // Deal dealer's hand

        // Prepare DEAL response
        PokerInfo response = new PokerInfo();
        response.setMessageType(PokerInfo.MessageType.DEAL);
        response.setPlayerHand(playerHand);
        response.setDealerHand(dealerHand);

        // Send DEAL message to client
        oos.writeObject(response);
        oos.flush();
    }

    /**
     * Handles PLAY message type by evaluating game results and sending them to the client.
     *
     * @param info The PokerInfo message received.
     * @throws Exception If an error occurs during handling.
     */
    private void handlePlay(PokerInfo info) throws Exception {
        ArrayList<Card> playerHand = info.getPlayerHand();
        ArrayList<Card> dealerHand = info.getDealerHand();
        boolean dq = ThreeCardLogic.dealerQualifies(dealerHand); // Check if dealer qualifies
        int gameRes;

        if (!dq) {
            // Dealer doesn't qualify: Tie result
            gameRes = 0;
        } else {
            // Compare hands to determine winner
            gameRes = ThreeCardLogic.compareHands(dealerHand, playerHand);
        }

        // Calculate Pair Plus winnings
        int ppWinnings = ThreeCardLogic.evalPPWinnings(playerHand, info.getPairPlusBet());

        // Set game results
        info.setGameResult(gameRes);
        info.setPairPlusWinnings(ppWinnings);
        info.setDealerQualifies(dq);

        // Create result string for logging
        String resStr;
        if (gameRes > 0) {
            resStr = "Player wins against dealer. Bets: Ante=$" + info.getAnteBet()
                    + ", Play=$" + info.getPlayBet() + ", PP=$" + info.getPairPlusBet();
        } else if (gameRes < 0) {
            resStr = "Dealer wins against player. Bets: Ante=$" + info.getAnteBet()
                    + ", Play=$" + info.getPlayBet() + ", PP=$" + info.getPairPlusBet();
        } else {
            resStr = "Tie/Dealer doesn't qualify. Bets: Ante=$" + info.getAnteBet()
                    + ", Play=$" + info.getPlayBet() + ", PP=$" + info.getPairPlusBet();
        }
        if (ppWinnings > 0) {
            resStr += " Player wins PairPlus: $" + ppWinnings;
        }

        // Log the result
        gameManager.logResult("Client " + clientName + ": " + resStr);

        // Send RESULT message to client
        info.setMessageType(PokerInfo.MessageType.RESULT);
        oos.writeObject(info);
        oos.flush();
    }

    /**
     * Handles FOLD message type by updating game state and notifying the client.
     *
     * @param info The PokerInfo message received.
     * @throws Exception If an error occurs during handling.
     */
    private void handleFold(PokerInfo info) throws Exception {
        // Create result string for folding
        String resStr = "Player folds. Lost Ante=$" + info.getAnteBet();
        if (info.getPairPlusBet() > 0) {
            resStr += " and PP=$" + info.getPairPlusBet();
        }
        gameManager.logResult("Client " + clientName + ": " + resStr);

        // Set game result for folding
        info.setMessageType(PokerInfo.MessageType.RESULT);
        info.setGameResult(-1); // Indicate player loss by folding

        // Send RESULT message to client
        oos.writeObject(info);
        oos.flush();
    }
}
