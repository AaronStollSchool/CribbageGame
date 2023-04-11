package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
import com.example.cribbagegame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.cribbagegame.GameFramework.players.GameComputerPlayer;

/**
 * CribSmartComputerPlayer -
 * This is the 'smart' computer player.
 *
 * @author
 * @version March 2023
 */
public class CribSmartComputerPlayer extends GameComputerPlayer {

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public CribSmartComputerPlayer(String name) {super(name);}

    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) {
            return;
        }

        Log.d("SmartComputerPlayer", "receiving");
        CribbageGameState cribGameState = (CribbageGameState) this.game.getGameState();//(CribbageGameState) info;

        if (cribGameState.getPlayerTurn() != this.playerNum){
            Log.d("SmartComputerPlayer", "it's not my turn!");
            return;
        }

        /* todo - implement actual functionality */
    }
}
