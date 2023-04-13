package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
import com.example.cribbagegame.GameFramework.players.GamePlayer;

public class CribSwitchDealerAction extends GameAction {
    private static final String TAG = "CribSwitchDealerAction";
    //private static final long serialVersionUID;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public CribSwitchDealerAction(GamePlayer player){ super(player); }
}
