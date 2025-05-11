package com.example.mazegame.strategy;

import com.example.mazegame.strategy.MovementStrategy;

public class RandomMovementStrategy implements MovementStrategy {
    @Override
    public void move() {
        System.out.println("Перемещаюсь случайным образом.");
    }
}
