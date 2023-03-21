package com.example.cribbagegame.CribGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cribbagegame.GameFramework.GameMainActivity;
import com.example.cribbagegame.GameFramework.LocalGame;
import com.example.cribbagegame.GameFramework.gameConfiguration.GameConfig;
import com.example.cribbagegame.GameFramework.infoMessage.GameState;
import com.example.cribbagegame.R;

public class CribbageMainActivity extends GameMainActivity {

    private static final String TAG = "CribbageMainActivity";
    public static final int PORT_NUMBER = 1;

    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return null;
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