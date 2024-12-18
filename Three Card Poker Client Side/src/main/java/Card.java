import java.io.Serializable;

/**
 * Represents a playing card with a rank and suit.
 */
public class Card implements Serializable {
    private static final long serialVersionUID = 1L; // Serialization ID for object persistence
    private int rank; // Rank of the card (2-14, where 11=J, 12=Q, 13=K, 14=A)
    private int suit; // Suit of the card (1=Clubs, 2=Diamonds, 3=Hearts, 4=Spades)

    /**
     * Constructs a Card with the specified rank and suit.
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of the card.
     * @return The rank.
     */
    public int getRank() { return rank; }

    /**
     * Returns the suit of the card.
     * @return The suit.
     */
    public int getSuit() { return suit; }

    /**
     * Provides a string representation of the card, e.g., "A of Spades".
     * @return The string representation of the card.
     */
    @Override
    public String toString() {
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"}; // Mapping of rank values to names
        String[] suits = {"Clubs","Diamonds","Hearts","Spades"}; // Mapping of suit values to names
        String r = ranks[rank-2]; // Adjust rank to index in array
        String s = suits[suit-1]; // Adjust suit to index in array
        return r + " of " + s; // Combine rank and suit for display
    }
}
