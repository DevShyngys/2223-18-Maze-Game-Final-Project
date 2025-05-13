package com.example.mazegame.command;

import com.example.mazegame.MazeGame;
import com.example.mazegame.strategy.Character;

public class MoveCommand implements Command {
    private com.example.mazegame.strategy.Character character;

    public MoveCommand(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        character.move(new MazeGame(), 1, 0);
    }
}

