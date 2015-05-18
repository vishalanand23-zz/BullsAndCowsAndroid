package com.games.vishalanand23.bullsandcowsandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;
import com.games.vishalanand23.bullsandcowsandroid.db.DbStorageHelper;
import com.games.vishalanand23.bullsandcowsandroid.network.ServerRequestHelper;

import java.util.concurrent.Callable;

public class PlayFragment extends Fragment {
    private int numberOfDigits = 4;
    private String originalValue;

    private ServerRequestHelper serverRequestHelper;
    private String androidId;
    private DbStorageHelper dbStorageHelper;

    private volatile boolean winGame = false;
    private volatile char[] currentValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_play, container, false);
//        new DbStorageHelper(layout.getContext()).createFile();
//        new DbStorageHelper((layout.getContext())).sanitizeDb();
        reset(layout);
        serverRequestHelper = new ServerRequestHelper(layout.getContext());
        dbStorageHelper = new DbStorageHelper(layout.getContext());
        return layout;
    }

    private void initializeNewGameButton(final View layout) {
        // TODO: Figure out how to pass winGame dynamically and then extract this to a new class.
        Button newGameButton2 = (Button) layout.findViewById(R.id.new_game_2);
        newGameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 2;
                reset(layout);
            }
        });

        Button newGameButton3 = (Button) layout.findViewById(R.id.new_game_3);
        newGameButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 3;
                reset(layout);
            }
        });

        Button newGameButton4 = (Button) layout.findViewById(R.id.new_game_4);
        newGameButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 4;
                reset(layout);
            }
        });

        Button newGameButton5 = (Button) layout.findViewById(R.id.new_game_5);
        newGameButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 5;
                reset(layout);
            }
        });

        Button newGameButton6 = (Button) layout.findViewById(R.id.new_game_6);
        newGameButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 6;
                reset(layout);
            }
        });

    }

    private void saveLostGameIfNecessary() {
        if (!winGame) {
            PlayResult lostGame = new PlayResult(androidId, numberOfDigits, originalValue,
                    -1, 0, Integer.MAX_VALUE);
            dbStorageHelper.insertInDb(lostGame);
            serverRequestHelper.postRequest(lostGame);
        }
    }

    private void initializeSubmitButton(final View layout) {
        final Button submitButton = (Button) layout.findViewById(R.id.submit);
        androidId = Secure.getString(layout.getContext().getContentResolver(),
                Secure.ANDROID_ID);
        submitButton.setEnabled(false);
        SubmitOnClickListener submitListener =
                new SubmitOnClickListener(layout, numberOfDigits, originalValue, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        winGame = true;
                        submitButton.setEnabled(false);
                        return null;
                    }
                });
        submitButton.setOnClickListener(submitListener);
        submitListener.reset();
    }

    private void reset(View layout) {
        if (numberOfDigits == 0) numberOfDigits = 4; // Base case
        currentValue = new char[numberOfDigits];
        originalValue = new NewNumberGenerator().generate(numberOfDigits);
        initializeSubmitButton(layout);
        initializeNewGameButton(layout);
        winGame = false;
        clearGuessTableLayout((TableLayout) layout.findViewById(R.id.guess_display));
        clearResultLayout((LinearLayout) layout.findViewById(R.id.result_display));
        clearNumberPickerLayout((LinearLayout) layout.findViewById(R.id.number_roller));
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
        for (int i = 0; i < currentValue.length; i++) {
            currentValue[i] = '0';
        }
    }

    private void clearNumberPickerLayout(LinearLayout layout) {
        int count = layout.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
        }
    }

    private void clearGuessTableLayout(TableLayout table) {
        int count = table.getChildCount();
        for (int i = count - 1; i >= 1; i--) { // 1st row is header, so don't clear.
            View child = table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    private void clearResultLayout(LinearLayout layout) {
        layout.setVisibility(View.INVISIBLE);
        int count = layout.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            layout.removeView(child);
        }
    }

    private void initializeNumberPickerArray(final View view, NumberPicker... numberPickerArray) {
        float weight = 1.0f / numberOfDigits;
        for (int i = 0; i < numberPickerArray.length; i++) {
            NumberPicker np = numberPickerArray[i];
            np.setVisibility(View.VISIBLE);
            np.setMinValue(0);
            np.setMaxValue(9);
            np.setValue(0);
            np.setWrapSelectorWheel(true);
            np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            LinearLayout layout = (LinearLayout) (view.findViewById(np.getId()));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(5, 300, weight);
            layout.setLayoutParams(lp);
            final int i1 = i;
            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                Button submit = (Button) view.findViewById(R.id.submit);
                @Override
                public void onValueChange(NumberPicker numberPicker, int oldNum, int newNum) {
                    currentValue[i1] = Character.forDigit(newNum, 10);
                    checkDigits(submit);
                }
            });
        }
    }

    private void checkDigits(Button submit) {
        if (allCharactersDifferent() && !winGame) {
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
