package com.example.mazegame.strategy;

import com.example.mazegame.MazeGame;

public class Character {
    private MovementStrategy movementStrategy;

    public Character(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public void move(MazeGame game, int x, int y) {
        movementStrategy.move(game, x, y);

    }
}
