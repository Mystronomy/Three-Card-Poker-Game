import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

/**
 * A standard deck of 52 playing cards.
 */
public class Deck implements Serializable {
    private static final long serialVersionUID = 1L;

    // List holding the cards in the deck
    private List<Card> cards;

    /**
     * Constructs a new Deck with 52 standard playing cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        // Initialize deck with all suits and ranks
        for (int s = 1; s <= 4; s++) { // Iterate through suits
            for (int r = 2; r <= 14; r++) { // Iterate through ranks
                cards.add(new Card(r, s)); // Add new card to deck
            }
        }
    }

    /**
     * Shuffles the deck randomly.
     */
    public void shuffle() {
        Collections.shuffle(cards); // Shuffle the list of cards
    }

    /**
     * Deals a single card from the top of the deck.
     *
     * @return the dealt Card, or null if the deck is empty
     */
    public Card dealCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); // Remove and return the top card
        }
        return null; // No cards left to deal
    }

    /**
     * Deals a hand of three cards.
     *
     * @return a list containing three dealt Cards
     */
    public List<Card> dealHand() {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            hand.add(dealCard()); // Add dealt card to the hand
        }
        return hand; // Return the completed hand
    }
}
