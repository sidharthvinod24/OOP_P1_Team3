// This is a Timer Observer class that observes the timer and updates the game state accordingly.

package com.myg2x.game.lwjgl3;

public interface TimerObserver {
    void onTimerUpdate(int remainingTime);
    void onTimerFinish();
}
