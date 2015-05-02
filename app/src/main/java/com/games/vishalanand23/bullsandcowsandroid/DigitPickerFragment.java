package com.games.vishalanand23.bullsandcowsandroid;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    private void initializeSubmitButton(final View layout) {
        Button submitButton = (Button) layout.findViewById(R.id.submit);
        checkDigits(submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TableLayout table = (TableLayout) layout.findViewById(R.id.guess_display);
                String guessedValue = new String(currentValue);
                BullsAndCows result = BullsAndCows.calculate(originalValue, guessedValue);
                displayRoundResult(table, guessedValue, result);
                if (result.bulls == numberOfDigits) {
                    chronometer.stop();
                    long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    displayGameResult(table, numberOfRounds, elapsedMillis / 1000f);
                } else {
                    numberOfRounds++;
                }
            }
        });
    }

    private void displayGameResult(TableLayout table, int numberOfRounds, float timeInSeconds) {
        TableRow row1 = new TableRow(table.getContext());
        TableRow row2 = new TableRow(table.getContext());
        TextView rounds = getTextView(table.getContext());
        rounds.setText("Rounds: " + numberOfRounds);
        row1.addView(rounds);
        TextView time = getTextView(table.getContext());
        time.setText("Time: " + timeInSeconds + " seconds");
        row2.addView(time);
        table.addView(row1);
        table.addView(row2);
    }

    private void displayRoundResult(TableLayout table, String currentValue, BullsAndCows result) {
        TableRow row = new TableRow(table.getContext());
        TextView value = getTextView(table.getContext());
        value.setText(currentValue);
        row.addView(value);

        TextView bulls = getTextView(table.getContext());
        bulls.setText(String.valueOf(result.bulls));
        row.addView(bulls);

        TextView cows = getTextView(table.getContext());
        cows.setText(String.valueOf(result.cows));
        row.addView(cows);
        table.addView(row);
    }

    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
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
        clearTableLayout((TableLayout) layout.findViewById(R.id.guess_display));
        initializeCurrentValueArray();
        chronometer.start();
    }

    private void clearTableLayout(TableLayout table) {
        int count = table.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    private void initializeCurrentValueArray() {
        for (int i = 0; i < currentValue.length; i++) {
            currentValue[i] = '0';
        }
    }

    private void initializeNumberPickerArray(View view, NumberPicker... numberPickerArray) {
        float weight = 1.0f / numberOfDigits;
        for (int i = 0; i < numberPickerArray.length; i++) {
            NumberPicker np = numberPickerArray[i];
            np.setMinValue(0);
            np.setMaxValue(9);
            np.setValue(0);
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
