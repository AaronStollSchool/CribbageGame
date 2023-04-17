package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
import com.example.cribbagegame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.cribbagegame.GameFramework.players.GameComputerPlayer;

import java.util.Random;

/**
 * CribComputerPlayer -
 * This is the 'dumb' computer player. It will deal, discard,
 * and play the first card from their hand with no scoring strategy.
 *
 * @author Aether, Aaron, Kincaid, Sean
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

        CribbageGameState cribGameState = (CribbageGameState) this.game.getGameState();//(CribbageGameState) info;

        if (cribGameState.getPlayerTurn() != this.playerNum){
            Log.d("ComputerPlayer", "it's not my turn!");
            return;
        }
        else {
            Log.d("ComputerPlayer", "playing... " + cribGameState.getHandSize(this.playerNum));
            sleep(2);
            Random r = new Random();

            /*if both hands are empty, if computer is NOT dealer, if it is computer's turn,
            AND if both points arent empty (it's not the beginning of the game)*/
            if (((CribbageGameState) info).getHandSize(0) == 0
                    && ((CribbageGameState) info).getHandSize(1) == 0
                    && !((CribbageGameState) info).isDealer(this.playerNum)
                    && ((CribbageGameState) info).getPlayerTurn() == this.playerNum) {

                CribSwitchDealerAction sda = new CribSwitchDealerAction(this);
                game.sendAction(sda);
                Log.d("ComputerPlayer", "CribSwitchAction");

//                CribEndTurnAction eta = new CribEndTurnAction(this);
//                game.sendAction(eta);
                Log.d("ComputerPlayer", "has become dealer.");
                Log.d("Dealer", " is player" + ((CribbageGameState) info).getPlayerTurn());
            }

            //if both hands are empty, and computer is dealer
            if (((CribbageGameState) info).getHandSize(0) == 0
                    && ((CribbageGameState) info).getHandSize(1) == 0
                    && ((CribbageGameState) info).isDealer(this.playerNum)) {

                CribDealAction da = new CribDealAction(this);
                game.sendAction(da);

                CribDiscardAction cda = new CribDiscardAction(this,
                        cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))),
                        cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                //this might need a gameState.phase int to specify when appropriate
                game.sendAction(cda);

                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
                Log.d("ComputerPlayer", "has dealt, discarded, and ended turn.");
                return;
            }

            //if computer's hand is 6 (human dealt)
            else if (cribGameState.getHandSize(this.playerNum) == 6) {

                CribDiscardAction da = new CribDiscardAction(this,
                        cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))),
                        cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                //this might need a gameState.phase int to specify when appropriate
                game.sendAction(da);

                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);

                Log.d("ComputerPlayer", "has discarded and ended turn.");
                return;
            }

            //if computer's hand is 4 (both distributed to the crib and it's computer's turn)
            else if (cribGameState.getHandSize(this.playerNum) <= 4) {
                //plays the first card in their hand at all times
                for(int i = 0; i < cribGameState.getHandSize(this.playerNum); i++) {
                    if (cribGameState.isPlayable(cribGameState.getHandCard(this.playerNum, i))) {
                        CribPlayCardAction pca = new CribPlayCardAction(this,
                                cribGameState.getHandCard(this.playerNum, i));
                        game.sendAction(pca);

                        CribEndTurnAction eta = new CribEndTurnAction(this);
                        game.sendAction(eta);
                        Log.d("ComputerPlayer", "has played card and ended turn.");
                        return;
                    }
                }
               /* //if no one called go yet
                if(cribGameState.getPlayerSaidGoFirst() == -1) {
                    //computer has no playable cards, computer says "go"
                    // the go action WILL ENSUE once humans calls GO action.
                    CribEndTurnAction eta = new CribEndTurnAction(this);
                    game.sendAction(eta);
                    Log.d("ComputerPlayer", "says \"Go\".");
                }
                //if the other player called go first
                else if (cribGameState.getPlayerSaidGoFirst() == 1-this.playerNum) {*/
                CribGoAction ga = new CribGoAction(this);
                game.sendAction(ga);
                Log.d("ComputerPlayer", "Cannot play any more cards and said \"GO\"");
               //}
            }

            //tally action may not be needed here but idk where else
                /*CribTallyAction ta = new CribTallyAction(this);
                game.sendAction(ta);
                Log.d("ComputerPlayer", "CribTallyAction");*/

                /*CribGoAction ga = new CribGoAction(this);
                game.sendAction(ga);
                Log.d("ComputerPlayer", "says \"GO\". ");*/

            //if computer needed to "go", check if round is over again
                /*if(((CribbageGameState) info).getHandSize(0) == 0
                        && ((CribbageGameState) info).getHandSize(1) == 0
                        && !((CribbageGameState) info).isDealer(this.playerNum)
                        && ((CribbageGameState) info).getPlayerTurn() == this.playerNum) {

                    CribSwitchDealerAction sda = new CribSwitchDealerAction(this);
                    game.sendAction(sda);

                    CribDealAction da = new CribDealAction(this);
                    game.sendAction(da);

                    CribDiscardAction cda = new CribDiscardAction(this,
                            cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))),
                            cribGameState.getHandCard(this.playerNum, r.nextInt(cribGameState.getHandSize(this.playerNum))));
                    //this might need a gameState.phase int to specify when appropriate
                    game.sendAction(cda);
                }*/

            CribEndTurnAction eta = new CribEndTurnAction(this);
            game.sendAction(eta);
            Log.d("ComputerPlayer", "has ended turn completely.");
        }
    }
}

/*
 * To be implemented:
 * - add functonality for ending player phase when no cards left (for if cpu = dealer)
 * -
 */
