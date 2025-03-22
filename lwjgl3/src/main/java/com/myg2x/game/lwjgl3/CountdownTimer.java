package com.myg2x.game.lwjgl3;
import java.util.ArrayList;
import java.util.List;



public class CountdownTimer {
    // CountdownTimer class that counts down from a given time and notifies observers when the timer finishes
    // Observers in this case will be the game objects that need to be updated when the timer finishes
    private float remainingTime; // Remaining time in seconds
    private boolean isRunning; // Is the timer running

    private final List<TimerObserver> observers = new ArrayList<>(); // List of observers


    // Initialize the timer with the given time
    public CountdownTimer(float time) {
        this.remainingTime = time;
        this.isRunning = false;
    }


    // Add an observer to the list of observers
    public void addObserver(TimerObserver observer) {
        observers.add(observer);
    }


    // Remove an observer from the list of observers
    public void removeObserver(TimerObserver observer) {
        observers.remove(observer);
    }



    // Start the timer
    public void start() {
        isRunning = true;
    }


    // Stop the timer
    public void stop() {
        isRunning = false;
    }

    // Reset the timer
    public void reset(float time) {
        this.remainingTime = time;
        isRunning = false;
    }

    // Update the timer
    public void update(float deltaTime) {
        if (isRunning) {
            remainingTime -= deltaTime;
            if (remainingTime <= 0) {
                remainingTime = 0;
                isRunning = false;
                notifyObserversFinish();
            } else {
                notifyObserversUpdate();
            }
        }
    }

    // Format time as MM:SS
    public String getFormattedTime() {
        int totalSeconds = (int) remainingTime;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    // Get the remaining time in seconds
    public int getRemainingTime() {
        return (int) remainingTime;
    }


    // Notify all observers that the timer has finished
    private void notifyObserversUpdate() {
        for (TimerObserver observer : observers) {
            observer.onTimerUpdate(getRemainingTime());
        }
    }


    // Notify all observers that the timer has finished
    private void notifyObserversFinish() {
        for (TimerObserver observer : observers) {
            observer.onTimerFinish();
        }
    }



}
