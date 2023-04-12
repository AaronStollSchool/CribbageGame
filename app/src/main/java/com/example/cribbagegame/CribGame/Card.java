package com.example.cribbagegame.CribGame;

/**
 * @authors Aaron Stoll, Aether Mocker, Kincaid Larson, Sean Murray
 * @version March 2023
 */
public class Card {

    private int cardValue;
    // 1-10 Ace, regular cards
    // 11-13 Jack, Queen, King
    private int suit;
    // 1-4 diamond, heart, spade, club

    private int cardID;
    private boolean isSelected;
    private int cardScore;
    private int playerID;

    public Card(int val, int suit, int cardID){
        cardValue = val;
        this.suit = suit;
        isSelected = false;
        this.cardID = suit + 10 * val;
        if(cardValue > 10)
        {
            cardScore = 10;
        }
        else
        {
            cardScore = cardValue;
        }
    }

    public int getCardValue() { return cardValue; }
    public int getSuit() { return suit; }
    public int getCardID() { return cardID; }
    public boolean isSelected() { return isSelected; }

    public void toggleSelected() { isSelected = !(isSelected); }
    public int getCardScore() { return cardScore; }
    public int getPlayerID() { return playerID; }
    public void setPlayerID(int id) { playerID = id; }

    public String toString()
    {
        String s1 = "";
        String s2 = "";
        switch (cardValue)
        {
            case 11:
                s1 = "Jack";
                break;
            case 12:
                s1 = "Queen";
                break;
            case 13:
                s1 = "King";
                break;
            case 1:
                s1 = "Ace";
                break;
            default:
                s1 = Integer.toString(cardValue);
                break;
        }

        switch (suit){
            case 1:
                s2 = "Diamonds";
                break;
            case 2:
                s2 = "Hearts";
                break;
            case 3:
                s2 = "Spades";
                break;
            case 4:
                s2 = "Clubs";
                break;
            default:
                s2 = "Game Test Cards";
                break;
        }

        return (s1 + " of " + s2);
    }


}