package com.example.mazegame;

import com.example.mazegame.command.GameController;
import com.example.mazegame.state.GameState;
import com.example.mazegame.state.PlayingState;

public class MazeGame {
    private GameState state;
    private Maze maze;
    private GameController controller;

    public MazeGame() {
        this.state = new PlayingState(); // Начальное состояние игры
        this.maze = new Maze(); // Создание лабиринта
        this.controller = new GameController(); // Контроллер игры
    }

    public void start() {
        System.out.println("Игра началась!");
        state.handle(this); // Обработка текущего состояния игры
        maze.generateMaze(); // Генерация лабиринта
    }

    public void setState(GameState state) {
        this.state = state; // Установка нового состояния игры
    }

    public void endGame() {
        System.out.println("Игра окончена!");
    }

    public static void main(String[] args) {
        MazeGame game = new MazeGame();
        game.start(); // Запуск игры
    }
}

