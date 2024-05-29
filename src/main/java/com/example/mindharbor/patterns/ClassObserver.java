package com.example.mindharbor.patterns;

import com.example.mindharbor.Enum.UserType;

import java.util.ArrayList;
import java.util.List;

public class ClassObserver {
    private static List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(UserType userType) {
        for (Observer observer : observers) {
            observer.updateUserStatus(userType);
        }
    }
    public void notifyObserversTest() {
        for (Observer observer : observers) {
            observer.notifyNewTest();
        }
    }
}
