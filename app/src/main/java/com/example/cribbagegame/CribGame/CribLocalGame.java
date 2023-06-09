package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.LocalGame;
import com.example.cribbagegame.GameFramework.actionMessage.EndTurnAction;
import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
import com.example.cribbagegame.GameFramework.actionMessage.GameOverAckAction;
import com.example.cribbagegame.GameFramework.players.GamePlayer;

/**
 * class CribLocalGame controls the play of the game
 *
 * @authors Aaron, Aether, Kincaid, Sean
 * @version March 2023
 */
public class CribLocalGame extends LocalGame {

    private CribbageGameState cribGameState;

    /**
     * ctor that creates a new game state
     */
    public CribLocalGame() {
        cribGameState = new CribbageGameState();
        state = cribGameState;
    }

    /**
     * @param playerIdx - the player's ID
     * @return true if the player with the given ID can take an action right now
     */
    @Override
    protected boolean canMove(int playerIdx){
        if (playerIdx == cribGameState.getPlayerTurn()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @param action - The move that the player has sent to the game
     * @return true if the action was taken or false if the action is invalid/illegal
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //check if the given action is an "instanceof" any game action
        //check which player's turn it is
        //execute the given action for the player
        //else, return false
        int pID = cribGameState.getPlayerTurn(); // Player ID for Action
        Log.wtf("makeMove", "" + action.getClass().getSimpleName() + " by Player " + pID);

        if(action instanceof CribDiscardAction){
            Card[] cards = ((CribDiscardAction) action).getCards();
            cribGameState.discard(cards[0], pID);
            cribGameState.discard(cards[1], pID);
            Log.d("CribDiscardAction", "Cards discarded: " + cards[0].toString() + " " + cards[1].toString());
            sendUpdatedStateTo(action.getPlayer());
            return true;
        }
        if(action instanceof CribPlayCardAction){
            cribGameState.setPlayerSaidGoFirst(-1);
            Card playedCard = ((CribPlayCardAction) action).getPlayedCard();
            cribGameState.playCard(playedCard);
            cribGameState.scorePoints(pID);//score points in play
            Log.d("CribPlayCardAction", "Card played: " + playedCard.toString());
            Log.d("CribPlayCardAction", "in play size: "+ cribGameState.getInPlaySize());
            return true;
        }
        if(action instanceof CribGoAction){
            Log.d("CribLocalGame", "Player " + pID + " said \"GO\" saidGoFirst = " + cribGameState.getPlayerSaidGoFirst());
            if(cribGameState.getPlayerSaidGoFirst() == -1) {
                cribGameState.setPlayerSaidGoFirst(pID);
            }
            else if(cribGameState.getPlayerSaidGoFirst() != pID) {
                if(cribGameState.getPlayerSaidGoFirst() == 0)
                {
                    cribGameState.go(1);
                    Log.d("Player 1 called go. ", "");
                }
                else
                {
                    cribGameState.go(0);
                    Log.d("Player 0 called go.", "");
                }
                //cribGameState.go(1-cribGameState.getPlayerSaidGoFirst());
                cribGameState.setPlayerSaidGoFirst(-1);
                Log.d("PlayerSaidGoFirst", "set to -1");
            }
            return true;
        }
        if(action instanceof CribEndTurnAction){
            cribGameState.endTurn(pID);
            Log.d("Player turn", "" + cribGameState.getPlayerTurn());
            return true;
        }
        if(action instanceof CribSwitchDealerAction) {
            cribGameState.switchDealer();
            Log.d("CribLocalGame: CribSwitchAction", "Dealer is Player " + cribGameState.getIsPlayer1Dealer());
            return true;
        }
        if(action instanceof CribDealAction){
            //cribGameState.tallyScore();
            cribGameState.setRoundScore(0);
            cribGameState.dealCards();
            return true;
        }
        if(action instanceof CribTallyAction){
            cribGameState.returnCards();
            cribGameState.tallyScore();
            cribGameState.removeCards();
            return true;
        }
        if(action instanceof CribExitGameAction){
            cribGameState.exitGame(pID);
            return true;
        }
        return false;
    }//makeMove

    /**
     * This method sends the updated game state to a given player
     * @param p - the player to send the information to
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        CribbageGameState cribGameState1 = new CribbageGameState(cribGameState);
        p.sendInfo(cribGameState1);
    }

    /**
     * A method that checks if the game is over
     * @return a message that says who the winner of the game is (or null if the game is not over)
     */
    @Override
    protected String checkIfGameOver() {
        if (cribGameState.getPlayer0Score() >= 61) {
            return "Game Over! " + this.playerNames[0] + " won!";
        }
        else if (cribGameState.getPlayer1Score() >= 61) {
            return "Game Over! " + this.playerNames[1] + " won!";
        }
        return null;
    }

}

