package WarCard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The GameController class manages the game logic and user interface for the War Card game.
 */
public class GameController {
    private boolean newGame = true;
    private boolean gameIsOn = false;
    private boolean gameEnded = false;

    private final int FACE_DOWN = 1;
    private final int DONT_SHOW_CARD = 2;
    private final int OUT_OF_CARDS = 3;

    private WarCard warCard;

    @FXML
    private ImageView imgComCard;

    @FXML
    private ImageView imgUserCard;

    @FXML
    private Label lblComName;

    @FXML
    private Label lblInstructions;

    @FXML
    private Label lblRoundRes;

    @FXML
    private Label lblUserName;

    @FXML
    void onNextBtmPressed(ActionEvent event) {
        if (newGame) {
            startNewGame();
        } else if (gameEnded) {
            showSummery();
        } else {
            runGame();
        }
    }

    /**
     * Initializes the GameController.
     * Creates a new instance of the WarCard class and displays face down cards in the welcome screen
     */
    public void initialize() {
        warCard = new WarCard();
        displaySpecialCards(FACE_DOWN, FACE_DOWN);
    }

    /**
     * Starts a new game by resetting game state and scene, and starting the game.
     */
    private void startNewGame() {
        newGame = false;
        gameEnded = false;
        gameIsOn = true;

        lblUserName.setText("you");
        lblComName.setText("computer");
        lblInstructions.setText("press next to draw the next card");

        warCard.startGame();
        runGame();
    }

    /**
     * Runs the game logic.
     * Checks if the game is still ongoing and proceeds to handle the game accordingly.
     */
    private void runGame() {
        if (gameIsOn) {
            handleGameRound();

        } else {
            handleGameEnd();
        }
    }

    /**
     * Handles the logic for each round of the game.
     * Executes a round of the game and updates the game interface accordingly.
     */
    private void handleGameRound() {
        gameIsOn = warCard.exeRound();

        displayCards();

        if (warCard.getWinner() != null && !warCard.insideWar()) {
            lblRoundRes.setText(warCard.getWinner() + " won the round");
        } else {
            switch (warCard.getWarCardsCount()) {
                case 0:
                    lblRoundRes.setText("war!");
                    break;
                case 1:
                    lblRoundRes.setText("war! 1...");
                    break;
                case 2:
                    lblRoundRes.setText("war! 1... 2...");
                    break;
                case 3:
                    lblRoundRes.setText("war! 1... 2... 3...");
                    break;
                default:
                    lblRoundRes.setText("error");
            }
        }
    }

    /**
     * Handles the end of the game by updating the game state and displaying the game summary.
     */
    private void handleGameEnd() {
        gameEnded = true;
        lblInstructions.setText("press next to see game's score summary");

        // tie
        if (warCard.getWinner() == null) {
            lblRoundRes.setText("It's a tie");
            displaySpecialCards(DONT_SHOW_CARD, DONT_SHOW_CARD);
        }
        // there is a winner
        else {
            lblRoundRes.setText(warCard.getWinner().getName().toUpperCase() + " WON THE GAME");
            if (warCard.getPlayerCom().hasNoCards()) { //user won
                displaySpecialCards(DONT_SHOW_CARD, FACE_DOWN);
            } else { //player won
                displaySpecialCards(FACE_DOWN, FACE_DOWN);
            }
        }
    }

    /**
     * Displays the game summary including player scores and prompts for starting a new game.
     */
    private void showSummery() {
        lblRoundRes.setText("\tyou: " + warCard.getPlayerUser().getScore() +
                "\t\tcomputer: " + warCard.getPlayerCom().getScore());
        lblInstructions.setText("would you like to play again? press next");

        lblComName.setText("");
        lblUserName.setText("");

        displaySpecialCards(FACE_DOWN, FACE_DOWN);

        newGame = true;
    }


    /**
     * Returns the image of a card based on the provided card object.
     *
     * @param card The card object for which to retrieve the image.
     * @return The image corresponding to the provided card object.
     */
    private Image getCardImg(Card card) {

        if (card == null) {
            return null;
        }
        return getCardImg(card.toString().replace(' ', '_'));
    }

    /**
     * Returns the image of a card based on its name.
     * Searches for the card image in the resources directory based on the card name.
     *
     * @param cardName The name of the card for which to retrieve the image.
     * @return The image corresponding to the card name.
     */
    private Image getCardImg(String cardName) {

        return new Image(getClass().getResource("cardImg/" + cardName + ".png").toString());
    }

    /**
     * Displays the cards drawn by players during the game round.
     */
    private void displayCards() {
        displayCard(imgComCard, warCard.getCardCom());
        displayCard(imgUserCard, warCard.getCardUser());
    }

    /**
     * Displays a single card image in the specified ImageView.
     *
     * @param imageView The ImageView in which to display the card image.
     * @param card      The card for which to display the image.
     */
    private void displayCard(ImageView imageView, Card card) {
        // player run out of card
        if (card == null) {
            displaySpecialCard(imageView, OUT_OF_CARDS);
        }
        //one of the 3 war cards
        else if (card.isFaceDown()) {
            displaySpecialCard(imageView, FACE_DOWN);
            card.setFaceUp(); // turns the card back over
        }
        // regular card
        else {
            imageView.setImage(getCardImg(card));
        }
    }

    /**
     * Displays cards in special state: reversed card, or no card at all
     * (in the end or start of the game)
     *
     * @param comCardState  The state of the computer's card (NO_CARD or FACE_DOWN).
     * @param userCardState The state of the user's card (NO_CARD or FACE_DOWN).
     */
    private void displaySpecialCards(int comCardState, int userCardState) {
        displaySpecialCard(imgComCard, comCardState);
        displaySpecialCard(imgUserCard, userCardState);
    }

    private void displaySpecialCard(ImageView imageView, int cardState) {
        if (cardState == DONT_SHOW_CARD){
            imageView.setImage(null);
        }
        if (cardState == FACE_DOWN){
            imageView.setImage(getCardImg("back"));
        }
        if (cardState == OUT_OF_CARDS){
            imageView.setImage(getCardImg("out_of_cards"));
        }
    }
}

