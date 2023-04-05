package com.example.cribbagegame.CribGame;

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

        if(action instanceof CribDiscardAction){
            Card[] cards = ((CribDiscardAction) action).getCards();
            cribGameState.discard(cards[0], pID);
            cribGameState.discard(cards[1], pID);

            return true;
        }
        if(action instanceof CribPlayCardAction){
            Card playedCard = ((CribPlayCardAction) action).getPlayedCard();
            cribGameState.playCard(playedCard);

            return true;
        }
        if(action instanceof EndTurnAction){
            cribGameState.endTurn(pID);
            return true;
        }
        if(action instanceof CribDealAction){
            cribGameState.dealCards();
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
        return null;
    }
}
