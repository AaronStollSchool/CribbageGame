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
    private int inPlay = 0;

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
    private int tally;

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
        isPlayer1Dealer = false;

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
        this.inPlayCards.addAll(gamestate.inPlayCards);
        this.crib = new ArrayList<Card>();
        this.crib.addAll(gamestate.crib);

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

    public boolean dealCards(int playerID) {
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
        if (faceUpCard.getCardScore() == 11) {
            if (playerID == 1) {
                p2Points += 2;
            }
            else if (playerID == 0) {
                p1Points += 2;
            }
        }
        return true;
    }

    public boolean removeCards() {
        p1Hand.clear();
        p2Hand.clear();
        crib.clear();
        inPlayCards.clear();
        return true;
    }

    public boolean setFaceUpCard() {
        faceUpCard = cardDeck.nextCard();
        return true;
    }

    public boolean setFaceUpCard(Card c) {
        faceUpCard = c;
        return true;
    }

    /*
     * NOT RANDOM ANYMORE
     *
     * whatever playerID is inputted becomes playerTurn
     * playerID can only be 1 or 0 as per playerID's should be
     *
     */
    public boolean setPlayerTurn(int playerID) {
        if(playerID == 1 || playerID == 0) {
            if (playerID == 1) {
                playerTurn = 1;
            } else {
                playerTurn = 0;
            }
            return true;
        }
        else {
            return false;
        }
    }

    // for later: for when dealer switches between rounds
    // different from playerTurn bc playerTurns change a lot,
    // but dealer doesn't
    public boolean switchDealer() {
        if(isPlayer1Dealer) {
            isPlayer1Dealer = false;
        }
        else {
            isPlayer1Dealer = true;
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
        dealCards(playerTurn);
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

    //not to be confused with getDealer() -- for different things
    public boolean isDealer(int playerID) {
        if(playerID == 0 && isPlayer1Dealer == false) {
            return true;
        }
        else if (playerID == 1 && isPlayer1Dealer == false) {
            return false;
        }
        else if (playerID == 0 && isPlayer1Dealer == true) {
            return false;
        }
        else if (playerID == 1 && isPlayer1Dealer == true) {
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
                inPlay++;
                roundScore += c.getCardScore();
                return true;
            }
        }
        else if(playerTurn == 1){
            if(isPlayable(c) == true) {
                p2Hand.remove(c);
                p2Hand.trimToSize();
                inPlayCards.add(c);
                inPlay++;
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

    public boolean returnCards()
    {
        for(Card c : inPlayCards)
        {
            if(c.getPlayerID() == 0)
            {
                p1Hand.add(c);
            }
            else if(c.getPlayerID() == 1)
            {
                p2Hand.add(c);
            }
            else
            {
                return false;
            }
        }
        inPlayCards.clear();
        return true;
    }

    //can be turn or change turn, not sure if completely needed
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
    public ArrayList<Card> getHand(int pID){
        ArrayList<Card> hand;
        if(pID == 0){
            hand = p1Hand;
        }
        else{
            hand = p2Hand;
        }
        return hand;
    }
    public int getHandSize(int playerId){
        int size = 0;
        if(playerId == 0){size = p1Hand.size();}
        if(playerId == 1){size = p2Hand.size();}
        return size;
    }
    public int getRoundScore(int playerId){return roundScore;}
    public int getGameScore(int playerId){
        int out = 0;
        if(playerId == 0){out = p1Points;}
        if(playerId == 1){out = p2Points;}
        return out;
    }
    public void setRoundScore(int score) {roundScore = score;}
    public int getPlayer0Score() { return p1Points; }
    public int getPlayer1Score() { return p2Points; }
    public int getGamePhase() { return phase; }
    public boolean getDealer() { return isPlayer1Dealer; }
    public Card getLastPlayed(){return inPlayCards.get(inPlayCards.size()-1);}
    public Card getCribCard(int index){return crib.get(index);}
    public Card getFaceUpCard() {return faceUpCard;}
    public int getPlayerTurn() {return playerTurn;}
    public int getCribSize() {return crib.size();}
    public Card getInPlayCard(int index) {return inPlayCards.get(index);}
    public boolean getDealer(int pID){
        if(pID == 0 && isPlayer1Dealer){ // Player1 passed in & Player1 is Dealer  ---> TRUE
            return true;
        }
        else if(pID == 1 && !isPlayer1Dealer){ // Player2 passed in & Player2 is Dealer ---> TRUE
            return true;
        }
        else{
            return false; // Else ---> FALSE
        }
    }

    //IMPORTANT: method does not return card at index! It returns the card (index) back from the last card.
    public Card getPlayedCard(int index) { return inPlayCards.get(inPlayCards.size()-1-index); }
    public int getInPlaySize() { return inPlayCards.size(); }
    public int scoreRuns()
    {
        if(inPlayCards.size()<3) return 0;
        int arr[] = new int[13];
        //populates array with values from inPlayCards
        for(int i = 0; i < 3; i++)
        {
            //increment arr at index of the "ith card from last"'s card value (minus 1 to shift values from 1-13 to 0-12)
            arr[inPlayCards.get(inPlayCards.size()-1-i).getCardValue()-1]++;
        }

        int runCount = 0;
        int tal = 0;
        //checks for run of 3
        for(int i = 0; i < 13; i++)
        {
            if(arr[i] > 0) runCount++;
            if(runCount == 3) tal += scoreRunsRecur(arr, 3); //checks for run of 4 if necessary
        }

        return tal;
    }
    public int scoreRunsRecur(int[] arr, int count)
    {
        if(inPlayCards.size()<=count) return count;
        //increment arr at index of the "count'th card from last"'s card value (minus 1 to shift values from 1-13 to 0-12)
        arr[inPlayCards.get(inPlayCards.size()-1-count).getCardValue()-1]++;

        int runCount = 0;
        for(int i = 0; i < 13; i++)
        {
            if(arr[i] > 0) runCount++;
            if(runCount == count+1) return scoreRunsRecur(arr, count+1);
        }

        return count;
    }
    public int scoreDoubles()
    {
        //continue to check for consec doubles until no more cards remain
        for(int i = 0; i<(inPlayCards.size()-1); i++)
        {
            if(inPlayCards.get(inPlayCards.size()-1-i).getCardValue() != inPlayCards.get(inPlayCards.size()-1-(i+1)).getCardValue())
            {
                //first non pair found ends loop (i=0 means no pairs, i=1 means pair, i=2 means triple,
                //i=3 means quad)
                return i * (i+1);
            }
        }
        //runs if loop ends before finding a non pair (i.e. either size is 1 with no pairs (1*0=0), 2 with
        //pair (2*1=2), 3 with triple (3*2=6), or 4 with quad (4*3=12))
        return inPlayCards.size() * (inPlayCards.size()-1);
    }
    public int tallyRuns(ArrayList<Card> hand)
    {
        //add all card values to an array
        int arr[] = new int[13];
        for(Card c : hand) {
            arr[c.getCardValue()-1]++;
        }

        arr[faceUpCard.getCardValue()-1]++;

        //int sum = 0;
        for (int i = 4; i < 13; i++) {
            //check for a run of 5
            if (arr[i] >= 1 && arr[i-1] >= 1 && arr[i-2] >= 1 && arr[i-3] >= 1 && arr[i-4] >= 1) {
                return 5;
            }
        }

        for (int j = 3; j < 13; j++) {
            //check for a run of 4
            if (arr[j] >= 1 && arr[j-1] >= 1 && arr[j-2] >= 1 && arr[j-3] >= 1) {
                //if there are 2 identical cards, count 2 separate runs of 4
                if (arr[j] >= 2 || arr[j-1] >= 2 || arr[j-2] >= 2 || arr[j-3] >= 2) {
                    return 4+4;
                }
                return 4;
            }
        }

        for (int k = 2; k < 13; k++) {
            //check for a run of 3
            if (arr[k] >= 1 && arr[k-1] >= 1 && arr[k-2] >= 1) {
                //if there are 3 identical cards of a value, count 3 separate runs of 3
                if (arr[k] >= 3 || arr[k-1] >= 3 || arr[k-2] >= 3) {
                    return 3+3+3;
                }
                //if there are 2 identical cards of a value, count 2 separate runs of 3
                else if (arr[k] >= 2 || arr[k-1] >= 2 || arr[k-2] >= 2) {
                    return 3+3;
                }
                return 3;
            }
        }

        //4, 6, 7, 7, 8 - ideally should count 2 runs (of length 4)
        /*
         * arr[3] = 1
         * arr[5] = 1
         * arr[6] = 2
         * arr[7] = 1
         */

        //1, 2, 2, 2, 3 - ideally should count 3 runs (of length 3)
        /*
         * arr[0] = 1
         * arr[1] = 3
         * arr[2] = 1
         */

        //3, 4, 4, 5, 6 - ideally should count 2 runs (of length 4)
        /*
         * arr[2] = 1
         * arr[3] = 2
         * arr[4] = 1
         * arr[5] = 1
         */

        //1, 2, 3, 5, 6 - no matter what, there can only be 1 valid run per hand if theres no duplicates

        //1, 2, 3, 4, 7 - should only count 1 run (of length 4), cannot split into 2 runs

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
    //https://stackoverflow.com/questions/4632322/finding-all-possible-combinations-of-numbers-to-reach-a-given-sum
    public int tally15s(ArrayList<Card> hand) {
        tally = 0;
        int target = 15;
        ArrayList<Integer> partial = new ArrayList<Integer>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i++){
            numbers.add(hand.get(i).getCardValue());
        }
        tally15Recur(numbers,target,partial);
        return tally;
    }
    public void tally15Recur(ArrayList<Integer> N, int target, ArrayList<Integer> partial){
        int sum = 0;
        for (int x:partial){sum += x;}
        if(sum == target){tally += 1;}
        if(sum > target){return;}
        for(int i = 0; i < N.size(); i++){
            ArrayList<Integer> remaining = new ArrayList<Integer>();
            int n = N.get(i);
            for(int j = i+1; j < N.size(); j++){remaining.add(N.get(j));}

            ArrayList<Integer> partialrec = new ArrayList<Integer>(partial);
            partialrec.add(n);
            tally15Recur(remaining, target, partialrec);
        }
    }

    public int tallyFlush(ArrayList<Card> hand)
    {
        return 0;
    }
    public int tallyHeels(ArrayList<Card> hand)
    {
        return 0;
    }

    /* TO DO
        - ScoreRuns for during play
            - must be sequential? loop through inPlayCards?
        - ScoreDoubles for during play
            - if card played is the same rank as the previous card played, score 2 points
            - if card played is the same rank as the previous 2 cards played, score 6 points
            - if card played is the same rank as the previous 3 cards played, score 12 points
        - Score15s for during play
            - if card played makes the current running total 15, score 2 points
            - if card played makes the current running total 31, score 2 points
        - tallyFlush (only at end of play)
            - loop through hand and check if all cards are the same suit, score 4 points
        - tallyHeels (otherwise known as nob)
            - if hand/crib contains a jack of the same suit as the faceUpCard, score 1 point
     */

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

        String p1Vals = "Player 0 Points: " + String.valueOf(p1Points) +
                " Player 0 Hand: " + String.valueOf(stringHands(p1Hand)) + " Player 0 Round Score: " + p1RoundScore;
        String p2Vals = " Player 1 Points: " + String.valueOf(p2Points) +
                " Player 1 Hand: " +  String.valueOf(stringHands(p2Hand)) + " Player 1 Round Score: " + p2RoundScore +
                " Round total score: " + roundScore + " Cards in play: " + String.valueOf(inPlay);

        return p1Vals + p2Vals;
    }
}