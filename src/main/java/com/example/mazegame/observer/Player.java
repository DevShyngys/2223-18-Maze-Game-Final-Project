package com.example.mazegame.observer;

public class Player implements  Observer {
    @Override
    public void onGameWon() {
        System.out.println("YOU WIN!");
    }

    @Override
    public void onTimeUp() {
        System.out.println("Time up!");
    }
    @Override
    public void update() {
        System.out.println("Игрок переместился.");
    }
}
