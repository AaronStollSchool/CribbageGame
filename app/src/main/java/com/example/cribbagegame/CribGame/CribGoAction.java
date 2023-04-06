package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
import com.example.cribbagegame.GameFramework.players.GamePlayer;

public class CribGoAction extends GameAction {

    private static final String TAG = "CribGoAction";
    //private static final long serialVersionUID;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CribGoAction(GamePlayer player) {
        super(player);
    }
}
