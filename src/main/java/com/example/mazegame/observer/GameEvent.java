package com.example.mazegame.observer;

import java.util.ArrayList;
import java.util.List;

public class GameEvent {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
