package com.example.cribbagegame.CribGame;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cribbagegame.GameFramework.GameMainActivity;
import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
import com.example.cribbagegame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.cribbagegame.GameFramework.players.GameHumanPlayer;
import com.example.cribbagegame.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * CribHumanPlayer -
 * Updates all TextView's and ImageButtons that can be interacted with on screen,
 * as well as allow users to interact with all Buttons and ImageButtons available.
 *
 * @author Aaron, Aether, Sean, Kincaid
 * @version March 2023
 */
public class CribHumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    private TextView playerScoreTextValue = null;
    private String playerScore;
    private TextView oppScoreTextValue = null;
    private String oppScore;
    private TextView playerScoreTextView = null;
    private String playerScoreText;
    private TextView oppScoreTextView = null;
    private String oppScoreText;
    private TextView roundTotalScoreView = null;
    private String roundScoreText;
    private TextView cribTextView = null;
    private String cribOwnerText;
    private TextView messageView = null;
    private TextView secondaryMessage = null;
    private String primaryText;
    private String secondaryText;
    private ImageView faceUpCard = null;
    private int faceUpID = (R.drawable.back_of_card);

    private GameMainActivity activity;

    private Button endTurnButton = null;
    private Button exitButton = null;
    private Button helpButton = null;
    private Button shuffleAndDealButton = null;
    private Button exitRules = null;

    private ArrayList<ImageButton> cards = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<ImageView> inPlayCards = new ArrayList<>();
    private int[] inPlayRes = new int[8];

    private ArrayList<ImageView> inCribCards = new ArrayList<>();
    private int[] inCribRes = new int[8];

    private int inPlay;
    private int inCrib;
    private int roundScore;
    private int handSize;
    private int flashHelper = 0;
    private CribbageGameState cribGameState;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public CribHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return activity.findViewById(R.id.top_view);
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) {
            return;
        }

        if (((CribbageGameState) info).getPlayer0Score() >= 61 || ((CribbageGameState) info).getPlayer1Score() >= 61) {
            return;
        }

        if (info instanceof CribbageGameState) {
            cribGameState = (CribbageGameState) this.game.getGameState();

            //display each player's names and their points as game goes on
            if (this.playerNum == 1) {
                playerScoreText = this.name + "'s Score: ";
                oppScoreText = this.allPlayerNames[0] + "'s Score: ";
                playerScore = "" + ((CribbageGameState) info).getPlayer1Score();
                oppScore = "" + ((CribbageGameState) info).getPlayer0Score();

            } else if (this.playerNum == 0) {
                playerScoreText = this.name + "'s Score: ";
                oppScoreText = this.allPlayerNames[1] + "'s Score: ";
                playerScore = "" + ((CribbageGameState) info).getPlayer0Score();
                oppScore = "" + ((CribbageGameState) info).getPlayer1Score();
            }
            playerScoreTextView.setText(playerScoreText);
            oppScoreTextView.setText(oppScoreText);
            playerScoreTextValue.setText(playerScore);
            oppScoreTextValue.setText(oppScore);

            //logging each player's turn
            if (((CribbageGameState) info).getPlayerTurn() != this.playerNum) {
                secondaryText = "It's Opponent's turn. ";
                if (((CribbageGameState) info).isDealer(this.playerNum)) {
                    secondaryText.concat("You are the dealer.");
                    cribOwnerText = "Your Crib:";
                }
                else {
                    secondaryText.concat("Opponent is the dealer.");
                    cribOwnerText = "Their Crib:";
                }
                secondaryMessage.setText(secondaryText);
                cribTextView.setText(cribOwnerText);
            } else if (((CribbageGameState) info).getPlayerTurn() == this.playerNum) {
                secondaryText = "It's Your turn. ";
                if (((CribbageGameState) info).isDealer(this.playerNum)) {
                    secondaryText.concat("You are the dealer.");
                    cribOwnerText = "Your Crib:";
                }
                else {
                    secondaryText.concat("Opponent is the dealer.");
                    cribOwnerText = "Their Crib:";
                }
                secondaryMessage.setText(secondaryText);
                cribTextView.setText(cribOwnerText);
            }
            //for more aesthetic/readability during gameplay
            if(((CribbageGameState) info).isDealer(this.playerNum)
                    && ((CribbageGameState) info).getHandSize(this.playerNum) == 0
                    && ((CribbageGameState) info).getHandSize(1-this.playerNum) == 0) {
                primaryText = "Please deal cards to begin play. ";
                messageView.setText(primaryText);
            }

            //to monitor and spy
            Log.d("Player Turn", "" + ((CribbageGameState) info).getPlayerTurn() );
            /*for(int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); ++i) {
                Log.d("Player" + ((CribbageGameState) info).getPlayerTurn() + "'s cards",
                        " " + ((CribbageGameState) info).getHandCard(((CribbageGameState) info).getPlayerTurn(), i).toString());
            }*/

            //this may change frequently because of the various scoring that happens
            //in different components of each round
            roundScore = ((CribbageGameState) info).getRoundScore(this.playerNum);
            roundScoreText = "" + roundScore;
            roundTotalScoreView.setText(roundScoreText);

            //resets highlighted cards
            hand.clear();
            for(int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); i++)
            {
                hand.add( ((CribbageGameState) info).getHandCard(this.playerNum, i) );
            }

            //copy down the card's for later use (onClick)
            if (this.hand == null || this.hand.isEmpty()) {
                for (int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); i++) {
                    this.hand.add(i, ((CribbageGameState) info).getHandCard(this.playerNum, i));
                }
            }

            //more information for later
            this.inCrib = ((CribbageGameState) info).getCribSize();
            this.inPlay = ((CribbageGameState) info).getInPlaySize();

            //if both hands are empty, human is IS dealer,
            //if both crib and inPlay cards are full (been played)
            // and it's HUMAN'S turn (if the round is over-)
            if (((CribbageGameState) info).getHandSize(0) == 0
                    && ((CribbageGameState) info).getHandSize(1) == 0
                    && ((CribbageGameState) info).getCribSize() == 4
                    && ((CribbageGameState) info).getInPlaySize() == 8
                    && ((CribbageGameState) info).isDealer(this.playerNum)
                    && ((CribbageGameState) info).getPlayerTurn() == this.playerNum) {
                //tally points and properly score each other, switch dealer, and computer will start new round

                CribTallyAction ta = new CribTallyAction(this);
                game.sendAction(ta);

                CribSwitchDealerAction sda = new CribSwitchDealerAction(this);
                game.sendAction(sda);

                if (((CribbageGameState) info).getIsPlayer1Dealer()) {
                    primaryText = "Dealer has been switched to begin a new round. Player 0 is dealer.";
                    messageView.setText(primaryText);
                } else {
                    primaryText = "Dealer has been switched to begin a new round. Player 1 is dealer.";
                    messageView.setText(primaryText);
                }
                Log.d("HumanPlayer, Dealer is Player", "" + ((CribbageGameState) info).getIsPlayer1Dealer());
                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
            }

            /*
             * FOR PLAYABLE CARDS IN HAND
             * setImageResource for each card in hand always
             */
            CardImageRes x = new CardImageRes();
            int k;
            handSize = ((CribbageGameState) info).getHandSize(this.playerNum);
            for (k = 0; k < handSize; k++) {
                cards.get(k).setClickable(true);
                cards.get(k).setImageResource(x.getCardResID(hand.get(k).getSuit(), hand.get(k).getCardValue()));
            }
            //set any unused cards to be gone
            for(; k < 6; k++) {
                cards.get(k).setImageResource(R.drawable.back_of_card);
                cards.get(k).setClickable(false);
            }

            /*
             * FOR FACE UP CARD
             * setImageResource for the faceUpCard,
             * only when it's the right time in game to show it
             */
            faceUpCard.setImageResource(R.drawable.back_of_card);
            if(((CribbageGameState) info).getHandSize(0) >= 5 || ((CribbageGameState) info).getHandSize(1) >= 5) {
                faceUpCard.setImageResource(R.drawable.back_of_card);
            }
            else {
                faceUpID = x.getCardResID( ((CribbageGameState) info).getFaceUpCard().getSuit(),
                        ((CribbageGameState) info).getFaceUpCard().getCardValue());
                faceUpCard.setImageResource(faceUpID);

                if(((CribbageGameState) info).isFaceUpCardJack()) {
                    ((CribbageGameState) info).awardFaceUpPoints();
                    if(this.playerNum == 0) {
                        playerScore = "" + ((CribbageGameState) info).getPlayer0Score();
                        oppScore = "" + ((CribbageGameState) info).getPlayer1Score();
                    } else {
                        playerScore = "" + ((CribbageGameState) info).getPlayer1Score();
                        oppScore = "" + ((CribbageGameState) info).getPlayer0Score();
                    }
                    playerScoreTextValue.setText(playerScore);
                    oppScoreTextValue.setText(oppScore);
                }
            }

            /*
             * FOR CRIB CARDS
             * setImageResource for crib cards,
             * only when it's appropriate to show them in game
             */
            Log.d("inCribSize", "" + inCrib);
            if(((CribbageGameState) info).getInPlaySize() != 8) {
                for(int i = 0; i < inCribCards.size(); i++) {
                    inCribRes[i] = R.drawable.back_of_card;
                    inCribCards.get(i).setImageResource(R.drawable.back_of_card);
                }
            } else {
                for (int i = 0; i < inCribCards.size(); i++) { //has had IndexOutOfBounds problems, check on that
                    inCribRes[i] = x.getCardResID( ((CribbageGameState) info).getCribCard(i).getSuit(),
                            ((CribbageGameState) info).getCribCard(i).getSuit());
                    inCribCards.get(i).setImageResource(inCribRes[i]);
                }
            }

            /*
             * FOR IN PLAY CARDS
             * setImageResource for crib cards,
             * set to show all the time
             *
             * might want to change to divide the cards into "MY" cards and "THEIR" cards,
             * maybe to help visually and for scoring (?)
             */
            Log.d("inPlaySize", "" + inPlay);
            for(int i = 0; i < 8; i++) {
                //reset them a lot just to make it cleaner - it doesn't listen to me when inPlay is cleared
                inPlayRes[i] = R.drawable.back_of_card;
                inPlayCards.get(i).setImageResource(R.drawable.back_of_card);
            }
            try {
                Log.d("inPlayCards", "drawing");
                for (int i = 0; i < inPlay; i++) {
                    inPlayRes[i] = x.getCardResID(((CribbageGameState) info).getInPlayCard(i).getSuit(),
                            ((CribbageGameState) info).getInPlayCard(i).getCardValue());
                    inPlayCards.get(i).setImageResource(inPlayRes[i]);
                }
                for(int i = 0; i < inPlay; i++) {
                    Log.d("inPlayCards", "drawing");
                    inPlayRes[i] = x.getCardResID(((CribbageGameState) info).getInPlayCard(i).getSuit(),
                            ((CribbageGameState) info).getInPlayCard(i).getCardValue());
                    inPlayCards.get(i).setImageResource(inPlayRes[i]);
                }
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                Log.d("inPlayCards", "IndexOutOfBoundsException, inPlay size: " + ((CribbageGameState) info).getInPlaySize());
            }

            //check if GoAction is going to be needed
            //if human's hand is not empty and we have discarded to crib, and it's human's turn
            if(hand.size() != 0 && hand.size() <= 4 && ((CribbageGameState) info).getPlayerTurn() == this.playerNum) {

                //if my hand doesn't have any playable cards, prompt to end turn to ENSUE GO ACTION
                boolean hasPlayableCard = ((CribbageGameState) info).hasAnyPlayableCard(this.playerNum);
                if(!hasPlayableCard) {
                    primaryText = "You should say \"Go\". Press \"End Turn\". ";
                    messageView.setText(primaryText);
                }
            }
        } else {
            if (flashHelper == 0) {
                flashHelper++;
                flash(Color.RED, 50);
                Log.wtf("receiveInfo", "failed with Info: " + info.getClass().getSimpleName());
            }
            else {
                flashHelper = 0;
            }
        }
    }//receiveInfo

    @Override
    public void setAsGui(GameMainActivity activity) {
        this.activity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.cribbage_main);

        //Initialize the widget reference member variables
        this.playerScoreTextValue = activity.findViewById(R.id.yourScoreValue);
        this.playerScoreTextView = activity.findViewById(R.id.yourScoreText);
        this.oppScoreTextValue = activity.findViewById(R.id.oppScoreValue);
        this.oppScoreTextView = activity.findViewById(R.id.oppScoreText);
        this.roundTotalScoreView = activity.findViewById(R.id.roundTotalValue);
        this.messageView = activity.findViewById(R.id.messageText);
        this.secondaryMessage = activity.findViewById(R.id.secondaryMessageText);
        this.cribTextView = activity.findViewById(R.id.cribTitle);

        exitButton = activity.findViewById(R.id.exitGameButton);
        helpButton = activity.findViewById(R.id.helpButton);
        endTurnButton = activity.findViewById(R.id.endTurnButton);
        shuffleAndDealButton = activity.findViewById(R.id.shuffleAndDeal);

        //setting each card object to a card button on the screen
        this.cards.add(0, activity.findViewById(R.id.card1));
        this.cards.add(1, activity.findViewById(R.id.card2));
        this.cards.add(2, activity.findViewById(R.id.card3));
        this.cards.add(3, activity.findViewById(R.id.card4));
        this.cards.add(4, activity.findViewById(R.id.card5));
        this.cards.add(5, activity.findViewById(R.id.card6));

        inPlayCards.add(0, activity.findViewById(R.id.inPlayCard1));
        inPlayCards.add(1, activity.findViewById(R.id.inPlayCard2));
        inPlayCards.add(2, activity.findViewById(R.id.inPlayCard3));
        inPlayCards.add(3, activity.findViewById(R.id.inPlayCard4));
        inPlayCards.add(4, activity.findViewById(R.id.inPlayCard5));
        inPlayCards.add(5, activity.findViewById(R.id.inPlayCard6));
        inPlayCards.add(6, activity.findViewById(R.id.inPlayCard7));
        inPlayCards.add(7, activity.findViewById(R.id.inPlayCard8));

        faceUpCard = activity.findViewById(R.id.faceUpCardView);
        inCribCards.add(0, activity.findViewById(R.id.cribCard1));
        inCribCards.add(1, activity.findViewById(R.id.cribCard2));
        inCribCards.add(2, activity.findViewById(R.id.cribCard3));
        inCribCards.add(3, activity.findViewById(R.id.cribCard4));

        //setOnClickListener for ImageButtons
        this.cards.get(0).setOnClickListener(this);
        this.cards.get(1).setOnClickListener(this);
        this.cards.get(2).setOnClickListener(this);
        this.cards.get(3).setOnClickListener(this);
        this.cards.get(4).setOnClickListener(this);
        this.cards.get(5).setOnClickListener(this);

        //setOnClickListener for Buttons
        exitButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        endTurnButton.setOnClickListener(this);
        shuffleAndDealButton.setOnClickListener(this);

    }

    //this is used to make sure that the player's names are displayed
    @Override //because it belongs to GameHumanPlayer as an empty method!
    public void initAfterReady() {
        //allPlayerNames can be null so after Ready, we can initialize the now non-null array
        playerScoreText = this.name + "'s Score: ";
        playerScoreTextView.setText(playerScoreText);
        if (this.playerNum == 1) {
            oppScoreText = this.allPlayerNames[0] + "'s Score: ";
        } else {
            oppScoreText = this.allPlayerNames[1] + "'s Score: ";
        }
        oppScoreTextView.setText(oppScoreText);
    }

    @Override
    public void onClick(View button) {
        //if it's an ImageButton... decide PlayCardAction or DiscardAction
        if (button instanceof ImageButton) {  /****************** CARD BUTTON  */

            for (int i = 0; i < cards.size(); i++) {
                //if button is this thing that you clicked on screen
                if (button.equals(cards.get(i))) {
                    int index;
                    //numClicked++;
                    hand.get(i).toggleSelected();
                    if (hand.get(i).isSelected()) {
                        cards.get(i).setBackgroundColor(Color.BLACK);
                    } else {
                        cards.get(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        }
        //unless it isn't a card you're clicking but a button you pressed
        else if (button instanceof Button) {

            if (button.equals(endTurnButton)) { /****************** END TURN BUTTON  */
                //check if discard or play
                if(hand.size() == 6) {
                    //check if valid discard, get values of relevant cards
                    int sum = 0;
                    Card c1 = null;
                    Card c2 = null;

                    for(int i = 0; i < hand.size(); i++) {
                        if(hand.get(i).isSelected()) {
                            sum++;
                            if(sum == 1) c1 = hand.get(i);
                            else if(sum == 2) c2 = hand.get(i);
                        }
                    }

                    if(sum == 2) {
                        //send discard action
                        primaryText = "Cards discarded: " + c1.toString() + " and " + c2.toString();
                        messageView.setText(primaryText);
                        CribDiscardAction da = new CribDiscardAction(this, c1, c2);
                        game.sendAction(da);
                        for(int i = 0; i < cards.size(); i++) {
                            cards.get(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        CribEndTurnAction eta = new CribEndTurnAction(this);
                        game.sendAction(eta);
                    } else {
                        //error message
                        primaryText = "Please select exactly 2 cards to discard to the crib.";
                        messageView.setText(primaryText);
                    }
                } else {
                    //check if valid play
                    int sum = 0;
                    Card c1 = null;

                    for (int i = 0; i < hand.size(); i++) {
                        if (hand.get(i).isSelected()) {
                            sum++;
                            if (sum == 1) c1 = hand.get(i);
                        }
                    }
                    if ((sum == 1) && (roundScore + c1.getCardScore() <= 31)) {
                        primaryText = "Card played: " + c1.toString();
                        messageView.setText(primaryText);
                        CribPlayCardAction pa = new CribPlayCardAction(this, c1);
                        game.sendAction(pa);
                        for (int i = 0; i < cards.size(); i++) {
                            cards.get(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        CribEndTurnAction eta = new CribEndTurnAction(this);
                        game.sendAction(eta);
                    } else {
                        boolean hasPlayableCard = cribGameState.hasAnyPlayableCard(this.playerNum);
                        if (!hasPlayableCard) {
                            primaryText = "You have said \"Go\".";
                            messageView.setText(primaryText);
                            CribGoAction ga = new CribGoAction(this);
                            game.sendAction(ga);

                            CribEndTurnAction eta = new CribEndTurnAction(this);
                            game.sendAction(eta);
                            Log.d("HumanPlayer", "has no playable cards, so Go action is called.");
                        } else {
                            primaryText = "Please select exactly 1 card to play.";
                            messageView.setText(primaryText);
                        }
                    }
                }

            } else if (button.equals(helpButton)) { /****************** HELP BUTTON  */
                //no way to go back, but that's not important yet i guess
                activity.setContentView(R.layout.cribbage_rules);
                exitRules = activity.findViewById(R.id.exitRules);
                exitRules.setOnClickListener(this);

            } else if(button.equals(exitRules)) {  /****************** EXIT RULES BUTTON  */
                reInitGui();

            }else if (button.equals(exitButton)) { /***************************** EXIT GAME BUTTON  */
                primaryText = "Game is over.";
                messageView.setText(primaryText);
                gameIsOver("Player " + this.playerNum + " has exited the game. Game is over.");
                //only so CribbageGameState knows.
                CribExitGameAction ega = new CribExitGameAction(this);
                game.sendAction(ega);
                activity.setGameOver(true);
                activity.finish();
                System.exit(0);

            } else if (button.equals(shuffleAndDealButton)) { /****************** SHUFFLE AND DEAL BUTTON  */

                if(cribGameState.getHandSize(this.playerNum) == 0) {
                    //reset all cards so they disappear
                    for(int i = 0; i < inCribCards.size(); i++) {
                        inCribRes[i] = R.drawable.back_of_card;
                        inCribCards.get(i).setImageResource(R.drawable.back_of_card);
                    }
                    for(int i = 0; i < inPlayCards.size(); i++) {
                        inPlayRes[i] = R.drawable.back_of_card;
                        inPlayCards.get(i).setImageResource(inPlayRes[i]);
                    }
                    //tally points if not tallied yet
                    if(cribGameState.getCribSize() == 4 && cribGameState.getInPlaySize() == 8) {
                        CribTallyAction ta = new CribTallyAction(this);
                        game.sendAction(ta);
                        Log.d("HumanPlayer", "CribTallyAction - onClick");
                    }

                    CribDealAction da = new CribDealAction(this);
                    game.sendAction(da);
                    primaryText = "You have dealt cards to all players.";
                    messageView.setText(primaryText);

                }
                else {
                    primaryText = "You're not supposed to deal right now. ";
                    messageView.setText(primaryText);
                }
            }

        }

        //may not be needed, helpful to have just in case i miss something
        /*else {
            flash(Color.RED, 50);
            Log.d("onClick", "failed");
        }*/
    }

    // Method to reinitialize all of the GUI and buttons to current proper values after switching out of help screen
    private void reInitGui() {
        setAsGui(activity);

        cribTextView.setText(cribOwnerText); // Reset the message textviews to what they were before
        messageView.setText(primaryText);
        secondaryMessage.setText(secondaryText);

        playerScoreTextView.setText(playerScoreText); // reset Score Text Views
        oppScoreTextView.setText(oppScoreText);
        playerScoreTextValue.setText(playerScore);
        oppScoreTextValue.setText(oppScore);
        roundTotalScoreView.setText(roundScoreText);

        faceUpCard.setImageResource(faceUpID); // reset Card Image Resources
        for(int i = 0; i < 8; i++){
            inPlayCards.get(i).setImageResource(inPlayRes[i]);
        }
        for(int i = 0; i < 4; i++){
            inCribCards.get(i).setImageResource(inCribRes[i]);
        }
        int k;
        CardImageRes x = new CardImageRes();
        for (k = 0; k < handSize; k++) {
            cards.get(k).setClickable(true);
            cards.get(k).setImageResource(x.getCardResID(hand.get(k).getSuit(), hand.get(k).getCardValue()));
        }
        //set any unused cards to be gone
        for(; k < 6; k++) {
            cards.get(k).setImageResource(R.drawable.back_of_card);
            cards.get(k).setClickable(false);
        }

    }
}
