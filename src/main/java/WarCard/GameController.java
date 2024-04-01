package WarCard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * The {@code GameController} class manages the logic and UI interactions of the Card War game. <br>
 * It handles staring and ending of new games, updates players score and manages game state.
 */
public class GameController {
    private static boolean newGame = true;
    private static boolean gameIsOn = false;
    private static boolean gameEnded = false;

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
        displayCardsBack("back", "back");
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

    private void runGame() {
        if (gameIsOn) {
            handleGameRound();

        } else {
            handleGameEnd();
        }
    }

    private void handleGameRound(){
        gameIsOn = warCard.exeRound();

        displayCards();

        if (warCard.getWinner() != null && !warCard.insideWar()) {
            lblRoundRes.setText(warCard.getWinner() + " won the round");
        } else {
            switch (warCard.getWarCardsCount()){
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


    private void handleGameEnd(){
        gameEnded = true;
        lblInstructions.setText("press next to see game's score summary");


        if (warCard.getWinner() == null) {
            lblRoundRes.setText("It's a tie");
            displayCardsBack(null, null); //don't show any card
            warCard.getPlayerCom().incScore();
            warCard.getPlayerUser().incScore();
        }
        else { //there is a winner
            lblRoundRes.setText(warCard.getWinner().getName().toUpperCase() + " WON THE GAME");
            warCard.getWinner().incScore();

            if (warCard.getPlayerCom().hasNoCards()) { //user won
                displayCardsBack(null, "back");
            } else { //player won
                displayCardsBack("back", null);
            }
        }
    }


    private void showSummery() {
        lblRoundRes.setText("would you like to play again?");
        lblInstructions.setText("press next to start a new game");

        lblComName.setText("computer: " + warCard.getPlayerCom().getScore());
        lblUserName.setText("you: " + warCard.getPlayerUser().getScore());

        displayCardsBack("back", "back");

        newGame = true;
    }


    private Image getCardImg(Card card) {

        if (card == null) {
            return null;
        }

        return getCardImg(card.toString().replace(' ', '_'));
    }


    private Image getCardImg(String cardName) {
        String s = File.separator;
        String path = "file:" + s + s + s + System.getProperty("user.dir") + s + "src" + s + "main" + s + "java" + s
                + "WarCard" + s + "cardImg" + s + cardName + ".png";

//        String path = "file:" + s + s + s + System.getProperty("user.dir") + s + cardName + ".png";

        return new Image(path);
    }

    private void displayCards() {
        displayCard(imgComCard, warCard.getCardCom());
        displayCard(imgUserCard, warCard.getCardUser());
    }

    private void displayCard(ImageView imageView, Card card) {
        if (card == null){
            imageView.setImage(getCardImg("out_of_cards"));
        }
        else if (!card.isReverse()) {
            imageView.setImage(getCardImg(card));
        } else {
            imageView.setImage(getCardImg("back"));
            card.reverseFace();
        }
    }

    private void displayCardsBack(String backComCard, String backUserCard) {
        displayCardBack(imgComCard, backComCard);
        displayCardBack(imgUserCard, backUserCard);
    }

    private void displayCardBack(ImageView imageView, String back) {
        if (back == null){
            imageView.setImage(null);
        }else{
            imageView.setImage(getCardImg("back"));
        }
    }
}
