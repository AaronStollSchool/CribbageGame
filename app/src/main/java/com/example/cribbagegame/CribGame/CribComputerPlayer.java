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
            return;
        } else {
            Log.d("ComputerPlayer", "playing... (Hand size: " + cribGameState.getHandSize(this.playerNum) + ")");
            sleep(2);
            Random r = new Random();

            /*if both hands are empty, if computer is IS dealer, if both the crib and inPlay size is full,
            if it is computer's turn, AND if both points arent empty
            (it's not the beginning of the game-- only assuming each round there are "sub-rounds" where go is needed)*/
            if (((CribbageGameState) info).getHandSize(0) == 0
                    && ((CribbageGameState) info).getHandSize(1) == 0
                    && ((CribbageGameState) info).getCribSize() == 4
                    && ((CribbageGameState) info).getInPlaySize() == 8
                    && ((CribbageGameState) info).isDealer(this.playerNum)
                    && ((CribbageGameState) info).getPlayerTurn() == this.playerNum) {
                CribTallyAction ta = new CribTallyAction(this);
                game.sendAction(ta);

                CribSwitchDealerAction sda = new CribSwitchDealerAction(this);
                game.sendAction(sda);
                Log.d("ComputerPlayer", "CribSwitchDealerAction");
                int dealer;
                if(((CribbageGameState) info).getIsPlayer1Dealer()) {
                    dealer = 0;
                }
                else {
                    dealer = 1;
                }
                Log.d("ComputerPlayer, Dealer", " is player " + dealer);
                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
                return;
            }

            //if both hands are empty, and computer is dealer
            if (((CribbageGameState) info).getHandSize(0) == 0
                    && ((CribbageGameState) info).getHandSize(1) == 0
                    && ((CribbageGameState) info).isDealer(this.playerNum)) {

                CribDealAction da = new CribDealAction(this);
                game.sendAction(da);

                sleep(0.3); //so that it looks like it's thinking lol

                //so it doesn't generate the same number card and input two of the same card
                int num1 = r.nextInt(cribGameState.getHandSize(this.playerNum));
                int num2 = r.nextInt(cribGameState.getHandSize(this.playerNum));
                while (num1 == num2) {
                    num2 = r.nextInt(cribGameState.getHandSize(this.playerNum));
                }

                CribDiscardAction cda = new CribDiscardAction(this,
                        cribGameState.getHandCard(this.playerNum, num1),
                        cribGameState.getHandCard(this.playerNum, num2));
                //this might need a gameState.phase int to specify when appropriate
                game.sendAction(cda);

                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
                Log.d("ComputerPlayer", "has dealt, discarded, and ended turn.");
                return;
            }

            //if computer's hand is 6 (human dealt)
            else if (cribGameState.getHandSize(this.playerNum) == 6) {

                int num1 = r.nextInt(cribGameState.getHandSize(this.playerNum));
                int num2 = r.nextInt(cribGameState.getHandSize(this.playerNum));
                while (num1 == num2) {
                    num2 = r.nextInt(cribGameState.getHandSize(this.playerNum));
                }

                CribDiscardAction da = new CribDiscardAction(this,
                        cribGameState.getHandCard(this.playerNum, num1),
                        cribGameState.getHandCard(this.playerNum, num2));
                //this might need a gameState.phase int to specify when appropriate
                game.sendAction(da);

                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);

                Log.d("ComputerPlayer", "has discarded and ended turn.");
                return;
            }

            //if computer's hand is 4 (both distributed to the crib and it's computer's turn)
            else if (cribGameState.getHandSize(this.playerNum) <= 4 && cribGameState.getHandSize(this.playerNum) != 0) {
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

                // !!!! if the other player "said" go first, other should call it
                else if (cribGameState.getPlayerSaidGoFirst() == 1-this.playerNum) {*/
                CribGoAction ga = new CribGoAction(this);
                game.sendAction(ga);
                Log.d("ComputerPlayer", "Cannot play any more cards and said \"GO\"");
               //}
            }

            //other options
            else if (cribGameState.getHandSize(this.playerNum) == 0) {
                //if other player "said" go,
                // and needs computer to call go action for them, but computer has no cards
                if (cribGameState.getPlayerSaidGoFirst() != -1) {
                    CribGoAction ga = new CribGoAction(this);
                    game.sendAction(ga);
                    Log.d("ComputerPlayer", "Cannot play any more cards and said \"GO\"");
                }
            }

            //sometimes just ends turn without /doing/ anything (?)
            CribEndTurnAction eta = new CribEndTurnAction(this);
            game.sendAction(eta);
            Log.d("ComputerPlayer", "has ended turn completely.");
        }
    }
}

/*
To figure out:
>>>how to switch back from computer-dealer-round to begin human-dealer-round
 */
