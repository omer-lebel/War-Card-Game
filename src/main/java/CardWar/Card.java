
package CardWar;

import javafx.scene.image.Image;

public class Card {
    private final Rank rank;
    private final Suit suit;
    private boolean reverse;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        this.reverse = false;
    }

    public Card(Rank rank) { //for testers
        this(rank, Suit.HEARTS);
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isReverse(){
        return reverse;
    }

    public void reverseCard(){
        this.reverse = !this.reverse;
    }

    @Override
    public String toString() {
        return rank.toString() + " of " + suit.toString();
    }

    boolean isGreater(Card other) {
        return this.rank.getValue() > other.rank.getValue();
    }

    Boolean isLess(Card other) {
        return other.isGreater(this);
    }

    boolean isEquals(Card other) {
        return this.rank == other.rank;
    }
}
