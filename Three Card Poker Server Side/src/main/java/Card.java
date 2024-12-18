import java.io.Serializable;

/**
 * Represents a single playing card with a rank and suit.
 * Ranks: 2=2,...10=10,11=J,12=Q,13=K,14=A
 * Suits: 1=Clubs,2=Diamonds,3=Hearts,4=Spades
 */
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    // Rank of the card (2-14, where 11=J, 12=Q, 13=K, 14=A)
    private int rank;

    // Suit of the card (1=Clubs, 2=Diamonds, 3=Hearts, 4=Spades)
    private int suit;

    /**
     * Constructs a Card with the specified rank and suit.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Returns a string representation of the card, e.g., "A of Spades".
     *
     * @return the string representation of the card
     */
    @Override
    public String toString() {
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        String[] suits = {"Clubs","Diamonds","Hearts","Spades"};
        String r = ranks[rank - 2];
        String s = suits[suit - 1];
        return r + " of " + s;
    }
}
