package com.example.cribbagegame.CribGame;

import android.util.Log;

import com.example.cribbagegame.GameFramework.infoMessage.GameState;

import java.util.ArrayList;
import java.util.Random;

/**
 * @authors Aaron Stoll, Aether Mocker, Kincaid Larson, Sean Murray
 * @version March 2023
 */
public class CribbageGameState extends GameState {
    // Total Game Score for each player
    private int p1Points;
    private int p2Points;

    // Each Players hand of Card objects
    private ArrayList<Card> p1Hand;
    private ArrayList<Card> p2Hand;
    private Deck cardDeck;

    // Arraylists for cards on table during play
    private ArrayList<Card> inPlayCards;
    private ArrayList<Card> crib;

    private Card faceUpCard;

    private boolean isHard;

    private int playerTurn;
    //player 1 turn: 1
    //player 2 turn: 2

    private boolean isPlayer1Dealer;

    private int phase;
    // 0 Menu
    // 1 In Round
    // 2 Score Screen

    private int p1RoundScore;
    private int p2RoundScore;
    private int roundScore;
    private Random gen;

    public CribbageGameState(){
        p1Points = 0;
        p2Points = 0;

        cardDeck = new Deck();

        p1Hand = new ArrayList<Card>();
        p2Hand = new ArrayList<Card>();

        inPlayCards = new ArrayList<Card>();
        crib = new ArrayList<Card>();

        faceUpCard = cardDeck.nextCard();

        isHard = true;

        playerTurn = 0;

        phase = 0;
        p1RoundScore = 0;
        p2RoundScore = 0;
        roundScore = 0;

        gen = new Random();
    }

    //copy constructor
    //takes a given GameState and does a deep copy
    public CribbageGameState(CribbageGameState gamestate) {
        this.p1Points = gamestate.p1Points;
        this.p2Points = gamestate.p2Points;

        this.p1Hand = new ArrayList<Card>();
        this.p1Hand.addAll(gamestate.p1Hand);
        this.p2Hand = new ArrayList<Card>();
        this.p2Hand.addAll(gamestate.p2Hand);

        this.inPlayCards = new ArrayList<Card>();
        //this.inPlayCards.addAll(gamestate.inPlayCards);
        this.crib = new ArrayList<Card>();
        //this.crib.addAll(gamestate.crib);

        this.faceUpCard = new Card(gamestate.faceUpCard.getCardValue(), gamestate.faceUpCard.getSuit(), gamestate.faceUpCard.getCardID());

        this.playerTurn = gamestate.playerTurn;

        this.isHard = gamestate.isHard;

        this.isPlayer1Dealer = gamestate.isPlayer1Dealer;

        //need to set up GamePhase
        this.phase = gamestate.phase;

        this.p1RoundScore = gamestate.p1RoundScore;
        this.p2RoundScore = gamestate.p2RoundScore;
        this.roundScore = gamestate.roundScore;

        this.gen = gamestate.gen;
    }

    public boolean dealCards() {
        for (int i = 0; i < 6; i++){
            p1Hand.add(cardDeck.nextCard());
            p2Hand.add(cardDeck.nextCard());
        }
        return true;
    }
    public boolean setFaceUpCard() {
        faceUpCard = cardDeck.nextCard();
        return true;
    }

    /*
     * Randomly initializes player turn for first round, and toggles
     * for every subsequent call. Dealer will always be opposite of
     * player turn (i.e. if it is player 1's turn, player 2 is dealer).
     */
    public boolean setPlayerTurn(int playerID) {
        if(playerTurn == 0) {
            playerTurn = gen.nextInt(2) + 1;

            if(playerTurn == 1) {
                isPlayer1Dealer = false;
            } else {
                isPlayer1Dealer = true;
            }
        } else {
            if(playerTurn == 1) {
                playerTurn = 2;
            } else {
                playerTurn = 1;
            }

            isPlayer1Dealer = !(isPlayer1Dealer);
        }
        return true;
    }

    public boolean exitGame(int playerID){
        if (phase == 0){ // Game Phase is Menu cannot exit
            return false;
        }
        if (playerID != playerTurn){ // Not given players turn
            return false;
        }
        phase = 0;
        return true;
    }

    public boolean setUpBoard() {
        dealCards();
        setFaceUpCard();
        setPlayerTurn(playerTurn);
        return true;
    }

    public boolean isPlayable(Card c) {
        if(c.getCardValue() + roundScore <= 31) {
            return true;
        }
        else {
            return false;
        }
    }

    //playCard just simply removes card from the hand
    // to the table in the inPlayCards arrayList
    public boolean playCard(Card c) {
        if(playerTurn == 1) {
            if(isPlayable(c) == true) {
                p1Hand.remove(c);
                p1Hand.trimToSize();
                inPlayCards.add(c);
                return true;
            }
        }
        else if(playerTurn == 2){
            if(isPlayable(c) == true) {
                p2Hand.remove(c);
                p2Hand.trimToSize();
                inPlayCards.add(c);
                return true;
            }
        }
        return false;
    }

    //can be end turn or change turn, not sure if completely needed
    public boolean endTurn(int playerID) {
        if(playerTurn == playerID) { //if it's player 1's turn, make it player 2's.
            playerTurn = 2;
        }
        else {
            playerTurn = 1;
        }
        return true;
    }

    public boolean discard(Card c, int playerID) { //discard TO CRIB
        if(playerTurn == 1) {
            if(isPlayable(c) == true) {
                p1Hand.remove(c);
                p1Hand.trimToSize();
                crib.add(c);
                return true;
            }
        }
        else if(playerTurn == 2){
            if(isPlayable(c) == true) {
                p2Hand.remove(c);
                p2Hand.trimToSize();
                crib.add(c);
                return true;
            }
        }
        return false;
    }

    public String stringHands(ArrayList<Card> c) {
        String[] hand = new String[c.size()];
        String hands = "";

        for(int i = 0; i < c.size(); ++i) {
            hand[i] = c.get(i).toString();
            hands += hand[i] + ", ";
        }
        return hands;
    }

    // GETTERS
    public Card getHandCard(int playerId, int index){
        Card out = null;
        if(playerId == 1){out = p1Hand.get(index);}
        if(playerId == 2){out = p2Hand.get(index);}
        return out;
    }
    public int getHandSize(int playerId){
        int size = 0;
        if(playerId == 1){size = p1Hand.size();}
        if(playerId == 2){size = p2Hand.size();}
        return size;
    }
    public int getRoundScore(int playerId){
        int out= 0;
        if(playerId == 1){out = p1RoundScore;}
        if(playerId == 2){out = p2RoundScore;}
        return out;
    }
    public int getGameScore(int playerId){
        int out = 0;
        if(playerId == 1){out = p1Points;}
        if(playerId == 2){out = p2Points;}
        return out;
    }
    public int getPlayer0Score() { return p1RoundScore; }
    public int getPlayer1Score() { return p2RoundScore; }

    public Card getLastPlayed(){
        return inPlayCards.get(inPlayCards.size()-1);
    }
    public Card getCribCard(int index){
        return crib.get(index);
    }
    public Card getFaceUpCard() {return faceUpCard;}
    public int getPlayerTurn() {return playerTurn;}

    @Override
    public String toString() {

        String p1Vals = "Player 1 Points: " + String.valueOf(p1Points) +
                " Player 1 Hand: " + String.valueOf(stringHands(p1Hand)) + " Player 1 Round Score: " + p1RoundScore;
        String p2Vals = " Player 2 Points: " + String.valueOf(p2Points) +
                " Player 2 Hand: " +  String.valueOf(stringHands(p2Hand)) + " Player 2 Round Score: " + p2RoundScore +
                " Round total score: " + roundScore;

        return p1Vals + p2Vals;
    }
}