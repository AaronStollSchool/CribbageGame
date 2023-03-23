package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
import com.example.cribbagegame.GameFramework.players.GamePlayer;

public class CribDiscardAction extends GameAction {
    private static final String TAG = "CribDiscardAction";
    //private static final long serialVersionUID;

    private Card[] cards;

    public CribDiscardAction(GamePlayer player, Card card1, Card card2) {
        // invoke superclass constructor to set the player
        super(player);

        // Set Cards to be discarded
        cards = new Card[2];
        cards[0] = card1;
        cards[1] = card2;
    }

    // GETTER
    public Card[] getCards(){
        return cards;
    }

}
