package com.example.mazegame.state;

import com.example.mazegame.MazeGame;
public interface GameState {
    void handle(MazeGame game);
}