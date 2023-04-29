package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.infoMessage.GameInfo;
import com.example.cribbagegame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.cribbagegame.GameFramework.players.GameComputerPlayer;

import java.util.ArrayList;
import java.util.Random;

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
    public CribSmartComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) {
            return;
        }

        if (((CribbageGameState) info).getPlayer0Score() >= 61 || ((CribbageGameState) info).getPlayer1Score() >= 61) {
            return;
        }

        CribbageGameState cribGameState = (CribbageGameState) this.game.getGameState();//(CribbageGameState) info;
        if (cribGameState.getPlayerTurn() != this.playerNum){
            return;
        } else {
            Log.d("ComputerPlayer", "playing... (Hand size: " + cribGameState.getHandSize(this.playerNum) + ")");
            sleep(2);
            Random r = new Random();

            /*if both hands are empty, if computer IS dealer, if both the crib and inPlay size is full,
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

                Log.d("computer", "dealing and discarding...");

                CribDealAction da = new CribDealAction(this);
                game.sendAction(da);

                sleep(0.3); //so that it looks like it's thinking lol

                Card[] discardChoice;
                if(cribGameState.isDealer(this.playerNum)){ // SmartComp is Dealer
                    discardChoice = discardOdds(cribGameState.getHand(this.playerNum), true);
                }
                else{ // Human Player is Dealer
                    discardChoice = discardOdds(cribGameState.getHand(this.playerNum), false);
                }

                CribDiscardAction cda = new CribDiscardAction(this, discardChoice[0], discardChoice[1]);
                game.sendAction(cda);
                //this might need a gameState.phase int to specify when appropriate

                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);
                Log.d("ComputerPlayer", "has dealt, discarded, and ended turn.");
                return;
            }

            else if (cribGameState.getHandSize(this.playerNum) == 0) {
                CribGoAction ga = new CribGoAction(this);
                game.sendAction(ga);
                Log.d("ComputerPlayer", "Hand is empty, said \"GO\"");
            }

            //if computer's hand is 6 (human dealt)
            else if (cribGameState.getHandSize(this.playerNum) == 6) {

                Card[] discardChoice;
                if(cribGameState.isDealer(this.playerNum)){ // SmartComp is Dealer
                    discardChoice = discardOdds(cribGameState.getHand(this.playerNum), true);
                }
                else{ // Human Player is Dealer
                    discardChoice = discardOdds(cribGameState.getHand(this.playerNum), false);
                }

                CribDiscardAction cda = new CribDiscardAction(this, discardChoice[0], discardChoice[1]);
                game.sendAction(cda);
                //this might need a gameState.phase int to specify when appropriate

                CribEndTurnAction eta = new CribEndTurnAction(this);
                game.sendAction(eta);

                Log.d("ComputerPlayer", "has discarded and ended turn.");
                return;
            }

            //if computer's hand is 4 (both distributed to the crib and it's computer's turn)
            else if (cribGameState.getHandSize(this.playerNum) <= 4 && cribGameState.getHandSize(this.playerNum) >= 1) {
                //plays the most optimal card in hand
                int maxScore = -1;
                int maxIndex = -1;
                int currScore = 0;
                for (int i = 0; i < cribGameState.getHandSize(this.playerNum); i++) {                                           // PLAY LOOP
                    if (cribGameState.isPlayable(cribGameState.getHandCard(this.playerNum, i))) {
                        currScore = cribGameState.scorePredict(cribGameState.getHandCard(this.playerNum, i));
                        if(currScore > maxScore)
                        {
                            maxScore = currScore;
                            maxIndex = i;
                        }
                    }
                }

                if(maxIndex != -1)
                {
                    CribPlayCardAction pca = new CribPlayCardAction(this,
                            cribGameState.getHandCard(this.playerNum, maxIndex));                                                  // maybe loop to determine playable cards first
                    game.sendAction(pca);                                                                                   // then go or determine which card to play

                    CribEndTurnAction eta = new CribEndTurnAction(this);
                    game.sendAction(eta);
                    Log.d("ComputerPlayer", "has played card and ended turn.");
                    return;
                }

                //if no one called go yet
                if (cribGameState.getPlayerSaidGoFirst() == -1) {
                    //computer has no playable cards, computer says "go"
                    // the go action WILL ENSUE once humans calls GO action.
                    CribGoAction ga = new CribGoAction(this);
                    game.sendAction(ga);

                    //CribEndTurnAction eta = new CribEndTurnAction(this);
                    //game.sendAction(eta);
                    Log.d("ComputerPlayer", "says \"Go\".");
                }
                // !!!! if the other player "said" go first, other should call it
                else if (cribGameState.getPlayerSaidGoFirst() == 1-this.playerNum) {
                    CribGoAction ga = new CribGoAction(this);
                    game.sendAction(ga);

                    //temp - need to see if this fixes something
                    //CribEndTurnAction eta = new CribEndTurnAction(this);
                    //game.sendAction(eta);
                    Log.d("ComputerPlayer", "Cannot play any more cards and said \"GO\"");
                }
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

    private Card[] discardOdds(ArrayList<Card> hand, boolean isDealer){
        Card[][] MaxMin = new Card[2][2];
        Card[] out = new Card[2];
        double max = 0.0;
        double min = 10.0;
        double[][] cribValue = new double[6][6];
        double[][] cribStatsYour = { // Array of average crib values for given discard pair [val1][val2]
                //http://www.cribbageforum.com/YourCrib.htm     **** reference for values ****
                {5.51,4.35,4.69,5.42,5.38,3.98,4.05,3.77,3.49,3.51,3.57,3.50,3.36},
                {4.35,5.82,7.14,4.64,5.54,4.15,3.78,3.82,3.91,3.71,4.05,3.86,3.57},
                {4.69,7.13,6.08,5.13,5.97,4.05,3.33,4.13,4.09,3.51,4.07,3.65,3.89},
                {5.41,4.63,5.12,5.54,6.53,3.95,3.61,3.77,3.82,3.60,3.98,3.63,3.61},
                {5.38,5.53,5.97,6.53,8.88,6.81,6.01,5.56,5.43,6.70,7.09,6.59,6.73},
                {3.97,4.15,4.05,3.95,6.80,5.76,5.14,4.63,5.11,3.31,3.45,3.73,3.21},
                {4.05,3.77,3.33,3.61,6.00,5.14,5.87,6.44,4.06,3.59,3.83,3.39,3.47},
                {3.76,3.82,4.13,3.77,5.56,4.63,6.44,5.50,4.77,3.72,3.93,3.19,3.04},
                {3.49,3.90,4.08,3.82,5.43,5.11,4.06,4.76,5.21,4.40,4.01,2.99,3.07},
                {3.50,3.71,3.51,3.60,6.69,3.31,3.59,3.72,4.39,4.72,4.76,3.17,2.84},
                {3.56,4.05,4.06,3.98,7.08,3.45,3.83,3.92,4.01,4.75,5.28,4.83,3.92},
                {3.50,3.85,3.64,3.63,6.59,3.73,3.38,3.19,2.99,3.16,4.82,4.93,3.48},
                {3.36,3.56,3.89,3.61,6.72,3.20,3.46,3.04,3.07,2.83,3.92,3.48,4.30}
        };
        double[][] cribStatsOpp = {}; // implement in future
        //http://www.cribbageforum.com/OppCrib.htm
        for(int i = 0; i < hand.size(); i++){ // assign proper values for each card combo in hand
            for(int j = 0; j < hand.size(); j++){
                if(i==j){cribValue[i][j] = 0.0;}
                else {
                    cribValue[i][j] = cribStatsYour
                            [hand.get(i).getCardValue()-1]
                            [hand.get(j).getCardValue()-1];
                    if(cribValue[i][j] > max){
                        max = cribValue[i][j];
                        MaxMin[0][0] = hand.get(i);
                        MaxMin[0][1] = hand.get(j);
                    }
                    if(cribValue[i][j] < min && cribValue[i][j] != 0.0){
                        min = cribValue[i][j];
                        MaxMin[1][0] = hand.get(i);
                        MaxMin[1][1] = hand.get(j);
                    }
                }
            }
        }
        if(isDealer){
            out[0] = MaxMin[0][0];
            out[1] = MaxMin[0][1];
        }
        else{
            out[0] = MaxMin[1][0];
            out[1] = MaxMin[1][1];
        }
        return out;
    }
}



