package com.example.cribbagegame;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.cribbagegame.CribGame.Card;
import com.example.cribbagegame.CribGame.CribbageGameState;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CribbageUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // Aaron
    @Test
    public void endTurn() throws Exception{ // Change this method in the future
        CribbageGameState state = new CribbageGameState();

        int turn = state.getPlayerTurn();
        state.endTurn(turn);

        assertNotEquals(turn, state.getPlayerTurn());

    }

    // Aaron
    @Test
    public void removeCards() throws Exception{
        CribbageGameState state = new CribbageGameState();
        state.dealCards();

        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn());
        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn());

        state.endTurn(state.getPlayerTurn()); // THIS METHOD IS REALLY STUPID WE SHOULD CHANGE IT ********

        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn());
        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn());

        state.endTurn(state.getPlayerTurn());

        state.playCard(state.getHandCard(state.getPlayerTurn(), 0));

        assertNotEquals(0, state.getHandSize(0));
        assertNotEquals(0, state.getHandSize(1));
        assertNotEquals(0, state.getCribSize());
        assertNotEquals(0, state.getInPlaySize());

        state.removeCards();

        assertEquals(0, state.getHandSize(0));
        assertEquals(0, state.getHandSize(1));
        assertEquals(0, state.getCribSize());
        assertEquals(0, state.getInPlaySize());

    }

    // Aaron
    @Test
    public void dealCards() throws Exception{
        CribbageGameState state = new CribbageGameState();
        state.dealCards();

        assertEquals(state.getHandSize(0), 6);
        assertEquals(state.getHandSize(1), 6);
        assertNotNull(state.getFaceUpCard());
        for (int i = 0; i < 6; i++){
            assertNotNull(state.getHandCard(0, i));
            assertNotNull(state.getHandCard(1, i));
        }
    }

    // Aaron
    @Test
    public void returnCards() throws Exception{
        CribbageGameState state = new CribbageGameState();
        state.dealCards(); // setup

        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn()); // discards 2 cards
        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn());

        state.endTurn(state.getPlayerTurn()); // THIS METHOD IS REALLY STUPID WE SHOULD CHANGE IT ********

        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn()); // discards 2 cards
        state.discard(state.getHandCard(state.getPlayerTurn(), 0),state.getPlayerTurn());

        state.endTurn(state.getPlayerTurn());
        state.playCard(state.getHandCard(state.getPlayerTurn(), 0)); // plays a card
        state.endTurn(state.getPlayerTurn());
        state.playCard(state.getHandCard(state.getPlayerTurn(), 0)); // plays another card

        int p1HandsizeA = state.getHandSize(0);
        int p2HandsizeA = state.getHandSize(1);
        int playsizeA = state.getInPlaySize();

        assertEquals(playsizeA, 2);

        state.returnCards();
        assertEquals(0, state.getInPlaySize());
        assertEquals(p1HandsizeA+1, state.getHandSize(0));
        assertEquals(p2HandsizeA+1, state.getHandSize(1));




    }

    //Aether
    @Test
    public void isPlayable(){}

    // Aether
    @Test
    public void discard(){}

    // Aether
    @Test
    public void setPlayerTurn(){}

    // Kincaid
    @Test
    public void tallyRuns(){}

    // Kincaid
    @Test
    public void tallyDoubles(){}
}