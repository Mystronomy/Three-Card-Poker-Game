import java.util.ArrayList;
import java.util.Comparator;

/**
 * Contains the logic for evaluating three card poker hands,
 * comparing hands, and calculating pair plus winnings.
 */
public class ThreeCardLogic {

    /**
     * Evaluates a 3 card hand and returns a rank integer that can be used to compare hands.
     * Hand ranks from best to worst:
     * Straight Flush = 6
     * Three of a Kind = 5
     * Straight = 4
     * Flush = 3
     * Pair = 2
     * High Card = 1
     */
    public static int evalHand(ArrayList<Card> hand) {
        sortHand(hand); // Sort hand for consistent evaluation
        boolean flush = isFlush(hand); // Check for flush
        boolean straight = isStraight(hand); // Check for straight
        boolean threeKind = isThreeOfKind(hand); // Check for three of a kind
        boolean pair = isPair(hand); // Check for pair

        if (straight && flush) return 6;     // Straight Flush
        if (threeKind) return 5;             // Three of a Kind
        if (straight) return 4;              // Straight
        if (flush) return 3;                 // Flush
        if (pair) return 2;                  // Pair
        return 1;                            // High Card
    }

    /**
     * Calculates Pair Plus winnings based on hand rank and bet amount.
     * Returns winnings or 0 if the hand is less than a pair.
     */
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        int rank = evalHand(hand); // Get hand rank
        if (rank < 2) {
            return 0; // No payout for rank less than pair
        }
        switch (rank) {
            case 2: // Pair
                return bet * 1;
            case 3: // Flush
                return bet * 3;
            case 4: // Straight
                return bet * 6;
            case 5: // Three of a Kind
                return bet * 30;
            case 6: // Straight Flush
                return bet * 40;
            default:
                return 0; // Default case, should not be reached
        }
    }

    /**
     * Compares two hands (dealer vs player) after ensuring dealer qualifies.
     * Returns 1 if player wins, 0 if tie, -1 if dealer wins.
     */
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player) {
        int dealerRank = evalHand(dealer); // Evaluate dealer's hand rank
        int playerRank = evalHand(player); // Evaluate player's hand rank

        if (playerRank > dealerRank) {
            return 1; // Player wins
        } else if (playerRank < dealerRank) {
            return -1; // Dealer wins
        } else {
            // Tie-breaking logic based on highest card
            sortHand(player);
            sortHand(dealer);
            for (int i = 2; i >= 0; i--) {
                int pRank = player.get(i).getRank();
                int dRank = dealer.get(i).getRank();
                if (pRank > dRank) return 1; // Player wins with higher card
                else if (pRank < dRank) return -1; // Dealer wins with higher card
            }
            return 0; // Tie if all cards are equal
        }
    }

    /**
     * Checks if the dealer qualifies.
     * Dealer qualifies with Queen high or better (highest card rank >= 12).
     */
    public static boolean dealerQualifies(ArrayList<Card> dealer) {
        sortHand(dealer); // Sort dealer's hand
        int highest = dealer.get(2).getRank(); // Get highest card
        return highest >= 12; // Check if rank is Queen or better
    }

    /**
     * Sorts a hand of cards in ascending order of rank.
     */
    private static void sortHand(ArrayList<Card> hand) {
        hand.sort(Comparator.comparingInt(Card::getRank));
    }

    /**
     * Checks if a hand is a flush (all cards have the same suit).
     */
    private static boolean isFlush(ArrayList<Card> hand) {
        int s = hand.get(0).getSuit();
        return hand.get(1).getSuit() == s && hand.get(2).getSuit() == s;
    }

    /**
     * Checks if a hand is a straight (consecutive ranks).
     */
    private static boolean isStraight(ArrayList<Card> hand) {
        int r1 = hand.get(0).getRank();
        int r2 = hand.get(1).getRank();
        int r3 = hand.get(2).getRank();
        return (r2 == r1 + 1 && r3 == r2 + 1);
    }

    /**
     * Checks if a hand is three of a kind (all cards have the same rank).
     */
    private static boolean isThreeOfKind(ArrayList<Card> hand) {
        int r1 = hand.get(0).getRank();
        int r2 = hand.get(1).getRank();
        int r3 = hand.get(2).getRank();
        return (r1 == r2 && r2 == r3);
    }

    /**
     * Checks if a hand is a pair (two cards have the same rank).
     */
    private static boolean isPair(ArrayList<Card> hand) {
        int r1 = hand.get(0).getRank();
        int r2 = hand.get(1).getRank();
        int r3 = hand.get(2).getRank();
        return (r1 == r2 || r2 == r3 || r1 == r3);
    }
}
