package com.example.mazegame.singleton;

public class GameSettings {
    private static GameSettings instance;

    private GameSettings() { }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }
}
