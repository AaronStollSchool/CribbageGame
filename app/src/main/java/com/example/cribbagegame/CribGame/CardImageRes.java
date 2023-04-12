package com.example.cribbagegame.CribGame;

import com.example.cribbagegame.R;

public class CardImageRes {
    private int[][] resources;
    // [suit] [value]

    public CardImageRes(){
        resources[1][1] = R.drawable.ace_of_diamonds;
        resources[1][2] = R.drawable._2_of_diamonds;
        resources[1][3] = R.drawable._3_of_diamonds;
        resources[1][4] = R.drawable._4_of_diamonds;
        resources[1][5] = R.drawable._5_of_diamonds;
        resources[1][6] = R.drawable._6_of_diamonds;
        resources[1][7] = R.drawable._7_of_diamonds;
        resources[1][8] = R.drawable._8_of_diamonds;
        resources[1][9] = R.drawable._9_of_diamonds;
        resources[1][10] = R.drawable._10_of_diamonds;
        resources[1][11] = R.drawable.jack_of_diamonds;
        resources[1][12] = R.drawable.queen_of_diamonds;
        resources[1][13] = R.drawable.king_of_diamonds;

        resources[2][1] = R.drawable.ace_of_hearts;
        resources[2][2] = R.drawable._2_of_hearts;
        resources[2][3] = R.drawable._3_of_hearts;
        resources[2][4] = R.drawable._4_of_hearts;
        resources[2][5] = R.drawable._5_of_hearts;
        resources[2][6] = R.drawable._6_of_hearts;
        resources[2][7] = R.drawable._7_of_hearts;
        resources[2][8] = R.drawable._8_of_hearts;
        resources[2][9] = R.drawable._9_of_hearts;
        resources[2][10] = R.drawable._10_of_hearts;
        resources[2][11] = R.drawable.jack_of_hearts;
        resources[2][12] = R.drawable.queen_of_hearts;
        resources[2][13] = R.drawable.king_of_hearts;

        resources[3][1] = R.drawable.ace_of_spades;
        resources[3][2] = R.drawable._2_of_spades;
        resources[3][3] = R.drawable._3_of_spades;
        resources[3][4] = R.drawable._4_of_spades;
        resources[3][5] = R.drawable._5_of_spades;
        resources[3][6] = R.drawable._6_of_spades;
        resources[3][7] = R.drawable._7_of_spades;
        resources[3][8] = R.drawable._8_of_spades;
        resources[3][9] = R.drawable._9_of_spades;
        resources[3][10] = R.drawable._10_of_spades;
        resources[3][11] = R.drawable.jack_of_spades;
        resources[3][12] = R.drawable.queen_of_spades;
        resources[3][13] = R.drawable.king_of_spades;

        resources[4][1] = R.drawable.ace_of_clubs;
        resources[4][2] = R.drawable._2_of_clubs;
        resources[4][3] = R.drawable._3_of_clubs;
        resources[4][4] = R.drawable._4_of_clubs;
        resources[4][5] = R.drawable._5_of_clubs;
        resources[4][6] = R.drawable._6_of_clubs;
        resources[4][7] = R.drawable._7_of_clubs;
        resources[4][8] = R.drawable._8_of_clubs;
        resources[4][9] = R.drawable._9_of_clubs;
        resources[4][10] = R.drawable._10_of_clubs;
        resources[4][11] = R.drawable.jack_of_clubs;
        resources[4][12] = R.drawable.queen_of_clubs;
        resources[4][13] = R.drawable.king_of_clubs;
    }

    public int getCardResID(int suit, int value){
        return resources[suit][value];
    }
}
