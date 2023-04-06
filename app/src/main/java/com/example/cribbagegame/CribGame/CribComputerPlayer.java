package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
import com.example.cribbagegame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.cribbagegame.GameFramework.players.GameComputerPlayer;

import java.util.Random;

/**
 * @author Aether Mocker
 * @version March 2023
 */
public class CribComputerPlayer extends GameComputerPlayer {
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public CribComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) {
            return;
        }

        Log.d("ComputerPlayer", "receiving");
        CribbageGameState cribGameState = (CribbageGameState) info;

        if (cribGameState.getPlayerTurn() != this.playerNum){
            Log.d("ComputerPlayer", "it's not my turn!");
            return;
        }
        else {
            Log.d("ComputerPlayer", "playing" + cribGameState.getHandSize(this.playerNum));
            sleep(2);
            Random r = new Random();
            if (cribGameState.getHandSize(this.playerNum) == 0) {
                CribDealAction da = new CribDealAction(this);
                game.sendAction(da);
                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
                Log.d("ComputerPlayer", "has dealt");
            }
            else if (cribGameState.getHandSize(this.playerNum) == 6) {
                CribDiscardAction da = new CribDiscardAction(this,
                        cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))),
                        cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                //this might need a gameState.phase int to specify when appropriate
                game.sendAction(da);
                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
                Log.d("ComputerPlayer", "has discarded");
            }
            else if (cribGameState.getHandSize(this.playerNum) <= 4) {
                for(int i = 0; i < cribGameState.getHandSize(this.playerNum); i++)
                {
                    if(cribGameState.isPlayable(cribGameState.getHandCard(this.playerNum, i)))
                    {
                        CribPlayCardAction pca = new CribPlayCardAction(this,
                                cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                        game.sendAction(pca);
                        CribEndTurnAction eta = new CribEndTurnAction(this);
                        game.sendAction(eta);
                        Log.d("ComputerPlayer", "has played card");
                        break;
                    }
                    CribGoAction ga = new CribGoAction(this);
                    game.sendAction(ga);
                    Log.d("ComputerPlayer", "has played card");
                }

            }
        }
        Log.d("ComputerPlayer", "has ended turn");
    }
}

/*
 * To be implemented:
 * - add functonality for ending player phase when no cards left (for if cpu = dealer)
 * -
 */
