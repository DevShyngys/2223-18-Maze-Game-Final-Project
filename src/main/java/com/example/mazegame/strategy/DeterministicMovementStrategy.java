package com.example.mazegame.strategy;
import com.example.mazegame.MazeGame;
import com.example.mazegame.factory.Wall;

public class DeterministicMovementStrategy implements MovementStrategy {
    @Override
    public void move(MazeGame game, int x, int y) {
        if (game.playerX + x >= 0 && game.playerX + x < MazeGame.cols && game.playerY + y >= 0 && game.playerY + y < MazeGame.rows) {
            if (game.maze[game.playerY + y][game.playerX + x] == null || game.maze[game.playerY + y][game.playerX + x] instanceof Wall) {
                return;
            }

            // Двигаем игрока
            game.playerX += x;
            game.playerY += y;
        }
    }
}
