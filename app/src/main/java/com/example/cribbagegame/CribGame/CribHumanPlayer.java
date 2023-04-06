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

    private ArrayList<ImageButton> cards = new ArrayList<>();
    private ArrayList<Card> hand = new ArrayList<>();
    private int roundScore;
    private int gamePhase; //for later
    private int numClicked = 0;
    private int inCrib;

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

            hand.clear();
            for(int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); i++)
            {
                hand.add( ((CribbageGameState) info).getHandCard(this.playerNum, i) );
            }

            //setImageResource for each card in hand, because each is an ImageButton
            int k;
            for (k = 0; k < ((CribbageGameState) info).getHandSize(this.playerNum); k++) {

                switch (((CribbageGameState) info).getHandCard(this.playerNum, k).getCardID()) {
                    case 11:
                        cards.get(k).setImageResource(R.drawable.ace_of_diamonds);
                        break;
                    case 12:
                        cards.get(k).setImageResource(R.drawable.ace_of_hearts);
                        break;
                    case 13:
                        cards.get(k).setImageResource(R.drawable.ace_of_spades);
                        break;
                    case 14:
                        cards.get(k).setImageResource(R.drawable.ace_of_clubs);
                        break;
                    case 21:
                        cards.get(k).setImageResource(R.drawable._2_of_diamonds);
                        break;
                    case 22:
                        cards.get(k).setImageResource(R.drawable._2_of_hearts);
                        break;
                    case 23:
                        cards.get(k).setImageResource(R.drawable._2_of_spades);
                        break;
                    case 24:
                        cards.get(k).setImageResource(R.drawable._2_of_clubs);
                        break;

                    case 31:
                        cards.get(k).setImageResource(R.drawable._3_of_diamonds);
                        break;
                    case 32:
                        cards.get(k).setImageResource(R.drawable._3_of_hearts);
                        break;
                    case 33:
                        cards.get(k).setImageResource(R.drawable._3_of_spades);
                        break;
                    case 34:
                        cards.get(k).setImageResource(R.drawable._3_of_clubs);
                        break;

                    case 41:
                        cards.get(k).setImageResource(R.drawable._4_of_diamonds);
                        break;
                    case 42:
                        cards.get(k).setImageResource(R.drawable._4_of_hearts);
                        break;
                    case 43:
                        cards.get(k).setImageResource(R.drawable._4_of_spades);
                        break;
                    case 44:
                        cards.get(k).setImageResource(R.drawable._4_of_clubs);
                        break;

                    case 51:
                        cards.get(k).setImageResource(R.drawable._5_of_diamonds);
                        break;
                    case 52:
                        cards.get(k).setImageResource(R.drawable._5_of_hearts);
                        break;
                    case 53:
                        cards.get(k).setImageResource(R.drawable._5_of_spades);
                        break;
                    case 54:
                        cards.get(k).setImageResource(R.drawable._5_of_clubs);
                        break;

                    case 61:
                        cards.get(k).setImageResource(R.drawable._6_of_diamonds);
                        break;
                    case 62:
                        cards.get(k).setImageResource(R.drawable._6_of_hearts);
                        break;
                    case 63:
                        cards.get(k).setImageResource(R.drawable._6_of_spades);
                        break;
                    case 64:
                        cards.get(k).setImageResource(R.drawable._6_of_clubs);
                        break;

                    case 71:
                        cards.get(k).setImageResource(R.drawable._7_of_diamonds);
                        break;
                    case 72:
                        cards.get(k).setImageResource(R.drawable._7_of_hearts);
                        break;
                    case 73:
                        cards.get(k).setImageResource(R.drawable._7_of_spades);
                        break;
                    case 74:
                        cards.get(k).setImageResource(R.drawable._7_of_clubs);
                        break;

                    case 81:
                        cards.get(k).setImageResource(R.drawable._8_of_diamonds);
                        break;
                    case 82:
                        cards.get(k).setImageResource(R.drawable._8_of_hearts);
                        break;
                    case 83:
                        cards.get(k).setImageResource(R.drawable._8_of_spades);
                        break;
                    case 84:
                        cards.get(k).setImageResource(R.drawable._8_of_clubs);
                        break;

                    case 91:
                        cards.get(k).setImageResource(R.drawable._9_of_diamonds);
                        break;
                    case 92:
                        cards.get(k).setImageResource(R.drawable._9_of_hearts);
                        break;
                    case 93:
                        cards.get(k).setImageResource(R.drawable._9_of_spades);
                        break;
                    case 94:
                        cards.get(k).setImageResource(R.drawable._9_of_clubs);
                        break;

                    case 101:
                        cards.get(k).setImageResource(R.drawable._10_of_diamonds);
                        break;
                    case 102:
                        cards.get(k).setImageResource(R.drawable._10_of_hearts);
                        break;
                    case 103:
                        cards.get(k).setImageResource(R.drawable._10_of_spades);
                        break;
                    case 104:
                        cards.get(k).setImageResource(R.drawable._10_of_clubs);
                        break;

                    case 111:
                        cards.get(k).setImageResource(R.drawable.jack_of_diamonds);
                        break;
                    case 112:
                        cards.get(k).setImageResource(R.drawable.jack_of_hearts);
                        break;
                    case 113:
                        cards.get(k).setImageResource(R.drawable.jack_of_spades);
                        break;
                    case 114:
                        cards.get(k).setImageResource(R.drawable.jack_of_clubs);
                        break;

                    case 121:
                        cards.get(k).setImageResource(R.drawable.queen_of_diamonds);
                        break;
                    case 122:
                        cards.get(k).setImageResource(R.drawable.queen_of_hearts);
                        break;
                    case 123:
                        cards.get(k).setImageResource(R.drawable.queen_of_spades);
                        break;
                    case 124:
                        cards.get(k).setImageResource(R.drawable.queen_of_clubs);
                        break;

                    case 131:
                        cards.get(k).setImageResource(R.drawable.king_of_diamonds);
                        break;
                    case 132:
                        cards.get(k).setImageResource(R.drawable.king_of_hearts);
                        break;
                    case 133:
                        cards.get(k).setImageResource(R.drawable.king_of_spades);
                        break;
                    case 134:
                        cards.get(k).setImageResource(R.drawable.king_of_clubs);
                        break;

                    default:
                        cards.get(k).setImageResource(R.drawable.back_of_card);
                        break;
                }
            }
            //set any unused cards to be gone
            for(; k < 6; k++)
            {
                cards.get(k).setImageResource(R.drawable.back_of_card);
                //cards.get(k).setVisibility(View.INVISIBLE);
            }

            //setImageResource for the faceUpCard -- hopefully this works
                switch (((CribbageGameState) info).getFaceUpCard().getCardID()) {
                    case 11:
                        faceUpCard.setImageResource(R.drawable.ace_of_diamonds);
                        break;
                    case 12:
                        faceUpCard.setImageResource(R.drawable.ace_of_hearts);
                        break;
                    case 13:
                        faceUpCard.setImageResource(R.drawable.ace_of_spades);
                        break;
                    case 14:
                        faceUpCard.setImageResource(R.drawable.ace_of_clubs);
                        break;

                    case 21:
                        faceUpCard.setImageResource(R.drawable._2_of_diamonds);
                        break;
                    case 22:
                        faceUpCard.setImageResource(R.drawable._2_of_hearts);
                        break;
                    case 23:
                        faceUpCard.setImageResource(R.drawable._2_of_spades);
                        break;
                    case 24:
                        faceUpCard.setImageResource(R.drawable._2_of_clubs);
                        break;

                    case 31:
                        faceUpCard.setImageResource(R.drawable._3_of_diamonds);
                        break;
                    case 32:
                        faceUpCard.setImageResource(R.drawable._3_of_hearts);
                        break;
                    case 33:
                        faceUpCard.setImageResource(R.drawable._3_of_spades);
                        break;
                    case 34:
                        faceUpCard.setImageResource(R.drawable._3_of_clubs);
                        break;

                    case 41:
                        faceUpCard.setImageResource(R.drawable._4_of_diamonds);
                        break;
                    case 42:
                        faceUpCard.setImageResource(R.drawable._4_of_hearts);
                        break;
                    case 43:
                        faceUpCard.setImageResource(R.drawable._4_of_spades);
                        break;
                    case 44:
                        faceUpCard.setImageResource(R.drawable._4_of_clubs);
                        break;

                    case 51:
                        faceUpCard.setImageResource(R.drawable._5_of_diamonds);
                        break;
                    case 52:
                        faceUpCard.setImageResource(R.drawable._5_of_hearts);
                        break;
                    case 53:
                        faceUpCard.setImageResource(R.drawable._5_of_spades);
                        break;
                    case 54:
                        faceUpCard.setImageResource(R.drawable._5_of_clubs);
                        break;

                    case 61:
                        faceUpCard.setImageResource(R.drawable._6_of_diamonds);
                        break;
                    case 62:
                        faceUpCard.setImageResource(R.drawable._6_of_hearts);
                        break;
                    case 63:
                        faceUpCard.setImageResource(R.drawable._6_of_spades);
                        break;
                    case 64:
                        faceUpCard.setImageResource(R.drawable._6_of_clubs);
                        break;

                    case 71:
                        faceUpCard.setImageResource(R.drawable._7_of_diamonds);
                        break;
                    case 72:
                        faceUpCard.setImageResource(R.drawable._7_of_hearts);
                        break;
                    case 73:
                        faceUpCard.setImageResource(R.drawable._7_of_spades);
                        break;
                    case 74:
                        faceUpCard.setImageResource(R.drawable._7_of_clubs);
                        break;

                    case 81:
                        faceUpCard.setImageResource(R.drawable._8_of_diamonds);
                        break;
                    case 82:
                        faceUpCard.setImageResource(R.drawable._8_of_hearts);
                        break;
                    case 83:
                        faceUpCard.setImageResource(R.drawable._8_of_spades);
                        break;
                    case 84:
                        faceUpCard.setImageResource(R.drawable._8_of_clubs);
                        break;

                    case 91:
                        faceUpCard.setImageResource(R.drawable._9_of_diamonds);
                        break;
                    case 92:
                        faceUpCard.setImageResource(R.drawable._9_of_hearts);
                        break;
                    case 93:
                        faceUpCard.setImageResource(R.drawable._9_of_spades);
                        break;
                    case 94:
                        faceUpCard.setImageResource(R.drawable._9_of_clubs);
                        break;

                    case 101:
                        faceUpCard.setImageResource(R.drawable._10_of_diamonds);
                        break;
                    case 102:
                        faceUpCard.setImageResource(R.drawable._10_of_hearts);
                        break;
                    case 103:
                        faceUpCard.setImageResource(R.drawable._10_of_spades);
                        break;
                    case 104:
                        faceUpCard.setImageResource(R.drawable._10_of_clubs);
                        break;

                    case 111:
                        faceUpCard.setImageResource(R.drawable.jack_of_diamonds);
                        break;
                    case 112:
                        faceUpCard.setImageResource(R.drawable.jack_of_hearts);
                        break;
                    case 113:
                        faceUpCard.setImageResource(R.drawable.jack_of_spades);
                        break;
                    case 114:
                        faceUpCard.setImageResource(R.drawable.jack_of_clubs);
                        break;

                    case 121:
                        faceUpCard.setImageResource(R.drawable.queen_of_diamonds);
                        break;
                    case 122:
                        faceUpCard.setImageResource(R.drawable.queen_of_hearts);
                        break;
                    case 123:
                        faceUpCard.setImageResource(R.drawable.queen_of_spades);
                        break;
                    case 124:
                        faceUpCard.setImageResource(R.drawable.queen_of_clubs);
                        break;

                    case 131:
                        faceUpCard.setImageResource(R.drawable.king_of_diamonds);
                        break;
                    case 132:
                        faceUpCard.setImageResource(R.drawable.king_of_hearts);
                        break;
                    case 133:
                        faceUpCard.setImageResource(R.drawable.king_of_spades);
                        break;
                    case 134:
                        faceUpCard.setImageResource(R.drawable.king_of_clubs);
                        break;

                    default:
                        faceUpCard.setImageResource(R.drawable.back_of_card);
                        break;
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

        }
        else {
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

        faceUpCard = activity.findViewById(R.id.faceUpCardView);

        //setting each card object to a card button on the screen
        this.cards.add(0, activity.findViewById(R.id.card1));
        this.cards.add(1, activity.findViewById(R.id.card2));
        this.cards.add(2, activity.findViewById(R.id.card3));
        this.cards.add(3, activity.findViewById(R.id.card4));
        this.cards.add(4, activity.findViewById(R.id.card5));
        this.cards.add(5, activity.findViewById(R.id.card6));

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
        playerScoreTextView.setText(this.name + "'s Score: ");
        if (this.playerNum == 1) {
            oppScoreTextView.setText(this.allPlayerNames[0] + "'s Score: ");
        } else {
            oppScoreTextView.setText(this.allPlayerNames[1] + "'s Score: ");
        }
    }

    @Override
    public void onClick(View button) {

        /*
        TO DO:
        * check if played card is valid--- whether that method exists or not
            that means if the card button you pressed currently holds a card in hand.
            like if it's not empty-- can be made if card's imageResource is (blank or whatever indication above)

        * helpButton needs a class or a method that exists somewhere to change screen to help_screen
                ^^^^^^^^not needed yet
         */

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
                    }
                    else
                    {
                        //error message
                        messageView.setText("Please select exactly 2 cards to discard to the crib.");
                    }
                }
                else
                {
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
            }
            else if (button.equals(helpButton)) {
                // * for later: may need HelpButtonAction (?)
            }
            else if (button.equals(exitButton)) {
                CribExitGameAction ega = new CribExitGameAction(this);
                game.sendAction(ega);
            }
            else if (button.equals(shuffleAndDealButton)) {
                CribDealAction da = new CribDealAction(this);
                game.sendAction(da);
            }

        }

        //may not be needed, helpful to have just in case i miss something
        /*else {
            flash(Color.RED, 50);
            Log.d("onClick", "failed");
        }*/
    }
}
