package com.example.cribbagegame;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.cribbagegame.CribGame.Card;
import com.example.cribbagegame.CribGame.CribbageGameState;
import com.example.cribbagegame.GameFramework.infoMessage.GameState;

import java.util.ArrayList;


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
    public void tallyRuns(){
        CribbageGameState cgs = new CribbageGameState();

        ArrayList<Card> hand1 = new ArrayList<>();
        hand1.add(new Card(1, 1, 0));
        hand1.add(new Card(2, 1, 0));
        hand1.add(new Card(3, 1, 0));
        hand1.add(new Card(4, 1, 0));
        cgs.setFaceUpCard(new Card(5, 1, 0));

        //test that a run of 5 returns 5
        assertEquals(5, cgs.tallyRuns(hand1));

        ArrayList<Card> hand2 = new ArrayList<>();
        hand2.add(new Card(5, 1, 0));
        hand2.add(new Card(1, 1, 0));
        hand2.add(new Card(4, 1, 0));
        hand2.add(new Card(3, 1, 0));
        cgs.setFaceUpCard(new Card(6, 1, 0));

        //test that a run of 4 returns 4
        assertEquals(4, cgs.tallyRuns(hand2));

        ArrayList<Card> hand3 = new ArrayList<>();
        hand3.add(new Card(1, 1, 0));
        hand3.add(new Card(2, 1, 0));
        hand3.add(new Card(5, 1, 0));
        hand3.add(new Card(4, 1, 0));
        cgs.setFaceUpCard(new Card(6, 1, 0));

        //test that a run of 3 returns 3
        assertEquals(3, cgs.tallyRuns(hand3));

        ArrayList<Card> hand4 = new ArrayList<>();
        hand4.add(new Card(3, 1, 0));
        hand4.add(new Card(4, 1, 0));
        hand4.add(new Card(4, 1, 0));
        hand4.add(new Card(5, 1, 0));
        cgs.setFaceUpCard(new Card(6, 1, 0));

        //test that a run of 4 with 2 duplicate cards returns 8
        assertEquals(8, cgs.tallyRuns(hand4));

        ArrayList<Card> hand5 = new ArrayList<>();
        hand5.add(new Card(1, 1, 0));
        hand5.add(new Card(2, 1, 0));
        hand5.add(new Card(2, 1, 0));
        hand5.add(new Card(2, 1, 0));
        cgs.setFaceUpCard(new Card(3, 1, 0));

        //test that a run of 3 with 3 duplicate cards returns  9
        assertEquals(9, cgs.tallyRuns(hand5));

        ArrayList<Card> hand6 = new ArrayList<>();
        hand6.add(new Card(1, 1, 0));
        hand6.add(new Card(3, 1, 0));
        hand6.add(new Card(4, 1, 0));
        hand6.add(new Card(4, 1, 0));
        cgs.setFaceUpCard(new Card(5, 1, 0));

        //test that a run of 3 with 2 duplicate cards returns 6
        assertEquals(6, cgs.tallyRuns(hand6));
    }

    // Kincaid
    @Test
    public void tallyDoubles(){
        CribbageGameState cgs = new CribbageGameState();

        ArrayList<Card> hand1 = new ArrayList<>();
        hand1.add(new Card(1, 1, 0));
        hand1.add(new Card(2, 1, 0));
        hand1.add(new Card(4, 1, 0));
        hand1.add(new Card(4, 1, 0));

        //test that a single pair returns 2
        assertEquals(2, cgs.tallyDoubles(hand1));

        ArrayList<Card> hand2 = new ArrayList<>();
        hand2.add(new Card(3, 1, 0));
        hand2.add(new Card(4, 1, 0));
        hand2.add(new Card(4, 1, 0));
        hand2.add(new Card(4, 1, 0));

        //test that a single triple returns 6
        assertEquals(6, cgs.tallyDoubles(hand2));

        ArrayList<Card> hand3 = new ArrayList<>();
        hand3.add(new Card(4, 1, 0));
        hand3.add(new Card(4, 1, 0));
        hand3.add(new Card(4, 1, 0));
        hand3.add(new Card(4, 1, 0));

        //test that a single quad returns 12
        assertEquals(12, cgs.tallyDoubles(hand3));

        ArrayList<Card> hand4 = new ArrayList<>();
        hand4.add(new Card(2, 1, 0));
        hand4.add(new Card(2, 1, 0));
        hand4.add(new Card(4, 1, 0));
        hand4.add(new Card(4, 1, 0));
        //test that 2 pairs returns 4
        assertEquals(4, cgs.tallyDoubles(hand4));
    }
}