import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents information exchanged between the client and server in a poker game.
 */
public class PokerInfo implements Serializable {
    private static final long serialVersionUID = 1L; // Serialization ID for object persistence

    /**
     * Enumeration of message types used in communication.
     * CONNECT - Initial connection message
     * BETS - Client sends bets
     * DEAL - Server sends dealt cards
     * PLAY - Client plays the current hand
     * FOLD - Client folds the current hand
     * RESULT - Server sends the result of the game
     * DISCONNECT - Client disconnects
     * CONTINUE - Client continues to next round
     */
    public enum MessageType {
        CONNECT, BETS, DEAL, PLAY, FOLD, RESULT, DISCONNECT, CONTINUE
    }

    private MessageType messageType;         // Type of message being exchanged
    private int anteBet;                     // Ante bet amount
    private int pairPlusBet;                 // Pair Plus bet amount
    private int playBet;                     // Play bet amount
    private ArrayList<Card> playerHand;      // Player's hand of cards
    private ArrayList<Card> dealerHand;      // Dealer's hand of cards
    private boolean dealerQualifies;         // Whether the dealer qualifies
    private int gameResult;                  // Game result: 1=player wins, -1=dealer wins, 0=tie
    private int pairPlusWinnings;            // Winnings from the Pair Plus bet
    private String infoMessage;              // Additional information or messages

    // Getters and Setters

    public MessageType getMessageType() { return messageType; }
    public void setMessageType(MessageType messageType) { this.messageType = messageType; }

    public int getAnteBet() { return anteBet; }
    public void setAnteBet(int anteBet) { this.anteBet = anteBet; }

    public int getPairPlusBet() { return pairPlusBet; }
    public void setPairPlusBet(int pairPlusBet) { this.pairPlusBet = pairPlusBet; }

    public int getPlayBet() { return playBet; }
    public void setPlayBet(int playBet) { this.playBet = playBet; }

    public ArrayList<Card> getPlayerHand() { return playerHand; }
    public void setPlayerHand(ArrayList<Card> playerHand) { this.playerHand = playerHand; }

    public ArrayList<Card> getDealerHand() { return dealerHand; }
    public void setDealerHand(ArrayList<Card> dealerHand) { this.dealerHand = dealerHand; }

    public boolean isDealerQualifies() { return dealerQualifies; }
    public void setDealerQualifies(boolean dealerQualifies) { this.dealerQualifies = dealerQualifies; }

    public int getGameResult() { return gameResult; }
    public void setGameResult(int gameResult) { this.gameResult = gameResult; }

    public int getPairPlusWinnings() { return pairPlusWinnings; }
    public void setPairPlusWinnings(int pairPlusWinnings) { this.pairPlusWinnings = pairPlusWinnings; }

    public String getInfoMessage() { return infoMessage; }
    public void setInfoMessage(String infoMessage) { this.infoMessage = infoMessage; }
}
