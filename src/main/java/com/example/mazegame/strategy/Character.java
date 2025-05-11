package com.example.mazegame.strategy;

public class Character {
    private MovementStrategy movementStrategy;

    public Character(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public void move() {
        movementStrategy.move();

    }
}
