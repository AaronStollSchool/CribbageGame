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
    private int inPlayScoreIndex = 0;
    private Card faceUpCard;

    private int playerTurn;
    //player 1 turn: 0
    //player 2 turn: 1

    private int playerSaidGoFirst = -1; //for GoAction -- starts -1 bc no one said go yet.

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

        faceUpCard = new Card(0,0,0);

        playerTurn = 0; // TBI: change value to show no player turn has been set yet
        isPlayer1Dealer = false;
        randomizeDealer();

        phase = 0;
        p1RoundScore = 0;
        p2RoundScore = 0;
        roundScore = 0;

        gen = new Random();
    }

    /**
     * Copy constructor for class CribbageGameState
     *
     * @param gameState
     * 		the CribbageGameState object that we want to clone
     */
    public CribbageGameState(CribbageGameState gameState) {
        this.p1Points = gameState.p1Points;
        this.p2Points = gameState.p2Points;

        this.p1Hand = new ArrayList<Card>();
        this.p1Hand.addAll(gameState.p1Hand);
        this.p2Hand = new ArrayList<Card>();
        this.p2Hand.addAll(gameState.p2Hand);

        this.inPlayCards = new ArrayList<Card>();
        this.inPlayCards.addAll(gameState.inPlayCards);
        this.crib = new ArrayList<Card>();
        this.crib.addAll(gameState.crib);

        this.faceUpCard = new Card(gameState.faceUpCard.getCardValue(), gameState.faceUpCard.getSuit(), gameState.faceUpCard.getCardID());

        this.playerTurn = gameState.playerTurn;

        this.isPlayer1Dealer = gameState.isPlayer1Dealer;

        //need to set up GamePhase
        this.phase = gameState.phase;

        this.p1RoundScore = gameState.p1RoundScore;
        this.p2RoundScore = gameState.p2RoundScore;
        this.roundScore = gameState.roundScore;

        this.gen = gameState.gen;
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
        Log.d("FaceUpCard", "" + faceUpCard.toString());
        if(getHandSize(1) == 6 && getHandSize(0) == 6) {
            return true;
        } else {
            return false;
        }
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

    public boolean isFaceUpCardJack() {
        if(faceUpCard.getCardValue() == 11) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean awardFaceUpPoints() {
        //points awarded to non-dealer
        if (isPlayer1Dealer) {
            p1Points += 2;
        }
        else {
            p2Points += 2;
        }
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
            return true;
        }
        else if (isPlayer1Dealer == false) {
            isPlayer1Dealer = true;
            return true;
        }
        else {
            return false;
        }
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

    //is 'playerID' dealer
    public boolean isDealer(int playerID) {
        if(playerID == 0 && !isPlayer1Dealer) {
            return true;
        }
        else if (playerID == 1 && !isPlayer1Dealer) {
            return false;
        }
        else if (playerID == 0 && isPlayer1Dealer) {
            return false;
        }
        else if (playerID == 1 && isPlayer1Dealer) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean randomizeDealer() {
        Random r = new Random();
        int dealer = r.nextInt(2);
        if (dealer == 0) {
            isPlayer1Dealer = false;
            playerTurn = 0;
            return true;
        }
        else if (dealer == 1) {
            isPlayer1Dealer = true;
            playerTurn = 1;
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

    //NOTICE!!! whoever calls go is the one who gets the points
    public boolean go(int playerID)
    {
        if(playerID == 0) {
            if(roundScore == 31)
            {
                p1Points += 2;
            }
            else
            {
                p1Points++;
            }
            roundScore = 0;
            inPlayScoreIndex = inPlayCards.size();
            return true;
        }
        else if(playerID == 1){
            if(roundScore == 31)
            {
                p2Points += 2;
            }
            else
            {
                p2Points++;
            }
            roundScore = 0;
            inPlayScoreIndex = inPlayCards.size();
            return true;
        }
        return false;
    }

    public boolean hasAnyPlayableCard(int playerID) {
        boolean hasPlayable = false;
        if(playerID == 0) {
            for(int i = 0; i < p1Hand.size(); i++) {
                if(isPlayable(p1Hand.get(i))) {
                    hasPlayable = true;
                }
            }
        }
        if(playerID == 1) {
            for(int i = 0; i < p2Hand.size(); i++) {
                if(isPlayable(p2Hand.get(i))) {
                    hasPlayable = true;
                }
            }
        }
        return hasPlayable;
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

        //patch fix for pc hand populating with 7 cards
        while(p1Hand.size() > 6) p1Hand.remove(p1Hand.size() - 1);
        while(p2Hand.size() > 6) p2Hand.remove(p2Hand.size() - 1);
        return true;
    }

    public boolean discard(Card c, int playerID) { //discard TO CRIB
        if(playerTurn == 0) {
            if(isPlayable(c)) {
                crib.add(c);
                p1Hand.remove(c);
                p1Hand.trimToSize();
                return true;
            }
        }
        else if(playerTurn == 1){
            if(isPlayable(c)) {
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

        for(int i = 0; i < c.size(); i++) {
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
    public void setPlayerSaidGoFirst(int playerNum) { this.playerSaidGoFirst = playerNum; }
    public Card getLastPlayedCard() {return inPlayCards.get(inPlayCards.size() -1); }
    public int getPlayerSaidGoFirst() { return playerSaidGoFirst; }
    public int getPlayer0Score() { return p1Points; }
    public int getPlayer1Score() { return p2Points; }
    public int getGamePhase() { return phase; }
    public boolean getIsPlayer1Dealer() { return isPlayer1Dealer; }
    public Card getCribCard(int index){return crib.get(index);}
    public Card getFaceUpCard() {return faceUpCard;}
    public int getPlayerTurn() {return playerTurn;}
    public int getCribSize() {return crib.size();}
    public Card getInPlayCard(int index) {return inPlayCards.get(index);}

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
            if(arr[i] > 0)
            {
                runCount++;
            }
            else
            {
                runCount = 0;
            }
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
            if(arr[i] > 0)
            {
                runCount++;
            }
            else
            {
                runCount = 0;
            }
            if(runCount == count+1) return scoreRunsRecur(arr, count+1);
        }

        return count;
    }

    public int scoreRunsNew()
    {
        if((inPlayCards.size()-inPlayScoreIndex) < 3) return 0;

        for(int i = inPlayCards.size()-inPlayScoreIndex; i > 2; i--)
        {
            int arr[] = new int[13];
            //populates array with values from inPlayCards
            for(int j = 0; j < i; j++)
            {
                //increment arr at index of the "ith card from last"'s card value (minus 1 to shift values from 1-13 to 0-12)
                arr[inPlayCards.get(inPlayCards.size()-1-j).getCardValue()-1]++;
            }

            int runCount = 0;
            for(int j = 0; j < 13; j++)
            {
                if(arr[j] == 1)
                {
                    runCount++;
                    if(runCount == i)
                    {
                        Log.d("Run Score:",Integer.toString(i));
                        return i;
                    }
                }
                else if(arr[j] > 1)
                {
                    break;
                }
                else
                {
                    if(runCount > 0) break;
                }
            }
        }
        Log.d("Run Score:",Integer.toString(0));
        return 0;
    }
    public int scoreDoubles()
    {
        //continue to check for consec doubles until no more cards remain
        for(int i = 0; i<(inPlayCards.size()-1-inPlayScoreIndex); i++)
        {
            if(inPlayCards.get(inPlayCards.size()-1-i).getCardValue() != inPlayCards.get(inPlayCards.size()-1-(i+1)).getCardValue())
            {
                //first non pair found ends loop (i=0 means no pairs, i=1 means pair, i=2 means triple,
                //i=3 means quad)
                Log.d("Double Score:",Integer.toString((i*(i+1))));
                return i * (i+1);
            }
        }
        //runs if loop ends before finding a non pair (i.e. either size is 1 with no pairs (1*0=0), 2 with
        //pair (2*1=2), 3 with triple (3*2=6), or 4 with quad (4*3=12))
        Log.d("Double Score:",Integer.toString((inPlayCards.size()-inPlayScoreIndex) * (inPlayCards.size()-1-inPlayScoreIndex)));
        return (inPlayCards.size()-inPlayScoreIndex) * (inPlayCards.size()-1-inPlayScoreIndex);
    }
    public int score15()
    {
        if(roundScore == 15) return 2;
        else return 0;
    }

    public void scorePoints(int pID)
    {
        int score = score15() + scoreDoubles() + scoreRunsNew();
        if (pID == 1) {
            Log.d("Total Score:", Integer.toString(score));
            p2Points += score;
        } else {
            Log.d("Total Score:", Integer.toString(score));
            p1Points += score;
        }
    }

    public int scorePredict(Card c)
    {
        int predict;
        inPlayCards.add(c);
        predict = scoreDoubles() + scoreRunsNew();
        if(roundScore + c.getCardScore() == 15) predict += 2;
        inPlayCards.remove(c);
        return predict;
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
                Log.d("Runs:", Integer.toString(5));
                return 5;
            }
        }

        for (int j = 3; j < 13; j++) {
            //check for a run of 4
            if (arr[j] >= 1 && arr[j-1] >= 1 && arr[j-2] >= 1 && arr[j-3] >= 1) {
                //if there are 2 identical cards, count 2 separate runs of 4
                if (arr[j] >= 2 || arr[j-1] >= 2 || arr[j-2] >= 2 || arr[j-3] >= 2) {
                    Log.d("Runs:", Integer.toString(8));
                    return 4+4;
                }
                Log.d("Runs:", Integer.toString(4));
                return 4;
            }
        }

        for (int k = 2; k < 13; k++) {
            //check for a run of 3
            if (arr[k] >= 1 && arr[k-1] >= 1 && arr[k-2] >= 1) {
                //if there are 3 identical cards of a value, count 3 separate runs of 3
                if (arr[k] >= 3 || arr[k-1] >= 3 || arr[k-2] >= 3) {
                    Log.d("Runs:", Integer.toString(9));
                    return 3+3+3;
                }
                //if there are 2 identical cards of a value, count 2 separate runs of 3
                else if (arr[k] >= 2 || arr[k-1] >= 2 || arr[k-2] >= 2) {
                    Log.d("Runs:", Integer.toString(6));
                    return 3+3;
                }
                Log.d("Runs:", Integer.toString(3));
                return 3;
            }
        }

        Log.d("Runs:", Integer.toString(0));
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
        arr[faceUpCard.getCardValue()-1]++;

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
        Log.d("Doubles:", Integer.toString(sum));
        return sum;
    }
    //https://stackoverflow.com/questions/4632322/finding-all-possible-combinations-of-numbers-to-reach-a-given-sum
    public int tally15s(ArrayList<Card> hand) {
        tally = 0;
        int target = 15;
        ArrayList<Integer> partial = new ArrayList<Integer>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i++){
            numbers.add(hand.get(i).getCardScore());
        }
        numbers.add(faceUpCard.getCardScore());
        tally15Recur(numbers,target,partial);
        Log.d("15s:", Integer.toString(tally));
        return tally;
    }
    public void tally15Recur(ArrayList<Integer> N, int target, ArrayList<Integer> partial){
        int sum = 0;
        for (int x:partial){sum += x;}
        if(sum == target){tally += 2;}
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


    public int tallyFlush(ArrayList<Card> hand, boolean isCrib)
    {
        for(int i = 0; i < 3; i++)
        {
            if(hand.get(i).getSuit() != hand.get(i+1).getSuit())
            {
                Log.d("Flush:", Integer.toString(0));
                return 0;
            }
        }

        if(hand.get(0).getSuit() != faceUpCard.getSuit())
        {
            if(isCrib)
            {
                Log.d("Flush:", Integer.toString(0));
                return 0;
            }
            else
            {
                Log.d("Flush:", Integer.toString(4));
                return 4;
            }
        }

        Log.d("Flush:", Integer.toString(5));
        return 5;
    }
    public int tallyHeels(ArrayList<Card> hand)
    {
        for(int i = 0; i < 4; i++)
        {
            if(hand.get(i).getCardValue() == 11 && hand.get(i).getSuit() == faceUpCard.getSuit())
            {
                Log.d("Heels:", Integer.toString(1));
                return 1;
            }
        }
        Log.d("Heels:", Integer.toString(0));
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
                    for(int j = 0; j < h.size(); j++)
                    {
                        Log.d("Player 1's Cards: ", h.get(j).toString());
                    }
                    Log.d("Face Up Card: ", faceUpCard.toString());

                    sum = tallyRuns(h) + tallyDoubles(h) + tally15s(h) + tallyFlush(h, false) + tallyHeels(h);
                    p1Points += sum;
                    break;
                case 1:
                    h = p2Hand;
                    for(int j = 0; j < h.size(); j++)
                    {
                        Log.d("Player 2's Cards: ", h.get(j).toString());
                    }
                    Log.d("Face Up Card: ", faceUpCard.toString());

                    sum = tallyRuns(h) + tallyDoubles(h) + tally15s(h) + tallyFlush(h, false) + tallyHeels(h);
                    p2Points += sum;
                    break;
                case 2:
                    h = crib;
                    for(int j = 0; j < h.size(); j++)
                    {
                        Log.d("Crib Cards: ", h.get(j).toString());
                    }
                    Log.d("Face Up Card: ", faceUpCard.toString());

                    sum = tallyRuns(h) + tallyDoubles(h) + tally15s(h) + tallyFlush(h, true) + tallyHeels(h);
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
            inPlay = 0;
            inPlayScoreIndex = 0;
        }
    }
}