package com.example.mazegame.observer;

public class Player implements  Observer {
    @Override
    public void update() {
        System.out.println("Игрок переместился.");
    }
}
