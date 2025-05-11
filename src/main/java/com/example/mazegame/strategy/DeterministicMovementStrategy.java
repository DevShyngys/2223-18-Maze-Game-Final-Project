package com.example.mazegame.strategy;

public class DeterministicMovementStrategy implements MovementStrategy {
    @Override
    public void move() {
        System.out.println("Перемещаюсь детерминированно.");
    }
}
