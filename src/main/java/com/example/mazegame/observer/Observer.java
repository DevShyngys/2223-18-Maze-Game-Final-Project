package com.example.mazegame.observer;

public interface Observer {
    void onGameWon();   // Обработчик победы
    void onTimeUp();
    void update();
}
