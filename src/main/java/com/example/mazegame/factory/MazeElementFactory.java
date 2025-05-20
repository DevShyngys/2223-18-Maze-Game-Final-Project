package com.example.mazegame.factory;
import com.example.mazegame.factory.MazeElement;
import com.example.mazegame.factory.Wall;
import com.example.mazegame.factory.Path;

public class MazeElementFactory {
    public static MazeElement createWall() {
        return new Wall();
    }

    public static MazeElement createPath() {
        return new Path();
    }}
