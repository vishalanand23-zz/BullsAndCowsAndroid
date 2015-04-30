package com.games.vishalanand23.bullsandcowsandroid;

public class BullsAndCows {

    int bulls;
    int cows;

    public BullsAndCows(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }

    @Override
    public String toString() {
        return "BullsAndCows{" + "bulls=" + bulls + ", cows=" + cows + "}";
    }

    public static BullsAndCows calculate(int numberOfDigits, long first, long second) {
        String firstString = pad(numberOfDigits, first);
        String secondString = pad(numberOfDigits, second);
        return new BullsAndCows(
                numberOfBulls(numberOfDigits, firstString, secondString),
                numberOfCows(numberOfDigits, firstString, secondString));
    }

    private static String pad(int numberOfDigits, long number) {
        String numberInString = ((Long) number).toString();
        if (numberInString.length() == numberOfDigits - 1) {
            return "0" + numberInString;
        } else {
            return numberInString;
        }
    }

    private static int numberOfCows(int numberOfDigits, String number, String guess) {
        int cows = 0;
        for (int i = 0; i < numberOfDigits; i++) {
            String a = guess.charAt(i) + "";
            if (number.contains(a)) {
                cows++;
            }
        }
        return cows - numberOfBulls(numberOfDigits, number, guess);
    }

    private static int numberOfBulls(int numberOfDigits, String number, String guess) {
        int bulls = 0;
        for (int i = 0; i < numberOfDigits; i++) {
            if (guess.charAt(i) == number.charAt(i)) bulls++;
        }
        return bulls;
    }

    public static void main(String args[]) {
        System.out.println(calculate(6, 123456L, 23765L));
    }
}
