package CardWar;

import java.util.ArrayList;

/**
 * The {@code Player} class represents a player in the card game.
 * Each player has a name, a hand of cards, and a score.
 */
public class Player {

    private String name;
    private ArrayList<Card> hand;
    private int score;

    /**
     * Constructs a new Player object with the specified name.
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    /**
     * Returns the name of the player.
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the string representation of the player's hand.
     * @return player's hand represent as string
     */
    public String getHand(){
        return hand.toString();
    }

    /**
     * Returns the score of the player.
     * @return player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Increases the player's score by 1.
     */
    public void incScore() {
        ++score;
    }

    /**
     * Adds a card to the top of the player's hand.
     * @param card the card to add to the top of the hand
     */
    public void addCardToTop(Card card) {
        hand.add(0, card);
    }

    /**
     * Adds cards to the bottom of the player's hand.
     * @param cards the cards to add to the bottom of the hand
     */
    public void addCardsToBottom(Card ... cards) {
        for (Card card : cards){
            hand.add(hand.size(), card);
        }
    }

    /**
     * Draws a card from the top of the player's hand.
     * @return the card drawn from the top of the hand, or null if the hand is empty
     */
    public Card drawCard() {
        if (!hand.isEmpty()) {
            return this.hand.remove(0);
        }
        return null; //hand is empty
    }

    /**
     * Checks if the player has no cards in their hand.
     * @return true if the player has no cards, false otherwise
     */
    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    /**
     * Clears the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Returns the string representation of the player, which is tha player's name.
     * @return the name of the player
     */
    @Override
    public String toString() {
        return name;
    }

}
