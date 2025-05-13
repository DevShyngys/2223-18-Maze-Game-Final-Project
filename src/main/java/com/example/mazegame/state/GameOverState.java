package com.example.mazegame.state;

import com.example.mazegame.MazeGame;
import com.example.mazegame.state.GameState;

public class GameOverState implements GameState {
    @Override
    public void handle(MazeGame game) {
        game.endGame();
    }
}
