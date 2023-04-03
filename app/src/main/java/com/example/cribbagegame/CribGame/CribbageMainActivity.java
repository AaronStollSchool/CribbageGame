package com.example.cribbagegame.CribGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cribbagegame.GameFramework.GameMainActivity;
import com.example.cribbagegame.GameFramework.LocalGame;
import com.example.cribbagegame.GameFramework.gameConfiguration.GameConfig;
import com.example.cribbagegame.GameFramework.gameConfiguration.GamePlayerType;
import com.example.cribbagegame.GameFramework.infoMessage.GameState;
import com.example.cribbagegame.GameFramework.players.GamePlayer;
import com.example.cribbagegame.R;

import java.util.ArrayList;

public class CribbageMainActivity extends GameMainActivity {

    private static final String TAG = "CribbageMainActivity";
    public static final int PORT_NUMBER = 2279;

    @Override
    public GameConfig createDefaultConfig() {

        //define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        //2 player types: human and computer
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new CribHumanPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CribComputerPlayer(name);
            }});

        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Cribbage", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); //Player 1: a human player
        defaultConfig.addPlayer("Computer", 1); //Player 2: a computer player
        defaultConfig.setRemoteData("Remote Human Player", "", 0);

        return defaultConfig;
    }//createDefaultConfig

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new CribLocalGame();
    }

    @Override
    public GameState saveGame(String gameName){
        return null;
    }

    @Override
    public GameState loadGame(String gameName){
        return null;
    }
}