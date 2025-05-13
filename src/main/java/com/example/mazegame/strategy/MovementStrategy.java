package com.example.mazegame.strategy;
import com.example.mazegame.MazeGame;

public interface MovementStrategy {
    void move(MazeGame game, int x, int y);
}