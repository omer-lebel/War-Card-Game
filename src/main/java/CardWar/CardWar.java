package CardWar;

import java.util.ArrayList;

/**
 * The {@code CardWar} class represents the main logic of the card game "War".
 * It manages the deck, players, and game rounds.
 */
public class CardWar {

    /**
     * Enumeration representing the different statuses of a round in the game. <br>
     * When there is a war, draw 3 face-down cards: WAR_CARD1, WAR_CARD2, WAR_CARD3
     */
    public enum RoundStatus {
        NEW_BATTLE,
        WAR_CARD1,
        WAR_CARD2,
        WAR_CARD3,
        WAR_CARD4, //mean a start of a new battle
    }

    private final Deck deck;
    private final ArrayList<Card> table;
    private final Player playerCom;
    private final Player playerUser;

    //round info
    RoundStatus roundStatus;
    private Card cardCom;
    private Card cardUser;
    private Player winner;


    /**
     * Constructs a new CardWar object and initializes the game components.
     * Starts the game automatically.
     */
    CardWar() {
        deck = new Deck();
        playerCom = new Player("computer");
        playerUser = new Player("you");
        table = new ArrayList<>();

        startGame();
    }

    /**
     * Starts a new game by clearing hands, shuffling the deck, and dealing cards to players.
     */
    public void startGame() {
        playerCom.clearHand();
        playerUser.clearHand();
        table.clear();
        roundStatus = RoundStatus.NEW_BATTLE;

        deck.shuffle();
        deck.dealCards(playerCom, playerUser);

    }


    /**
     * Returns the current status of the round.
     * @return the round status
     */
    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    /**
     * Returns the player representing the user.
     * @return the user player
     */
    public Player getPlayerUser() {
        return playerUser;
    }

    /**
     * Returns the player representing the computer.
     * @return the computer player
     */
    public Player getPlayerCom() {
        return playerCom;
    }

    /**
     * Returns the card drawn by the user in the current round.
     * @return the user's card
     */
    public Card getCardUser() {
        return cardUser;
    }

    /**
     * Returns the card drawn by the computer in the current round.
     * @return the computer's card
     */
    public Card getCardCom() {
        return cardCom;
    }

    /**
     * Returns the winner of the current round.
     * @return the winning player, or null if it's a tie (can happen in a war or in the end of the game)
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if both players have no cards, false otherwise
     */
    public boolean gameIsOver() {
        return playerCom.hasNoCards() && playerUser.hasNoCards();
    }

    /**
     * Checks if the game ended in a tie.
     *
     * @return true if both players have no cards, false otherwise
     */
    public boolean isTie() {
        return playerCom.hasNoCards() && playerUser.hasNoCards();
    }


    /**
     * Executes a round of the game.
     *
     * @return true if another round should be executed, false otherwise
     */
    public boolean exeRound() {
        if (gameIsOver() && !isTie()) { //means there is a winner for the game
            return false;
        }

        if (roundStatus == RoundStatus.NEW_BATTLE || roundStatus == RoundStatus.WAR_CARD4) {
            return battle();
        } else {
            return insideWar();
        }
    }

    /**
     * Executes a battle round in the game.
     * Draws cards from players, determines the winner, and updates the game state accordingly.
     *
     * @return true if another round should be executed, false otherwise
     */
    private boolean battle() {
        if (!drawCardsFromPlayers()) {
            return false;
        }

        //war
        if (cardCom.isEquals(cardUser)) {
            winner = null;
            roundStatus = RoundStatus.WAR_CARD1;
            return true; // after war, we always try to exe another round
        }
        //computer won
        else if (cardCom.isGreater(cardUser)) {
            moveCardFromTableToPlayer(playerCom);
            winner = playerCom;
        }
        //user won
        else {
            moveCardFromTableToPlayer(playerUser);
            winner = playerUser;
        }

        roundStatus = RoundStatus.NEW_BATTLE;
        return !gameIsOver();

    }

    /**
     * Draws cards from both players for the round and move them to the table.
     * If one of the players run out of cards, update the winner.
     * @return true if both players have cards and the round can continue, false otherwise
     */
    private boolean drawCardsFromPlayers() {

        cardCom = playerCom.drawCard();
        cardUser = playerUser.drawCard();

        // if both have cards - round can exe
        if (cardCom != null && cardUser != null) {
            table.add(cardCom);
            table.add(cardUser);
            return true;
        }

        // if both run out of card - tie
        if (cardCom == null && cardUser == null) {
            winner = null;
        } else { // if only one of them run out of card - the second wins
            winner = (cardCom != null) ? playerCom : playerUser;
            moveCardFromTableToPlayer(winner);
        }
        return false;
    }

    /**
     * Moves cards from the table to a specified player's hand (which is the winner of the round)
     * @param player the player to whom the cards are moved
     */
    private void moveCardFromTableToPlayer(Player player) {
        while (table.size() > 0) {
            player.addCardsToBottom(table.remove(0));
        }
    }

    /**
     * Executes a one of the 3 war round in the game of drawing reversed cards
     * @return true if another round should be executed, false otherwise
     */
    private boolean insideWar() {

        boolean res = drawCardsFromPlayers();
        if (cardUser != null) {
            cardUser.reverseCard();
        }
        if (cardCom != null) {
            cardCom.reverseCard();
        }

        updateWarStatus();

        return res;
    }

    /**
     * Updates the status of the ongoing war based on the current round status.
     */
    private void updateWarStatus() {
        if (roundStatus == RoundStatus.WAR_CARD1) {
            roundStatus = RoundStatus.WAR_CARD2;
        } else if (roundStatus == RoundStatus.WAR_CARD2) {
            roundStatus = RoundStatus.WAR_CARD3;
        } else { //WAR_CARD3
            roundStatus = RoundStatus.WAR_CARD4;
        }
    }



    /* -------------------------------- testers --------------------------------------- */

    public String showTable() {
        return table.toString();
    }

    // user win
    private void deck1Test() {
        playerCom.addCardToTop(new Card(Rank.FOUR));
        playerUser.addCardToTop(new Card(Rank.ACE));
    }

    // tie in the start of war
    private void deck2Test() {
        playerCom.addCardsToBottom(new Card(Rank.THREE));
        playerUser.addCardsToBottom(new Card(Rank.THREE));
    }

    // tie in the middle of war
    private void deck3Test() {
        playerCom.addCardsToBottom(new Card(Rank.TWO), new Card(Rank.TWO));
        playerUser.addCardsToBottom(new Card(Rank.TWO), new Card(Rank.ACE));
    }

    // winning inside war (user run out of cards)
    private void deck4Test() {
        playerCom.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.TWO), new Card(Rank.THREE));
        playerUser.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.TWO));
    }

}
