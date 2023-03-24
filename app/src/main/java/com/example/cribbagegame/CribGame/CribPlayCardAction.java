package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
import com.example.cribbagegame.GameFramework.players.GamePlayer;

public class CribPlayCardAction extends GameAction {
    private static final String TAG = "CribPlayCardAction";
    //private static final long serialVersionUID;

    private Card playedCard;
    public CribPlayCardAction(GamePlayer player, Card c) {
        super(player);

        playedCard = c;
    }

    public Card getPlayedCard(){
        return playedCard;
    }
}
