package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
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
        CribbageGameState cribGameState = (CribbageGameState) info;

        if (cribGameState.getPlayerTurn() != this.playerNum){
            return;
        }
        else {
            sleep(1000);
            Random r = new Random();
            int rand = r.nextInt(4)+1;
            if(cribGameState.getGamePhase() == 1) { //phase = 1, setUp phase of game
                CribShuffleAction sa = new CribShuffleAction(this);
                game.sendAction(sa);
                sleep(50);
                CribDealAction da = new CribDealAction(this);
                game.sendAction(da);
            }
            else {
                if (rand == 1) {
                    CribDiscardAction da = new CribDiscardAction(this,
                            cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))),
                            cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                    //this might need a gameState.phase int to specify when appropriate
                    game.sendAction(da);

                } else if (rand == 2) {
                    CribEndTurnAction eta = new CribEndTurnAction(this);
                    game.sendAction(eta);

                } else if (rand == 3) {
                    CribPlayCardAction pca = new CribPlayCardAction(this,
                            cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                    game.sendAction(pca);
                }
            }
            }
        }
    }
