package WarCard;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Deck class represents a deck of playing cards.
 * It contains methods for initializing, shuffling, dealing cards, and displaying the deck.
 */
public class Deck {
    private final ArrayList<Card> deck;

    /**
     * Constructs a new Deck object and initializes it with a standard deck of 52 cards.
     */
    public Deck() {
        deck = new ArrayList<>();
        initDeck();
    }

    /**
     * Initializes the deck with a standard set of 52 playing cards.
     */
    private void initDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(rank, suit);
                deck.add(card);
            }
        }
    }
    /**
     * Returns the deck of cards.
     * @return An ArrayList containing the cards in the deck.
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Random random = new Random();
        for (int first = 0; first < deck.size(); ++first) {
            int second = random.nextInt(deck.size());
            swap(first, second);
        }
    }

    /**
     * Swaps two cards in the deck.
     * @param i the index of the first card to swap
     * @param j the index of the second card to swap
     */
    private void swap(int i, int j) {

        Card tmp = deck.get(i);
        deck.set(i, deck.get(j));
        deck.set(j, tmp);
    }

    /**
     * Deals cards from the deck to two players.
     * Cards are distributed equally between the players.
     * @param player1 the first player to receive cards
     * @param player2 the second player to receive cards
     */
    public void dealCards(Player player1, Player player2) {
        int cardsPerPlayer = deck.size() / 2;
        for (int i = 0; i < cardsPerPlayer ; ++i) {
                player1.addCardToTop(deck.get(i));
                player2.addCardToTop(deck.get(i + cardsPerPlayer));
        }
    }

    /**
     * Returns a string representation of the deck.
     * @return a string containing the cards in the deck
     */
    @Override
    public String toString() {
        return deck.toString();
    }
}


