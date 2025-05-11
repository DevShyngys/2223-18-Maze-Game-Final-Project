package com.example.mazegame.factory;

public class MazeFactory {
    public static MazeElement createElement(String type) {
        if (type.equals("wall")) {
            return new Wall(); // Создание стены
        } else if (type.equals("path")) {
            return new Path(); // Создание пути
        }
        throw new IllegalArgumentException("Неизвестный тип элемента");
    }
}

