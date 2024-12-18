import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class ThreeCardLogicTest {

    @Test
    void testEvalHandHighCard() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(2,1));
        hand.add(new Card(5,2));
        hand.add(new Card(9,3));
        int rank = ThreeCardLogic.evalHand(hand);
        assertEquals(1, rank); // High card
    }

    @Test
    void testEvalHandPair() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(10,1));
        hand.add(new Card(10,2));
        hand.add(new Card(5,3));
        int rank = ThreeCardLogic.evalHand(hand);
        assertEquals(2, rank); // Pair
    }

    @Test
    void testEvalHandFlush() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(2,3));
        hand.add(new Card(6,3));
        hand.add(new Card(9,3));
        int rank = ThreeCardLogic.evalHand(hand);
        assertEquals(3, rank); // Flush
    }

    @Test
    void testEvalHandStraight() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(4,1));
        hand.add(new Card(5,2));
        hand.add(new Card(6,4));
        int rank = ThreeCardLogic.evalHand(hand);
        assertEquals(4, rank); // Straight
    }

    @Test
    void testEvalHandThreeOfKind() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(7,1));
        hand.add(new Card(7,2));
        hand.add(new Card(7,3));
        int rank = ThreeCardLogic.evalHand(hand);
        assertEquals(5, rank); // Three of a Kind
    }

    @Test
    void testEvalHandStraightFlush() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(5,4));
        hand.add(new Card(6,4));
        hand.add(new Card(7,4));
        int rank = ThreeCardLogic.evalHand(hand);
        assertEquals(6, rank); // Straight Flush
    }

    @Test
    void testEvalPPWinnings() {
        ArrayList<Card> pairHand = new ArrayList<>();
        pairHand.add(new Card(10,1));
        pairHand.add(new Card(10,2));
        pairHand.add(new Card(5,3));
        // Pair = 2 rank
        assertEquals(10, ThreeCardLogic.evalPPWinnings(pairHand, 10));

        ArrayList<Card> flushHand = new ArrayList<>();
        flushHand.add(new Card(2,3));
        flushHand.add(new Card(6,3));
        flushHand.add(new Card(9,3));
        // Flush = rank 3 = 3 to 1 payout
        assertEquals(30, ThreeCardLogic.evalPPWinnings(flushHand, 10));
    }

    @Test
    void testCompareHands() {
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(10,1));
        dealer.add(new Card(10,2));
        dealer.add(new Card(4,3));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(11,1)); // J
        player.add(new Card(11,2)); // J
        player.add(new Card(5,4));

        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(1, res); // Player should win since Jacks outrank Tens
    }

    @Test
    void testDealerQualifies() {
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(12,2)); // Q
        dealer.add(new Card(9,4));
        dealer.add(new Card(5,1));
        assertTrue(ThreeCardLogic.dealerQualifies(dealer));
    }

    // Additional 11 test cases

    @Test
    void testEvalPPWinningsNoWinnings() {
        // High card only, no pair plus winnings
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(2,1));
        hand.add(new Card(3,2));
        hand.add(new Card(8,3));
        assertEquals(0, ThreeCardLogic.evalPPWinnings(hand, 10));
    }

    @Test
    void testEvalPPWinningsStraightFlush() {
        // Straight flush = 40 to 1
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(5,4));
        hand.add(new Card(6,4));
        hand.add(new Card(7,4));
        assertEquals(400, ThreeCardLogic.evalPPWinnings(hand, 10));
    }

    @Test
    void testCompareHandsTieSameHighCard() {
        // Both high card hands with same exact cards
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(2,1));
        dealer.add(new Card(5,2));
        dealer.add(new Card(9,3));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(2,4));
        player.add(new Card(5,1));
        player.add(new Card(9,2));

        // Sort doesn't care about suits, only ranks when tied
        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(0, res); // Tie
    }

    @Test
    void testCompareHandsDealerWins() {
        // Dealer has a pair, player has high card
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(10,1));
        dealer.add(new Card(10,3));
        dealer.add(new Card(4,2));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(2,1));
        player.add(new Card(7,2));
        player.add(new Card(8,3));

        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(-1, res); // Dealer wins
    }

    @Test
    void testDealerQualifiesLow() {
        // Dealer highest card less than Q should not qualify
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(11,4)); // J high only
        dealer.add(new Card(5,1));
        dealer.add(new Card(3,2));
        assertFalse(ThreeCardLogic.dealerQualifies(dealer));
    }

    @Test
    void testDealerQualifiesHighAce() {
        // Dealer has Ace high
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(14,4)); // A
        dealer.add(new Card(2,1));
        dealer.add(new Card(3,2));
        assertTrue(ThreeCardLogic.dealerQualifies(dealer));
    }

    @Test
    void testCompareHandsFlushTie() {
        // Both flush, check highest card
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(2,3));
        dealer.add(new Card(6,3));
        dealer.add(new Card(9,3));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(2,4));
        player.add(new Card(7,4));
        player.add(new Card(9,4));

        // Compare flushes: dealer highest 9, player highest 9, next highest dealer 6 vs player 7, player should win
        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(1, res); // Player wins due to higher middle card
    }

    @Test
    void testCompareHandsSameStraight() {
        // Same straight, exact ranks -> tie
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(4,1));
        dealer.add(new Card(5,2));
        dealer.add(new Card(6,3));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(6,4));
        player.add(new Card(5,1));
        player.add(new Card(4,4));

        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(0, res); // Tie on identical straights
    }

    @Test
    void testCompareHandsSameThreeOfKind() {
        // Both have three of a kind of the same rank
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(7,1));
        dealer.add(new Card(7,2));
        dealer.add(new Card(7,3));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(7,4));
        player.add(new Card(7,1));
        player.add(new Card(7,2));

        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(0, res); // Exactly the same three of a kind
    }

    @Test
    void testCompareHandsSamePairDifferentKicker() {
        // Both have a pair of 10s, compare kickers
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(10,1));
        dealer.add(new Card(10,2));
        dealer.add(new Card(4,3));

        ArrayList<Card> player = new ArrayList<>();
        player.add(new Card(10,1));
        player.add(new Card(10,3));
        player.add(new Card(5,4));

        // Player kicker is 5 vs Dealer kicker 4
        int res = ThreeCardLogic.compareHands(dealer, player);
        assertEquals(1, res); // Player wins due to higher kicker
    }

    @Test
    void testDealerQualifiesBoundary() {
        // Dealer highest card is exactly Q (12)
        ArrayList<Card> dealer = new ArrayList<>();
        dealer.add(new Card(12,4)); // Q
        dealer.add(new Card(3,1));
        dealer.add(new Card(2,2));
        assertTrue(ThreeCardLogic.dealerQualifies(dealer));
    }

}
