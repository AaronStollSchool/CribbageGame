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
import com.example.cribbagegame.GameFramework.players.GameHumanPlayer;
import com.example.cribbagegame.R;

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
    private TextView oppScoreTextValue = null;
    private TextView playerScoreTextView = null;
    private TextView oppScoreTextView = null;
    private TextView roundTotalScoreView = null;
    private TextView messageView = null;
    private ImageView faceUpCard = null;

    private GameMainActivity activity;

    private Button endTurnButton = null;
    private Button exitButton = null;
    private Button helpButton = null;
    private Button shuffleAndDealButton = null;
    private Button exitRules = null;
    private Button boardButton = null;

    private ArrayList<ImageButton> cards = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();
    private ArrayList<ImageView> inPlayCards = new ArrayList<>();
    private ArrayList<ImageView> inCribCards = new ArrayList<>();

    private int inPlay;
    private int inCrib;
    private int roundScore;
    private int gamePhase; //for later

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

        if (info instanceof CribbageGameState) {

            //display each player's names and their points as game goes on
            if (this.playerNum == 1) {
                playerScoreTextView.setText(this.name + "'s Score: ");
                oppScoreTextView.setText(this.allPlayerNames[0] + "'s Score: ");
                playerScoreTextValue.setText("" + ((CribbageGameState) info).getPlayer1Score());
                oppScoreTextValue.setText("" + ((CribbageGameState) info).getPlayer0Score());
            } else if (this.playerNum == 0) {
                playerScoreTextView.setText(this.name + "'s Score: ");
                oppScoreTextView.setText(this.allPlayerNames[1] + "'s Score: ");
                playerScoreTextValue.setText("" + ((CribbageGameState) info).getPlayer0Score());
                oppScoreTextValue.setText("" + ((CribbageGameState) info).getPlayer1Score());
            }

            //logging each player's turn
            if (((CribbageGameState) info).getPlayerTurn() != this.playerNum) {
                messageView.setText("It's Opponent's turn. \n");
                Log.d("Player Turn", "" + ((CribbageGameState) info).getPlayerTurn() );

            }
            else if (((CribbageGameState) info).getPlayerTurn() == this.playerNum) {
                messageView.setText("It's My's turn. \n");
                Log.d("Player Turn", "" + ((CribbageGameState) info).getPlayerTurn() );
            }

            //to monitor and spy
            for(int i = 0; i < ((CribbageGameState) info).getHandSize(((CribbageGameState) info).getPlayerTurn()); ++i) {
                Log.d("Player" + ((CribbageGameState) info).getPlayerTurn(),
                        " " + ((CribbageGameState) info).getHandCard(((CribbageGameState) info).getPlayerTurn(), i).toString());
            }


            //this may change frequently because of the various scoring that happens
            //in different components of each game
            roundScore = ((CribbageGameState) info).getRoundScore(this.playerNum);
            roundTotalScoreView.setText("" + roundScore);

            //resets highlighted cards
            hand.clear();
            for(int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); i++)
            {
                hand.add( ((CribbageGameState) info).getHandCard(this.playerNum, i) );
            }

            //copy down the card's for later use (onClick)
            if (this.hand == null || this.hand.isEmpty()) {
                for (int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); ++i) {
                    this.hand.add(i, ((CribbageGameState) info).getHandCard(this.playerNum, i));
                }
            }

            //more information for later
            this.gamePhase = ((CribbageGameState) info).getGamePhase();
            this.inCrib = ((CribbageGameState) info).getCribSize();
            this.inPlay = ((CribbageGameState) info).getInPlaySize();

            /**
             * FOR PLAYABLE CARDS IN HAND
             * setImageResource for each card in hand
             */
            int k;
            for (k = 0; k < ((CribbageGameState) info).getHandSize(this.playerNum); k++) {
                cards.get(k).setClickable(true);
                CardImageRes x = new CardImageRes();
                cards.get(k).setImageResource(x.getCardResID(hand.get(k).getSuit(), hand.get(k).getCardValue()));
            }

            //set any unused cards to be gone
            for(; k < 6; k++)
            {
                cards.get(k).setImageResource(R.drawable.back_of_card);
                cards.get(k).setClickable(false);
                //cards.get(k).setVisibility(View.INVISIBLE);
            }

            /**
             * FOR FACE UP CARD
             * setImageResource for the faceUpCard,
             * only when it's the right time in game to show it
             */
            if(((CribbageGameState) info).getHandSize(0) != 4 || ((CribbageGameState) info).getHandSize(1) != 4) {
                faceUpCard.setImageResource(R.drawable.back_of_card);
            }
            else {
                CardImageRes x = new CardImageRes();
                faceUpCard.setImageResource(x.getCardResID( ((CribbageGameState) info).getFaceUpCard().getSuit(),
                        ((CribbageGameState) info).getFaceUpCard().getCardValue()) );
            }

            /**
             * FOR CRIB CARDS
             * setImageResource for crib cards,
             * only when it's appropriate to show them in game
             */
            if(((CribbageGameState) info).getInPlaySize() != 8) {
                for(int i = 0; i < inCrib; ++i) {
                    inCribCards.get(i).setImageResource(R.drawable.back_of_card);
                }
            }
            else {
                for (int i = 0; i < inCrib; ++i) {
                    CardImageRes x = new CardImageRes();
                    inCribCards.get(i).setImageResource(x.getCardResID( ((CribbageGameState) info).getCribCard(i).getSuit(),
                            ((CribbageGameState) info).getCribCard(i).getSuit()) );
                    }
                }

            /**
             * FOR IN PLAY CARDS
             */
            for(int i = 0; i < inPlay; ++i) {
                Log.d("inPlayCards", "drawing");
                CardImageRes x = new CardImageRes();
                inPlayCards.get(i).setImageResource(x.getCardResID( ((CribbageGameState) info).getInPlayCard(i).getSuit(),
                        ((CribbageGameState) info).getInPlayCard(i).getSuit()) );
            }

            //check if round is over
            if(((CribbageGameState) info).getHandSize(0) == 0 && ((CribbageGameState) info).getHandSize(1) == 0
                && ((CribbageGameState) this.game.getGameState()).getCribSize() != 0)
            {
                Log.d("Player", "Tally action initiated");
                CribTallyAction ta = new CribTallyAction(this);
                game.sendAction(ta);
            }

            //check if Go action needed
            if(hand.size() != 0 && hand.size() <= 4 && ((CribbageGameState) info).getPlayerTurn() == this.playerNum)
            {
                int sum = 0;
                for(int i = 0; i < hand.size(); i++)
                {
                    if(hand.get(i).getCardScore() + roundScore <= 31)
                    {
                        break;
                    }
                    else
                    {
                        sum++;
                    }
                }
                if(sum == hand.size())
                {
                    CribGoAction ga = new CribGoAction(this);
                    game.sendAction(ga);

                }
            }

        } else {
            flash(Color.RED, 50);
            Log.d("receiveInfo", "failed");
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

        exitButton = activity.findViewById(R.id.exitGameButton);
        helpButton = activity.findViewById(R.id.helpButton);
        endTurnButton = activity.findViewById(R.id.endTurnButton);
        shuffleAndDealButton = activity.findViewById(R.id.shuffleAndDeal);
        boardButton = activity.findViewById(R.id.boardButton);
        exitRules = activity.findViewById(R.id.exitRules);

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
        boardButton.setOnClickListener(this);
        //exitRules.setOnClickListener(this);

    }

    //this is used to make sure that the player's names are displayed
    @Override //because it belongs to GameHumanPlayer as an empty method!
    public void initAfterReady() {
        //allPlayerNames can be null so after Ready, we can initialize the now non-null array
        playerScoreTextView.setText(this.name + "'s Score: ");
        if (this.playerNum == 1) {
            oppScoreTextView.setText(this.allPlayerNames[0] + "'s Score: ");
        } else {
            oppScoreTextView.setText(this.allPlayerNames[1] + "'s Score: ");
        }
    }

    @Override
    public void onClick(View button) {
        //if it's an ImageButton... decide PlayCardAction or DiscardAction
        if (button instanceof ImageButton) {

            for (int i = 0; i < cards.size(); ++i) {
                //if button is this thing that you clicked on screen
                if (button.equals(cards.get(i))) {
                    int index;
                    //numClicked++;
                    hand.get(i).toggleSelected();
                    if(hand.get(i).isSelected())
                    {
                        cards.get(i).setBackgroundColor(Color.BLACK);
                    }
                    else
                    {
                        cards.get(i).setBackgroundColor(Color.TRANSPARENT);
                    }

                    //when discarding card
                    //messageView.setText("Please select another card to discard both to the crib.");
                    /*if (numClicked == 2) {
                        index = i;
                        messageView.setText("");
                        CribDiscardAction da = new CribDiscardAction(this, hand.get(i), hand.get(index));
                        game.sendAction(da);
                    }

                    //if you're not meant to discard, play it
                    /*else if (numClicked == 1) {
                        CribPlayCardAction pca = new CribPlayCardAction(this, hand.get(i));
                        game.sendAction(pca);
                    }*/

                }
                /*else {
                    flash(Color.RED, 50);
                }*/
            }
        }
        //unless it isn't a card you're clicking but a button you pressed
        else if (button instanceof Button) {

            if (button.equals(endTurnButton)) {
                //check if discard or play
                if(hand.size() == 6)
                {
                    //check if valid discard, get values of relevant cards
                    int sum = 0;
                    Card c1 = null;
                    Card c2 = null;

                    for(int i = 0; i < hand.size(); i++)
                    {
                        if(hand.get(i).isSelected())
                        {
                            sum++;
                            if(sum == 1) c1 = hand.get(i);
                            else if(sum == 2) c2 = hand.get(i);
                        }
                    }

                    if(sum == 2)
                    {
                        //send discard action
                        messageView.setText("Cards discarded: " + c1.toString() + " and " + c2.toString());
                        CribDiscardAction da = new CribDiscardAction(this, c1, c2);
                        game.sendAction(da);
                        for(int i = 0; i < cards.size(); i++)
                        {
                            cards.get(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        CribEndTurnAction eta = new CribEndTurnAction(this);
                        game.sendAction(eta);
                    } else {
                        //error message
                        messageView.setText("Please select exactly 2 cards to discard to the crib.");
                    }
                } else {
                    //check if valid play
                    int sum = 0;
                    Card c1 = null;

                    for(int i = 0; i < hand.size(); i++)
                    {
                        if(hand.get(i).isSelected())
                        {
                            sum++;
                            if(sum == 1) c1 = hand.get(i);
                        }
                    }
                    if((sum == 1) && (roundScore + c1.getCardValue() <= 31))
                    {
                        messageView.setText("Card played: " + c1.toString());
                        CribPlayCardAction pa = new CribPlayCardAction(this, c1);
                        game.sendAction(pa);
                        for(int i = 0; i < cards.size(); i++)
                        {
                            cards.get(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                        CribEndTurnAction eta = new CribEndTurnAction(this);
                        game.sendAction(eta);
                    }
                    else
                    {
                        messageView.setText("Please select exactly 1 card to play.");
                    }
                }
            } else if (button.equals(helpButton)) {
                //no way to go back, but that's not important yet i guess
                activity.setContentView(R.layout.cribbage_rules);
            } else if (button.equals(exitButton)) {
                //you don't have to exit the screen it's totally fine.
                messageView.setText("Game is over.");
                gameIsOver("Player " + this.playerNum + " has exited the game. Game is over.");
                //only so CribbageGameState knows.
                // ---- this should also maybe make it so no buttons are able to be pressed and stuff
                CribExitGameAction ega = new CribExitGameAction(this);
                game.sendAction(ega);
            } else if (button.equals(shuffleAndDealButton)) {

                //reset all cards so they disappear
                for(int i = 0; i < inCribCards.size(); ++i) {
                    inCribCards.get(i).setImageResource(R.drawable.back_of_card);
                }
                for(int i = 0; i < inPlayCards.size(); ++i) {
                    inPlayCards.get(i).setImageResource(R.drawable.back_of_card);
                }

                if(hand.size() == 0) {
                    CribDealAction da = new CribDealAction(this);
                    game.sendAction(da);
                }
            } else if (button.equals(boardButton)) {
                //cribbage_board not drawn yet!!!!
                activity.setContentView(R.layout.cribbage_board);
            } else if (button.equals(exitRules)) {
                //may have to give up on this, it doesn't like buttons anywhere else
                //activity.setContentView(R.layout.cribbage_main);
            }

        }

        //may not be needed, helpful to have just in case i miss something
        /*else {
            flash(Color.RED, 50);
            Log.d("onClick", "failed");
        }*/
    }

    private void addInPlayCardView(Card c){
        int width = 80;
        int height = 140;

        //ImageView cardView = new ImageView();

        //inPlayLayout.addView(cardView);
    }
}
