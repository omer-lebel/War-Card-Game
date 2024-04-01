package WarCard;

import java.util.ArrayList;

/**
 * The {@code WarCard} class represents the main logic of the card game "War".
 * It manages the deck, players, and game rounds.
 */
public class WarCard {

    private final Deck deck;
    private final ArrayList<Card> table;
    private final Player playerCom;
    private final Player playerUser;
    private boolean insideWar;
    private int warCardsCount; //count the 3 cards that we put after each war;

    //round info
    private Card cardCom;
    private Card cardUser;
    private Player winner;

    private int testCount;


    /**
     * Constructs a new WarCard object and initializes the game components.
     */
    WarCard() {
        deck = new Deck();
        playerCom = new Player("computer");
        playerUser = new Player("you");
        table = new ArrayList<>();

        testCount = 0;
    }

    /**
     * Starts a new game by clearing hands, shuffling the deck, and dealing cards to players.
     */
    public void startGame() {
        playerCom.clearHand();
        playerUser.clearHand();
        table.clear();
        insideWar = false;

        deck.shuffle();
        deck.dealCards(playerCom, playerUser);
//        test(-1);
    }

    /**
     * Executes a round of the game.
     * @return true if another round should be executed, false otherwise
     */
    public boolean exeRound() {
        if (insideWar) {
            return updateWarStatus();
        } else {
            return newBattle();
        }
    }

    /**
     * Executes a battle round in the game.
     * Draw cards from players, determines the winner, and updates the game state accordingly.
     * @return true if another round should be executed, false otherwise
     */
    private boolean newBattle() {
        if (!drawCardsFromPlayers()) {
            return false;
        }

        if (cardCom.isEquals(cardUser)){ // war
            winner = null;
            warCardsCount = 0;
            insideWar = true;
            return true;
        }
        else{
            if (cardCom.isGreater(cardUser)) { //computer won
                moveCardFromTableToPlayer(playerCom);
                winner = playerCom;
            }
            else { //user won
                moveCardFromTableToPlayer(playerUser);
                winner = playerUser;
            }
            warCardsCount = -1;
            return !gameIsOver();
        }
    }

    /**
     * Updates the status of the ongoing war based on the current round status.
     * @return true if another round should be executed, false otherwise
     */
    private boolean updateWarStatus() {
        boolean res = drawCardsFromPlayers();
        warCardsCount++;
        if (warCardsCount == 3) {
            insideWar = false;
        }
        return res;
    }

    /**
     * Draws cards from both players for the round and move them to the table.
     * If one of the players run out of cards, update the winner.
     *
     * @return true if both players have cards and the round can continue, false otherwise
     */
    private boolean drawCardsFromPlayers() {

        cardCom = playerCom.drawCard();
        cardUser = playerUser.drawCard();

        if (insideWar) {
            faceDownCards(cardCom, cardUser);
        }

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
     *
     * @param player the player to whom the cards are moved
     */
    private void moveCardFromTableToPlayer(Player player) {
        while (table.size() > 0) {
            player.addCardsToBottom(table.remove(0));
        }
    }

    private void faceDownCards(Card... cards) {
        for (Card card : cards) {
            if (card != null) {
                card.reverseFace();
            }
        }
    }

    /**
     * Returns the current card on the table as string
     *
     * @return string representation of the table
     */
    public String getTable() {
        return table.toString();
    }

    /**
     * Returns the current status of the round.
     *
     * @return the round status
     */
    public int getWarCardsCount() {
        return warCardsCount;
    }

    /**
     * Returns the player representing the user.
     *
     * @return the user player
     */
    public Player getPlayerUser() {
        return playerUser;
    }

    /**
     * Returns the player representing the computer.
     *
     * @return the computer player
     */
    public Player getPlayerCom() {
        return playerCom;
    }

    /**
     * Returns the card drawn by the user in the current round.
     *
     * @return the user's card
     */
    public Card getCardUser() {
        return cardUser;
    }

    /**
     * Returns the card drawn by the computer in the current round.
     *
     * @return the computer's card
     */
    public Card getCardCom() {
        return cardCom;
    }

    /**
     * Returns the winner of the current round.
     *
     * @return the winning player, or null if it's a tie (can happen in a war or in the end of the game)
     */
    public Player getWinner() {
        return winner;
    }

    // return true if the last round was inside a war
    // (despite the boolean insideWar that determinate whether the next round is inside war)
    public boolean insideWar(){
        return warCardsCount > 0;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if one of the players have no cards, false otherwise
     */
    private boolean gameIsOver() {
        return playerUser.hasNoCards() || playerCom.hasNoCards();
    }

    private boolean isTie() {
        return playerCom.hasNoCards() && playerUser.hasNoCards();
    }




    /* -------------------------------- testers --------------------------------------- */

    private void test(int request) {
        int numOftest = 7;
        // user win
        if ((testCount % numOftest == 0 && request < 0) || request == 0) {
            System.out.println("user win test");
            playerCom.addCardsToBottom(new Card(Rank.TWO));
            playerUser.addCardsToBottom(new Card(Rank.ACE));
        }

        // tie in the start of war
        else if ((testCount % numOftest == 1 && request < 0) || request == 1) {
            System.out.println("tie in the start of war test");
            playerCom.addCardsToBottom(new Card(Rank.THREE));
            playerUser.addCardsToBottom(new Card(Rank.THREE));
        }

        // tie in the middle of war
        else if ((testCount % numOftest == 2 && request < 0) || request == 2) {
            System.out.println("tie in the middle of war test");
            playerCom.addCardsToBottom(new Card(Rank.TWO), new Card(Rank.TWO));
            playerUser.addCardsToBottom(new Card(Rank.TWO), new Card(Rank.ACE));
        }

        // computer wins in the middle of a war (in the first card) test
        else if ((testCount % numOftest == 3 && request < 0) || request == 3) {
            System.out.println("computer wins in the middle of a war (in the first card) test");
            playerCom.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.TWO), new Card(Rank.THREE));
            playerUser.addCardsToBottom(new Card(Rank.FOUR));
        }

        // computer wins in the middle of a war (in the second card) test
        else if ((testCount % numOftest == 4 && request < 0) || request == 4) {
            System.out.println("computer wins in the middle of a war (in the second card) test");
            playerCom.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.TWO), new Card(Rank.THREE));
            playerUser.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE));
        }

        // computer win in the middle of a war (in the third card) test
        else if ((testCount % numOftest == 5 && request < 0) || request == 5) {
            System.out.println("computer win in the middle of a war (in the third card) test ");
            playerCom.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.TWO), new Card(Rank.THREE));
            playerUser.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.ACE));
        }

        // computer win in the start of a new war after war test
        else if ((testCount % numOftest == 6 && request < 0) || request == 6) {
            System.out.println("computer win in the start of a new war after war test");
            playerCom.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.TWO), new Card(Rank.ACE), new Card(Rank.ACE));
            playerUser.addCardsToBottom(new Card(Rank.FOUR), new Card(Rank.ACE), new Card(Rank.ACE), new Card(Rank.ACE));
        }

        if (request < 0) {
            testCount++;
        }
    }
}
