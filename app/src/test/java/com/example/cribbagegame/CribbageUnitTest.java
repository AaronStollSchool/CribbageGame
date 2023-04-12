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
    public void endTurn() throws Exception{
        CribbageGameState state = new CribbageGameState();


    }

    // Aaron
    @Test
    public void removeCards() throws Exception{}

    // Aaron
    @Test
    public void dealCards() throws Exception{}

    // Aaron
    @Test
    public void returnCards() throws Exception{}

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