package com.example.mazegame.state;

import com.example.mazegame.state.GameState;
import com.example.mazegame.MazeGame;

public class PlayingState implements GameState {
    @Override
    public void handle(MazeGame game) {
        System.out.println("Игрок играет...");
        game.setState(new GameOverState());
    }
}
