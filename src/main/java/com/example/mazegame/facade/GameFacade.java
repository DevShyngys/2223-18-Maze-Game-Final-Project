package com.example.mazegame.facade;

import com.example.mazegame.MazeGame;
import com.example.mazegame.command.GameController;
import com.example.mazegame.command.MoveCommand;
import com.example.mazegame.strategy.Character;
import com.example.mazegame.strategy.RandomMovementStrategy;

public class GameFacade {
    private MazeGame game;
    private GameController controller;

    public GameFacade() {
        this.game = new MazeGame();
        this.controller = new GameController();
    }

    public void startGame() {
        game.start();
        controller.setCommand(new MoveCommand(new Character(new RandomMovementStrategy())));
    }

    public static void main(String[] args) {
        GameFacade facade = new GameFacade();
        facade.startGame();
    }
}
