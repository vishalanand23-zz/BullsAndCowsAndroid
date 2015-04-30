package com.games.vishalanand23.bullsandcowsandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class DigitPickerFragment extends Fragment {
    private int numberOfDigits = 4;
    private char[] currentValue = new char[numberOfDigits];
    private String originalValue;
    private Chronometer chronometer;
    private int numberOfRounds = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_number_picker, container, false);
        initializeSubmitButton(layout);
        initializeNewGameButton(layout);
        initialize(layout);
        return layout;
    }

    private void initializeNewGameButton(final View layout) {
        Button newGameButton = (Button) layout.findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialize(layout);
            }
        });
    }

    private void initializeSubmitButton(View layout) {
        Button submitButton = (Button) layout.findViewById(R.id.submit);
        checkDigits(submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String guessedValue = new String(currentValue);
                BullsAndCows result = BullsAndCows.calculate(originalValue, guessedValue);
                displayRoundResult(guessedValue, result);
                if (result.bulls == numberOfDigits) {
                    chronometer.stop();
                    long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    displayGameResult(numberOfRounds, elapsedMillis / 1000f);
                } else {
                    numberOfRounds++;
                }
            }
        });
    }

    private void displayGameResult(int numberOfRounds, float timeInSeconds) {
        System.out.println("zzzz: " + numberOfRounds + ". Time: " + timeInSeconds);
    }

    private void displayRoundResult(String currentValue, BullsAndCows result) {
        System.out.println("zzzz: " + currentValue + " : " + result);
    }

    void initialize(View layout) {
        numberOfRounds = 1;
        chronometer = new Chronometer(layout.getContext());
        chronometer.setBase(SystemClock.elapsedRealtime());
        originalValue = new NewNumberGenerator().generate(numberOfDigits);
        switch (numberOfDigits) {
            case 2:
                initializeNumberPickerArray(layout,
                        (NumberPicker) layout.findViewById(R.id.digit_1),
                        (NumberPicker) layout.findViewById(R.id.digit_2));
                break;
            case 3:
                initializeNumberPickerArray(layout,
                        (NumberPicker) layout.findViewById(R.id.digit_1),
                        (NumberPicker) layout.findViewById(R.id.digit_2),
                        (NumberPicker) layout.findViewById(R.id.digit_3));
                break;
            case 4:
                initializeNumberPickerArray(layout,
                        (NumberPicker) layout.findViewById(R.id.digit_1),
                        (NumberPicker) layout.findViewById(R.id.digit_2),
                        (NumberPicker) layout.findViewById(R.id.digit_3),
                        (NumberPicker) layout.findViewById(R.id.digit_4));
                break;
            case 5:
                initializeNumberPickerArray(layout,
                        (NumberPicker) layout.findViewById(R.id.digit_1),
                        (NumberPicker) layout.findViewById(R.id.digit_2),
                        (NumberPicker) layout.findViewById(R.id.digit_3),
                        (NumberPicker) layout.findViewById(R.id.digit_4),
                        (NumberPicker) layout.findViewById(R.id.digit_5));
                break;
            case 6:
                initializeNumberPickerArray(layout,
                        (NumberPicker) layout.findViewById(R.id.digit_1),
                        (NumberPicker) layout.findViewById(R.id.digit_2),
                        (NumberPicker) layout.findViewById(R.id.digit_3),
                        (NumberPicker) layout.findViewById(R.id.digit_4),
                        (NumberPicker) layout.findViewById(R.id.digit_5),
                        (NumberPicker) layout.findViewById(R.id.digit_6));
                break;
            default:
        }
        chronometer.start();
    }


    private void initializeNumberPickerArray(View view, NumberPicker... numberPickerArray) {
        float weight = 1.0f / numberOfDigits;
        for (int i = 0; i < numberPickerArray.length; i++) {
            NumberPicker np = numberPickerArray[i];
            np.setMinValue(0);
            np.setMaxValue(9);
            np.setWrapSelectorWheel(true);
            LinearLayout layout = (LinearLayout) (view.findViewById(np.getId()));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(5, 300, weight);
            layout.setLayoutParams(lp);
            setListener(i, np, view);
        }
    }

    private void setListener(final int i, NumberPicker np, final View view) {
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            Button submit = (Button) view.findViewById(R.id.submit);

            @Override
            public void onValueChange(NumberPicker numberPicker, int oldNum, int newNum) {
                currentValue[i] = Character.forDigit(newNum, 10);
                checkDigits(submit);
            }
        });
    }

    private void checkDigits(Button submit) {
        if (allCharactersDifferent()) {
            submit.setEnabled(true);
        } else {
            submit.setEnabled(false);
        }
    }

    private boolean allCharactersDifferent() {
        for (int i = 0; i < currentValue.length; i++) {
            for (int j = 0; j < currentValue.length; j++) {
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

}
