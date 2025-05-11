package com.example.mazegame;

import com.example.mazegame.factory.MazeElement;
import com.example.mazegame.factory.Path;
import com.example.mazegame.factory.Wall;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private List<MazeElement> elements = new ArrayList<>();

    public Maze() {
    }

    public void generateMaze() {
        // Генерация простого лабиринта
        elements.add(new Wall());
        elements.add(new Path());
        elements.add(new Wall());
        elements.add(new Path());

        for (MazeElement element : elements) {
            element.draw(null);
        }
    }
    public void drawMaze(Graphics g) {
        for (MazeElement element : elements) {
            element.draw(g);  // Отрисовываем каждый элемент
        }
    }
}

