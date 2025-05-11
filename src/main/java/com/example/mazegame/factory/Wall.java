package com.example.mazegame.factory;
import java.awt.Graphics;
import java.awt.Color;
public class Wall extends MazeElement {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 50, 50);
    }
}
