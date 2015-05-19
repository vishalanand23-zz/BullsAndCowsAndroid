package com.games.vishalanand23.bullsandcowsandroid.data;

import android.content.Context;
import android.os.SystemClock;
import android.widget.Chronometer;

public class GameData {
    private final int numberOfDigits;
    private Chronometer chronometer;
    private boolean winGame = false;
    private char[] currentValue;
    private long timeWhenStopped;
    private int numberOfRounds = 0;
    private boolean pausedGame = false;

    public GameData(Context context, int numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
        timeWhenStopped = 0;
        winGame = false;
        currentValue = new char[numberOfDigits];
        for (int i = 0; i < numberOfDigits; i++) {
            currentValue[i] = Character.forDigit(i + 1, 10);
        }
        chronometer = new Chronometer(context);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void pause() {
        pausedGame = true;
        timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
    }

    public void resume() {
        pausedGame = false;
        chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chronometer.start();
    }

    public void setChar(int i, char c) {
        currentValue[i] = c;
    }

    public void setWin() {
        numberOfRounds++;
        chronometer.stop();
        winGame = true;
    }

    public boolean isGameWon() {
        return winGame;
    }

    public boolean allCharactersDifferent() {
        for (int i = 0; i < numberOfDigits; i++) {
            for (int j = 0; j < numberOfDigits; j++) {
                if (i == j) {
                    continue;
                }
                if (currentValue[i] == currentValue[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getGameTime() {
        return (int) (SystemClock.elapsedRealtime() - chronometer.getBase());
    }

    public void roundOver() {
        numberOfRounds++;
    }

    public int numberOfRounds() {
        return numberOfRounds;
    }

    public boolean isGamePaused() {
        return pausedGame;
    }
}
