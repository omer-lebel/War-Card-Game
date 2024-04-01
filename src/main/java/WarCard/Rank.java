package WarCard;

/**
 * The {@code Rank} enum represents the ranks of playing cards.
 * Each rank has an associated integer value that determines its place in the order ratio of the card game.
 */
public enum Rank {
    ACE(14), TWO(2), THREE(3), FOUR(4)
    ,JACK(11);

//    , FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
//    TEN(10), JACK(11), QUEEN(12), KING(13);

    private final int value;

    /**
     * Constructs a new Rank with the specified integer value.
     * @param value the integer value determining the rank's place in the order ratio
     */
    Rank(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value that determines its rank
     * @return the integer value of the rank
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the string representation of this rank.
     * For numeric ranks (2 to 10) -  numeric value.
     * For face cards (JACK, QUEEN, KING, ACE) - its names in lowercase.
     * @return the string representation of the rank
     */
    @Override
    public String toString() {
        if (value < Rank.JACK.value) {
            return String.valueOf(value);
        }
        return this.name().toLowerCase();
    }
}
