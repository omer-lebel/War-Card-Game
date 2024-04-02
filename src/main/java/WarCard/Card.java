package WarCard;

/**
 * The Card class represents a single playing card with a rank and a suit.
 */
public class Card {
    private final Rank rank;
    private final Suit suit;
    private boolean faceDown;

    /**
     * Constructs a Card object with a specified rank and suit, facing up by default.
     * @param rank The rank of the card.
     * @param suit The suit of the card.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.faceDown = false;
    }

    /**
     * Constructs a Card object with a specified rank and default suit (hearts), facing up by default.
     * This constructor is mainly for testing purposes.
     * @param rank The rank of the card.
     */
    public Card(Rank rank) { //for testers
        this(rank, Suit.HEARTS);
    }

    /**
     * Returns the rank of the card.
     * @return Card's rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Return the suit of the card
     * @return Card's suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Checks if the card is facing down.
     * @return True if the card is facing down, false otherwise.
     */
    public boolean isFaceDown(){
        return faceDown;
    }

    /**
     * Sets the card to face down.
     */
    public void setFaceDown(){
        this.faceDown = true;
    }

    /**
     * Sets the card to face up.
     */
    public void setFaceUp(){
        this.faceDown = false;
    }

    /**
     * Returns a string representation of the card.
     * @return A string representing the card, including its rank and suit.
     */
    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }

    /**
     * Checks if this card's rank is greater than the rank of another card.
     * @param other The other card to compare.
     * @return True if this card's rank is greater, false otherwise.
     */
    public boolean isGreater(Card other) {
        return this.rank.getValue() > other.rank.getValue();
    }

    /**
     * Checks if this card's rank is equal to the rank of another card.
     * @param other The other card to compare.
     * @return True if this card's rank is equal, false otherwise.
     */
    public boolean isEquals(Card other) {
        return this.rank == other.rank;
    }
}
