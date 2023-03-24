package com.example.cribbagegame.CribGame;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cribbagegame.GameFramework.GameMainActivity;
import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
import com.example.cribbagegame.GameFramework.players.GameHumanPlayer;
import com.example.cribbagegame.R;

import java.util.ArrayList;

public class CribHumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    private TextView playerScoreTextView = null;
    private TextView oppScoreTextView    = null;
    private TextView roundTotalScoreView = null;
    private GameMainActivity activity;

    private ArrayList<ImageButton> cards = new ArrayList<ImageButton>();

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
        if(info instanceof CribbageGameState) {
            if(this.playerNum == 1) {
                playerScoreTextView.setText("" + ((CribbageGameState) info).getPlayer1Score());
                oppScoreTextView.setText("" + ((CribbageGameState) info).getPlayer0Score());
            }
            else if(this.playerNum == 0) {
                playerScoreTextView.setText("" + ((CribbageGameState) info).getPlayer0Score());
                oppScoreTextView.setText("" + ((CribbageGameState) info).getPlayer1Score());
            }
            roundTotalScoreView.setText("" + ((CribbageGameState) info).getRoundScore(this.playerNum));

            //setImageResource for each card in hand, because each is an ImageButton
            for(int k = 0; k < ((CribbageGameState) info).getHandSize(this.playerNum); ++k) {
                for(int i = 1; i <= 4; ++i) {
                    for(int j = 1; j <= 14; ++j) {
                        int cardID =  i + j;
                        switch (((CribbageGameState) info).getHandCard(this.playerNum,k).getCardID()) {
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

                            case 141:
                                cards.get(k).setImageResource(R.drawable.ace_of_diamonds);
                                break;
                            case 142:
                                cards.get(k).setImageResource(R.drawable.ace_of_hearts);
                                break;
                            case 143:
                                cards.get(k).setImageResource(R.drawable.ace_of_spades);
                                break;
                            case 144:
                                cards.get(k).setImageResource(R.drawable.ace_of_clubs);
                                break;

                            default:
                                cards.get(k).setImageResource(R.drawable.black_joker);
                                break;
                            }
                    }
                }
            }
        }
        else {
            flash(Color.RED, 50);
        }
    }//recieveInfo

    @Override
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        this.activity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.activity_main);

        //Initialize the widget reference member variables
        this.playerScoreTextView = activity.findViewById(R.id.yourScoreView);
        this.oppScoreTextView = activity.findViewById(R.id.oppScoreView);
        this.roundTotalScoreView = activity.findViewById(R.id.roundTotalView);
        this.cards.set(0, activity.findViewById(R.id.card1));
        this.cards.set(1, activity.findViewById(R.id.card2));
        this.cards.set(2, activity.findViewById(R.id.card3));
        this.cards.set(3, activity.findViewById(R.id.card4));
        this.cards.set(4, activity.findViewById(R.id.card5));
        this.cards.set(5, activity.findViewById(R.id.card6));


        /*Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View button) {
        //TO IMPLEMENT: idk how to do this
        if (button instanceof ImageButton) {
            //PigRollAction pra = new PigRollAction(this);
            //game.sendAction(pra);
        }
        else if (button instanceof Button) {
            //PigHoldAction pha = new PigHoldAction(this);
            //game.sendAction(pha);
        }
    }
}
