package com.example.mazegame.composite;
import com.example.mazegame.factory.MazeElement;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
public class MazeComposite extends MazeElement {
    private List<MazeElement> components = new ArrayList<>();
    public void add(MazeElement component) {
        components.add(component);
    }

    @Override
    public void draw(Graphics g) {
        for (MazeElement component : components) {
            component.draw(g);
        }
    }
}
