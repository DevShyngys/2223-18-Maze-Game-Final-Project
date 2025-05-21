package com.example.mazegame.facade;

import com.example.mazegame.strategy.DeterministicMovementStrategy;
import com.example.mazegame.MazeGame;
import com.example.mazegame.strategy.MovementStrategy;

public class GameFacade {
    private static MazeGame game;

    public GameFacade(MazeGame game) {
        this.game = game;
    }

    public static void startGame(MazeGame game) {
        game.start();
    }
}
