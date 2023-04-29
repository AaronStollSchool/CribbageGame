package com.example.cribbagegame.CribGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cribbagegame.GameFramework.GameMainActivity;
import com.example.cribbagegame.GameFramework.LocalGame;
import com.example.cribbagegame.GameFramework.gameConfiguration.GameConfig;
import com.example.cribbagegame.GameFramework.gameConfiguration.GamePlayerType;
import com.example.cribbagegame.GameFramework.infoMessage.GameState;
import com.example.cribbagegame.GameFramework.players.GamePlayer;
import com.example.cribbagegame.GameFramework.utilities.Logger;
import com.example.cribbagegame.GameFramework.utilities.Saving;
import com.example.cribbagegame.R;

import java.util.ArrayList;

/**
 * @authors Aaron Stoll, Aether Mocker, Kincaid Larson, Sean Murray
 * @version March 2023
 */
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
            public GamePlayer createPlayer(String name) { return new CribComputerPlayer(name); }});
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CribSmartComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Alt Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CribAltComputerPlayer(name);
            }});

        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Cribbage", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); //Player 1: a human player
        defaultConfig.addPlayer("Computer", 1); //Player 2: a computer player
        defaultConfig.setRemoteData("Remote Human Player", "", 0);

        return defaultConfig;
    }//createDefaultConfig

    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     * @param gameState
     * 				the gameState for this game or null for a new game
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     */
    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new CribLocalGame();
    }

    /**
     * saveGame, adds this games prepend to the filename
     *
     * @param gameName
     * 				Desired save name
     * @return String representation of the save
     */
    @Override
    public GameState saveGame(String gameName){
        return super.saveGame(getGameString(gameName));
    }

    /**
     * loadGame, adds this games prepend to the desire file to open and creates the game specific state
     * @param gameName
     * 				The file to open
     * @return The loaded GameState
     */
    @Override
    public GameState loadGame(String gameName){
        String appName = getGameString(gameName);
        super.loadGame(appName);
        Logger.log(TAG, "Loading: " + gameName);
        return (GameState) new CribbageGameState((CribbageGameState) Saving.readFromFile(appName, this.getApplicationContext()));
    }
}