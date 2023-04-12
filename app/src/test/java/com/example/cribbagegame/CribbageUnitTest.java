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
    public void endTurn(){}

    // Aaron
    @Test
    public void removeCards(){}

    // Aaron
    @Test
    public void dealCards(){}

    // Aaron
    @Test
    public void returnCards(){}

    // Aether
    @Test
    public void discard(){
        CribbageGameState game = new CribbageGameState();
        game.setUpBoard();

        Card p0_1 = game.getHandCard(game.getPlayerTurn(),0);
        Card p0_2 = game.getHandCard(game.getPlayerTurn(),1);

        assertEquals(p0_1, game.getHandCard(game.getPlayerTurn(),0));
        assertEquals(p0_2, game.getHandCard(game.getPlayerTurn(),1));

        game.discard(p0_1, game.getPlayerTurn());
        game.discard(p0_2, game.getPlayerTurn());

        assertEquals(p0_1, game.getCribCard(0));
        assertEquals(p0_2, game.getCribCard(1));

        game.setPlayerTurn(1);

        Card p1_1 = game.getHandCard(game.getPlayerTurn(),0);
        Card p1_2 = game.getHandCard(game.getPlayerTurn(),1);

        assertEquals(p1_1, game.getHandCard(game.getPlayerTurn(),0));
        assertEquals(p1_2, game.getHandCard(game.getPlayerTurn(),1));

        game.discard(p1_1, game.getPlayerTurn());
        game.discard(p1_2, game.getPlayerTurn());

        assertEquals(p1_1, game.getCribCard(2));
        assertEquals(p1_2, game.getCribCard(3));
    }

    //Aether
    @Test
    public void isPlayable(){
        CribbageGameState game = new CribbageGameState();
        game.setUpBoard();

        Card p0_1 = game.getHandCard(game.getPlayerTurn(),0);
        Card p0_2 = game.getHandCard(game.getPlayerTurn(),1);

        assertEquals(p0_1, game.getHandCard(game.getPlayerTurn(),0));
        assertEquals(p0_2, game.getHandCard(game.getPlayerTurn(),1));

        game.discard(p0_1, game.getPlayerTurn());
        game.discard(p0_2, game.getPlayerTurn());
        game.setPlayerTurn(1);

        Card p1_1 = game.getHandCard(game.getPlayerTurn(),0);
        Card p1_2 = game.getHandCard(game.getPlayerTurn(),1);

        assertEquals(p1_1, game.getHandCard(game.getPlayerTurn(),0));
        assertEquals(p1_2, game.getHandCard(game.getPlayerTurn(),1));

        game.discard(game.getHandCard(game.getPlayerTurn(),0), game.getPlayerTurn());
        game.discard(game.getHandCard(game.getPlayerTurn(),1), game.getPlayerTurn());
        game.setPlayerTurn(0);

        game.playCard(game.getHandCard(game.getPlayerTurn(), 0));

        assertEquals(true, game.isPlayable(game.getHandCard(game.getPlayerTurn(), 0)));

        game.setPlayerTurn(0);

        game.playCard(game.getHandCard(game.getPlayerTurn(), 0));

        assertEquals(true, game.isPlayable(game.getHandCard(game.getPlayerTurn(), 0)));

    }

    // Aether
    @Test
    public void setPlayerTurn(){
        CribbageGameState game = new CribbageGameState();
        game.setUpBoard();

        assertEquals(0, game.getPlayerTurn());

        game.setPlayerTurn(1);

        assertEquals(1, game.getPlayerTurn());
    }

    // Kincaid
    @Test
    public void tallyRuns(){}

    // Kincaid
    @Test
    public void tallyDoubles(){}
}