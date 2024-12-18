/**
 * Represents the current game state, including bets, winnings, and game information.
 */
public class GameState {
    private int totalWinnings = 0;       // Total winnings of the player
    private int anteBet = 0;             // Current ante bet amount
    private int pairPlusBet = 0;         // Current Pair Plus bet amount
    private int playBet = 0;             // Current play bet amount
    private PokerInfo currentInfo;       // Current game information (e.g., hands, results)

    /**
     * Gets the total winnings.
     * @return The total winnings.
     */
    public int getTotalWinnings() { return totalWinnings; }

    /**
     * Adds an amount to the total winnings.
     * @param amount The amount to add.
     */
    public void addWinnings(int amount) { totalWinnings += amount; }

    /**
     * Resets the total winnings to zero.
     */
    public void resetWinnings() { totalWinnings = 0; }

    /**
     * Gets the current ante bet.
     * @return The ante bet amount.
     */
    public int getAnteBet() { return anteBet; }

    /**
     * Sets the ante bet amount.
     * @param anteBet The ante bet amount to set.
     */
    public void setAnteBet(int anteBet) { this.anteBet = anteBet; }

    /**
     * Gets the current Pair Plus bet.
     * @return The Pair Plus bet amount.
     */
    public int getPairPlusBet() { return pairPlusBet; }

    /**
     * Sets the Pair Plus bet amount.
     * @param pairPlusBet The Pair Plus bet amount to set.
     */
    public void setPairPlusBet(int pairPlusBet) { this.pairPlusBet = pairPlusBet; }

    /**
     * Gets the current play bet.
     * @return The play bet amount.
     */
    public int getPlayBet() { return playBet; }

    /**
     * Sets the play bet amount.
     * @param playBet The play bet amount to set.
     */
    public void setPlayBet(int playBet) { this.playBet = playBet; }

    /**
     * Gets the current game information.
     * @return The current PokerInfo object.
     */
    public PokerInfo getCurrentInfo() { return currentInfo; }

    /**
     * Sets the current game information.
     * @param info The PokerInfo object to set.
     */
    public void setCurrentInfo(PokerInfo info) { this.currentInfo = info; }
}
