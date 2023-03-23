package com.example.cribbagegame.CribGame;

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

public class CribHumanPlayer extends GameHumanPlayer {
    private TextView playerScoreTextView = null;
    private TextView oppScoreTextView    = null;
    private TextView roundTotalScoreView = null;
    private GameMainActivity activity;

    private ArrayList<ImageButton> cards = null;
    //because you can click them ?
    //doesn't work for organizeHand actions

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
        return null;
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
            /*for(int i = 0; i < ((CribbageGameState) info).getHandSize(this.playerNum); ++i) {
                if(((CribbageGameState) info).getHandCard(this.playerNum,i) == ) {

                }
            }*/
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
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreView);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreView);
        this.roundTotalScoreView   = (TextView)activity.findViewById(R.id.roundTotalView);

        /*Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);*/
    }
}
