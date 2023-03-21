package com.example.cribbagegame.CribGame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cribbagegame.GameFramework.GameMainActivity;

import com.example.cribbagegame.GameFramework.LocalGame;
import com.example.cribbagegame.GameFramework.gameConfiguration.GameConfig;
import com.example.cribbagegame.GameFramework.infoMessage.GameState;

public class CribbageMainActivity extends GameMainActivity {

    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return null;
    }
}