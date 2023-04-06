package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.LocalGame;
import com.example.cribbagegame.GameFramework.actionMessage.EndTurnAction;
import com.example.cribbagegame.GameFramework.actionMessage.GameAction;
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
        Log.d("makeMove", "" + action.getClass().getSimpleName());

        if(action instanceof CribDiscardAction){
            Card[] cards = ((CribDiscardAction) action).getCards();
            cribGameState.discard(cards[0], pID);
            cribGameState.discard(cards[1], pID);
            sendUpdatedStateTo(action.getPlayer());
            return true;
        }
        if(action instanceof CribPlayCardAction){
            Card playedCard = ((CribPlayCardAction) action).getPlayedCard();
            cribGameState.playCard(playedCard);
            Log.d("CribPlayCardAction", "in play size: "+ cribGameState.getInPlaySize());
            return true;
        }
        if(action instanceof CribGoAction){
            cribGameState.go(pID);
            return true;
        }
        if(action instanceof CribEndTurnAction){
            cribGameState.endTurn(pID);
            Log.d("Player turn", "" + cribGameState.getPlayerTurn());
            return true;
        }
        if(action instanceof CribDealAction){
            cribGameState.dealCards();
            return true;
        }
        if(action instanceof CribTallyAction){
            cribGameState.returnCards();
            cribGameState.tallyScore();
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

    /*protected int scoreRunsInPlay() {

        int[] vals = new int[13];
        int k = 0;
        boolean isRun = true;

        //checks if double is possible given number of cards in play
        if(cribGameState.getInPlaySize() < 3) return 0;


        for(int i = 0; i < 3; i++)
        {

        }


        while(isRun)
        {
            //checks if double is possible given number of cards in play
            if(cribGameState.getInPlaySize() < 3 + k) return sum;
            //checks for double
            if(cribGameState.getPlayedCard(k).getCardValue() == cribGameState.getPlayedCard(k+1).getCardValue())
            {
                k++;
                //allocates points for double (2*1 = 2), triple (2*(1+2) = 6), and quad (2*(1+2+3) = 12)
                sum += 2*k;
            }
            else
            {
                //breaks if no double
                isRun = false;
            }
        }
        return sum;
    }
    }
    protected int scoreDoublesInPlay() {

        int sum = 0;
        int k = 0;
        boolean isDouble = true;


        while(isDouble)
        {
            //checks if double is possible given number of cards in play
            if(cribGameState.getInPlaySize() < 2 + k) return sum;
            //checks for double
            if(cribGameState.getPlayedCard(k).getCardValue() == cribGameState.getPlayedCard(k+1).getCardValue())
            {
                k++;
                //allocates points for double (2*1 = 2), triple (2*(1+2) = 6), and quad (2*(1+2+3) = 12)
                sum += 2*k;
            }
            else
            {
                //breaks if no double
                isDouble = false;
            }
        }
        return sum;
    }*/
}

