package com.games.vishalanand23.bullsandcowsandroid.data;

public class PlayResult {
    String deviceId;
    int numberOfDigits;
    String playingNumber;
    int numberOfGuesses;
    int winGame;
    int timeInMillis;

    public String getDeviceId() {
        return deviceId;
    }

    public int getNumberOfDigits() {
        return numberOfDigits;
    }

    public String getPlayingNumber() {
        return playingNumber;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public int getWinGame() {
        return winGame;
    }

    public int getTimeInMillis() {
        return timeInMillis;
    }

    public PlayResult(
            String deviceId,
            int numberOfDigits,
            String playingNumber,
            int numberOfGuesses,
            int winGame,
            int timeInMillis) {
        if (playingNumber.length() != numberOfDigits) {
            throw new RuntimeException("Length of playing number must be equal to number of digits.");
        }
        this.deviceId = deviceId;
        this.numberOfDigits = numberOfDigits;
        this.playingNumber = playingNumber;
        this.numberOfGuesses = numberOfGuesses;
        this.winGame = winGame;
        this.timeInMillis = timeInMillis;
    }
}
