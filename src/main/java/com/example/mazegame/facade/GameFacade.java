package com.example.mazegame.facade;

import com.example.mazegame.MazeGame;

public class GameFacade {
    public static void startGame(MazeGame game) {
        game.generateMaze();
        game.createAndShowGUI();
    }
}
