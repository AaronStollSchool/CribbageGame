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

/*
 * Current status of the game:
 * Very buggy, all scoring methods weren't implemented, and CribSmartComputerPlayer is only
 * partially implemented.
 *
 * Most of us were busy with other things during easter weekend, so that left us really only Tuesday
 * and Wednesday to make most of the changes needed for the beta release. This lead to us doing a lot of
 * major changes that lead to a lot of bugs with not much time to resolve them. This could have probably
 * been prevented with better planning and scheduling.
 *
 * Theres issues with the computer player sometimes freezing up, the game flashing red and staying red,
 * the computer player trying to infinitely switch dealers at the end of play, among other issues.
 * Most of these likely have to do with the player turn being switched unnecessarily at some point,
 * or the go action.
 *
 * Scoring wasn't finished cause of a lack of time. Mostly a fault on our end.
 * CribSmartComputerPlayer couldn't be finished without the scoring methods.
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
        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CribComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new CribSmartComputerPlayer(name);
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