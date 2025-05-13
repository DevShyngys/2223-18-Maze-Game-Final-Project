package com.example.mazegame.command;

import com.example.mazegame.MazeGame;
import com.example.mazegame.factory.Path;

public class MoveLeftCommand implements Command {
    @Override
    public void execute() {
        if (MazeGame.maze[MazeGame.playerY - 1][MazeGame.playerX] instanceof Path) {
            MazeGame.playerX--;
        }
    }
}