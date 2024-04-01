package WarCard;

/**
 * The {@code Suit} enum represents the suits of playing cards.
 * Each suit represents a category of cards in the card game.
 */
public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    /**
     * Returns the lowercase string representation of this suit.
     * @return the string representation of the suit
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
