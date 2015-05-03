package com.games.vishalanand23.bullsandcowsandroid.data;

public class BullsAndCows {

    int bulls;
    int cows;

    public BullsAndCows(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }

    public int getBulls() {
        return bulls;
    }

    public int getCows() {
        return cows;
    }

    @Override
    public String toString() {
        return "BullsAndCows{" + "bulls=" + bulls + ", cows=" + cows + "}";
    }

    public static BullsAndCows calculate(String first, String second) {
        return new BullsAndCows(
                numberOfBulls(first, second),
                numberOfCows(first, second));
    }

    private static int numberOfCows(String number, String guess) {
        int cowsPlusBulls = 0;
        for (int i = 0; i < number.length(); i++) {
            String a = guess.charAt(i) + "";
            if (number.contains(a)) {
                cowsPlusBulls++;
            }
        }
        return cowsPlusBulls - numberOfBulls(number, guess);
    }

    private static int numberOfBulls(String number, String guess) {
        int bulls = 0;
        for (int i = 0; i < number.length(); i++) {
            if (guess.charAt(i) == number.charAt(i)) bulls++;
        }
        return bulls;
    }

    public boolean isGuessCorrect(int numberOfDigits) {
        return bulls == numberOfDigits;
    }

    public static void main(String args[]) {
        System.out.println(calculate("123456", "023657"));
    }
}
