package com.example.cribbagegame.CribGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @authors Aaron Stoll, Aether Mocker, Kincaid Larson, Sean Murray
 * @version March 2023
 */
public class Deck {
    private ArrayList<Card> gameDeck;
    private int deckIndex;

    public Deck(){
        gameDeck = new ArrayList<Card>();
        deckIndex = 0;

        for (int i = 1; i <= 4; i++){
            for(int j = 2; j <= 14; j++){
                int val = j;
                int suit = i;
                int cardID = j + i;
                Card holder = new Card(val, suit, cardID);
                gameDeck.add(holder);
            }
        }
        Collections.shuffle(gameDeck);
    }

    public Card nextCard(){
        Card out = gameDeck.get(deckIndex);
        deckIndex++;

        return out;
    }
}