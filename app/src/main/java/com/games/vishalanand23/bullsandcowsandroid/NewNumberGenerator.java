package com.games.vishalanand23.bullsandcowsandroid;

import java.util.Random;

public class NewNumberGenerator {

    public String generate(int numberOfDigits) {
        Random random = new Random();
        int numberArray[] = new int[numberOfDigits];
        int i = 0;
        while (i < numberOfDigits) {
            boolean isRepeated = false;
            int temp = random.nextInt(10);
            for (int j = 0; j < i; j++) {
                if (temp == numberArray[j]) {
                    isRepeated = true;
                    break;
                }
            }
            if (!isRepeated) {
                numberArray[i] = temp;
                i++;
            }
        }
        long number = 0;
        for (int k = 0; k < numberOfDigits; k++) {
            number += ((Double) Math.pow(10, numberOfDigits - k - 1)).longValue() * numberArray[k];
        }
        String toString = ((Long) number).toString();
        return toString.length() == numberOfDigits ? toString : "0" + toString;
    }

    public static void main(String args[]) {
        System.out.println(new NewNumberGenerator().generate(10));
    }
}
