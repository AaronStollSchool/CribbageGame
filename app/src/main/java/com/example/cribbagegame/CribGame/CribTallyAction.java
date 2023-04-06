package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
import com.example.cribbagegame.GameFramework.players.GamePlayer;

public class CribTallyAction extends GameAction {

    private static final String TAG = "CribTallyAction";
    //private static final long serialVersionUID;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CribTallyAction(GamePlayer player) {
        super(player);
    }
}
