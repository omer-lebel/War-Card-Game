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

    //round info
    private Player winner;          // winner of the round (or the game if ended)
    private Card cardCom;           // the card drawn by the computer in the current round
    private Card cardUser;          // the card drawn by the user in the current round
    private int warCardsCount;      // counts the 3 cards that draw after each war
    private boolean insideWar;      // indicate if the *next* round will be inside war

    private int testCount;          // for control of the test func


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
//        deck.dealCards(playerCom, playerUser);
        test(-1);
    }

    /**
     * Executes a round of the game. If the game is over, update the players score
     * @return true if another round should be executed, false otherwise
     */
    public boolean exeRound() {
        boolean res;
        if (insideWar) {
            res = updateWarStatus();
        } else {
            res = newBattle();
        }

        if (!res){
            updateScore();
        }
        return res;
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
     * Updates the number of cards drawn from the 3 cards that are drawn in each war
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

    /**
     * Sets the face-down state for the specified cards.
     * @param cards The cards to set face-down.
     */
    private void faceDownCards(Card... cards) {
        for (Card card : cards) {
            if (card != null) {
                card.setFaceDown();
            }
        }
    }

    /**
     * Increases the score of the winner of the game, or for both in a tie.
     */
    private void updateScore(){
        if (winner == playerCom || winner == null){
            playerCom.incScore();
        }
        if (winner == playerUser || winner == null){
            playerUser.incScore();
        }
    }

    /**
     * Checks if the last round was part of war (the first or one of the extra 3 faced down cards)
     *
     * @return True if the last round wss inside a war, false otherwise.
     */
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

    /**
     * Returns string representation of the cards currently on the table.
     * @return string representation of the table
     */
    public String getTable() {
        return table.toString();
    }

    /**
     * Retrieves the count of additional cards drawn in the current war.
     *
     * The count includes:
     * - 0: The initial round that caused the war.
     * - 1, 2, 3: The three additional cards drawn during the war.
     *
     * @return The count of additional cards drawn in the current war.
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
     * @return the winning player, or null if it's a tie (happened only in war)
     */
    public Player getWinner() {
        return winner;
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
