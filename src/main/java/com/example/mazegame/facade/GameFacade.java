package com.example.mazegame.facade;

import com.example.mazegame.strategy.DeterministicMovementStrategy;
import com.example.mazegame.MazeGame;
import com.example.mazegame.strategy.MovementStrategy;

public class GameFacade {
    private MazeGame game;
    private MovementStrategy moveStrategy;

    public GameFacade(MazeGame game) {
        this.game = game;
        this.moveStrategy = new DeterministicMovementStrategy();
    }

    public void startGame() {
        game.start();
    }

    public void movePlayer(int x, int y) {
        moveStrategy.move(game, x, y);
    }
}
