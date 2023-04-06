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
    ///////////////////////////////////////////////////
    // ************** instance variables ************
    ///////////////////////////////////////////////////
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
    //player 1 turn: 0
    //player 2 turn: 1

    private boolean isPlayer1Dealer;

    private int phase; //this should be fleshed more out to accom for shuffle/deal/setUp of the game
    // 0 Menu
    // 1 In Round
    // 2 Score Screen

    private int p1RoundScore;
    private int p2RoundScore;
    private int roundScore;
    private Random gen;

    /**
     * Constructor for objects of class CribbageGameState
     */
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

        playerTurn = 0; // TBI: change value to show no player turn has been set yet
        isPlayer1Dealer = true;

        phase = 0;
        p1RoundScore = 0;
        p2RoundScore = 0;
        roundScore = 0;

        gen = new Random();
    }

    /**
     * Copy constructor for class CribbageGameState
     *
     * @param gamestate
     * 		the CribbageGameState object that we want to clone
     */
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
        cardDeck = new Deck();
        Card c = null;
        for (int i = 0; i < 6; i++){
            c = cardDeck.nextCard();
            c.setPlayerID(0);
            p1Hand.add(c);

            c = cardDeck.nextCard();
            c.setPlayerID(1);
            p2Hand.add(c);
        }
        faceUpCard = cardDeck.nextCard();
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
        if(playerTurn == 2) {
            playerTurn = gen.nextInt(2);

            if(playerTurn == 1) {
                isPlayer1Dealer = false;
            } else {
                isPlayer1Dealer = true;
            }
        } else {
            if(playerTurn == 0) {
                playerTurn = 1;
            } else {
                playerTurn = 0;
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
        if(c.getCardScore() + roundScore <= 31) {
            return true;
        }
        else {
            return false;
        }
    }

    //playCard just simply removes card from the hand
    // to the table in the inPlayCards arrayList
    public boolean playCard(Card c) {
        if(playerTurn == 0) {
            if(isPlayable(c) == true) {
                p1Hand.remove(c);
                p1Hand.trimToSize();
                inPlayCards.add(c);
                roundScore += c.getCardScore();
                return true;
            }
        }
        else if(playerTurn == 1){
            if(isPlayable(c) == true) {
                p2Hand.remove(c);
                p2Hand.trimToSize();
                inPlayCards.add(c);
                roundScore += c.getCardScore();
                return true;
            }
        }
        return false;
    }

    public boolean go(int playerID)
    {
        if(playerTurn == 0) {
            if(roundScore == 31)
            {
                p2Points += 2;
            }
            else
            {
                p2Points++;
            }
            roundScore = 0;
            return true;
        }
        else if(playerTurn == 1){
            if(roundScore == 31)
            {
                p1Points += 2;
            }
            else
            {
                p1Points++;
            }
            roundScore = 0;
            return true;
        }
        return false;
    }

    //can be end turn or change turn, not sure if completely needed
    public boolean endTurn(int playerID) {
        if(playerID == 1) {
            playerTurn = 0;
        }
        else {
            playerTurn = 1;
        }
        return true;
    }

    public boolean discard(Card c, int playerID) { //discard TO CRIB
        if(playerTurn == 0) {
            if(isPlayable(c) == true) {
                crib.add(c);
                p1Hand.remove(c);
                p1Hand.trimToSize();
                return true;
            }
        }
        else if(playerTurn == 1){
            if(isPlayable(c) == true) {
                crib.add(c);
                p2Hand.remove(c);
                p2Hand.trimToSize();
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
        if(playerId == 0){out = p1Hand.get(index);}
        if(playerId == 1){out = p2Hand.get(index);}
        return out;
    }
    public int getHandSize(int playerId){
        int size = 0;
        if(playerId == 0){size = p1Hand.size();}
        if(playerId == 1){size = p2Hand.size();}
        return size;
    }
    public int getRoundScore(int playerId){
        return roundScore;
    }
    public int getGameScore(int playerId){
        int out = 0;
        if(playerId == 0){out = p1Points;}
        if(playerId == 1){out = p2Points;}
        return out;
    }
    public int getPlayer0Score() { return p1Points; }
    public int getPlayer1Score() { return p2Points; }

    public int getGamePhase() { return phase; }

    public Card getLastPlayed(){
        return inPlayCards.get(inPlayCards.size()-1);
    }
    public Card getCribCard(int index){
        return crib.get(index);
    }
    public Card getFaceUpCard() {return faceUpCard;}
    public int getPlayerTurn() {return playerTurn;}
    public int getCribSize() {return crib.size();}

    //IMPORTANT: method does not return card at index! It returns the card (index) back from the last card.
    public Card getPlayedCard(int index) { return inPlayCards.get(inPlayCards.size()-1-index); }
    public int getInPlaySize() { return inPlayCards.size(); }
    public int tallyRuns(ArrayList<Card> hand)
    {
        return 0;
    }
    public int tallyDoubles(ArrayList<Card> hand)
    {
        //add all card values to an array
        int arr[] = new int[13];
        for(Card c : hand)
        {
            arr[c.getCardValue()-1]++;
        }

        //loop through array checking for values greater than 1
        int sum = 0;
        for(int i = 0; i < 13; i++)
        {
            switch (arr[i])
            {
                case 2:
                    sum += 2;
                    break;
                case 3:
                    sum += 6;
                    break;
                case 4:
                    sum += 12;
                    break;
                default:
                    break;
            }
        }
        return sum;
    }
    public int tally15s(ArrayList<Card> hand)
    {
        return 0;
    }
    public int tallyFlush(ArrayList<Card> hand)
    {
        return 0;
    }
    public int tallyHeels(ArrayList<Card> hand)
    {
        return 0;
    }
    public void tallyScore()
    {
        ArrayList<Card> h;
        int sum;

        for(int i = 0; i < 3; i++)
        {
            switch (i)
            {
                case 0:
                    h = p1Hand;
                    sum = tallyRuns(h) + tallyDoubles(h) + tally15s(h) + tallyFlush(h) + tallyHeels(h);
                    p1Points += sum;
                    break;
                case 1:
                    h = p2Hand;
                    sum = tallyRuns(h) + tallyDoubles(h) + tally15s(h) + tallyFlush(h) + tallyHeels(h);
                    p2Points += sum;
                    break;
                case 2:
                    h = crib;
                    sum = tallyRuns(h) + tallyDoubles(h) + tally15s(h) + tallyFlush(h) + tallyHeels(h);
                    if(isPlayer1Dealer)
                    {
                        p1Points += sum;
                    }
                    else
                    {
                        p2Points += sum;
                    }
                    break;
                default:
                    break;
            }
        }
    }

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