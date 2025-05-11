package com.example.mazegame.factory;
import java.awt.Graphics;
import java.awt.Color;
public class Path extends MazeElement {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 50, 50);
    }
}
