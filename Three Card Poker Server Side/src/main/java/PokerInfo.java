import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class used to pass information between server and client via sockets.
 * Data fields can be adapted as needed for the Poker game.
 */
public class PokerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Enumeration of possible message types exchanged between client and server.
     */
    public enum MessageType {
        CONNECT, BETS, DEAL, PLAY, FOLD, RESULT, DISCONNECT, CONTINUE
    }

    private MessageType messageType; // Type of message
    private int anteBet;             // Ante bet amount
    private int pairPlusBet;         // PairPlus bet amount
    private int playBet;             // Play bet amount
    private ArrayList<Card> playerHand;  // Player's hand
    private ArrayList<Card> dealerHand;  // Dealer's hand
    private boolean dealerQualifies; // Whether the dealer qualifies
    private int gameResult;          // Game result: 1=player wins, -1=dealer wins, 0=tie
    private int pairPlusWinnings;    // PairPlus winnings amount

    private String infoMessage;      // Additional information message

    // Default constructor
    public PokerInfo() {}

    // Getters and Setters

    public MessageType getMessageType() {
        return messageType; // Returns the message type
    }
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType; // Sets the message type
    }

    public int getAnteBet() {
        return anteBet; // Returns the ante bet amount
    }
    public void setAnteBet(int anteBet) {
        this.anteBet = anteBet; // Sets the ante bet amount
    }

    public int getPairPlusBet() {
        return pairPlusBet; // Returns the PairPlus bet amount
    }
    public void setPairPlusBet(int pairPlusBet) {
        this.pairPlusBet = pairPlusBet; // Sets the PairPlus bet amount
    }

    public int getPlayBet() {
        return playBet; // Returns the play bet amount
    }
    public void setPlayBet(int playBet) {
        this.playBet = playBet; // Sets the play bet amount
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand; // Returns the player's hand
    }
    public void setPlayerHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand; // Sets the player's hand
    }

    public ArrayList<Card> getDealerHand() {
        return dealerHand; // Returns the dealer's hand
    }
    public void setDealerHand(ArrayList<Card> dealerHand) {
        this.dealerHand = dealerHand; // Sets the dealer's hand
    }

    public boolean isDealerQualifies() {
        return dealerQualifies; // Checks if the dealer qualifies
    }
    public void setDealerQualifies(boolean dealerQualifies) {
        this.dealerQualifies = dealerQualifies; // Sets the dealer's qualification status
    }

    public int getGameResult() {
        return gameResult; // Returns the game result
    }
    public void setGameResult(int gameResult) {
        this.gameResult = gameResult; // Sets the game result
    }

    public int getPairPlusWinnings() {
        return pairPlusWinnings; // Returns PairPlus winnings
    }
    public void setPairPlusWinnings(int pairPlusWinnings) {
        this.pairPlusWinnings = pairPlusWinnings; // Sets PairPlus winnings
    }

    public String getInfoMessage() {
        return infoMessage; // Returns additional info message
    }
    public void setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage; // Sets additional info message
    }
}
