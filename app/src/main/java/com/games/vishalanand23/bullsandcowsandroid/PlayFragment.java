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

    private volatile boolean winGame = false;
    private char[] currentValue = new char[numberOfDigits];

    private ServerRequestHelper serverRequestHelper;
    private DbStorageHelper dbStorageHelper;
    private String androidId;

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
        Button newGameButton = (Button) layout.findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!winGame) {
                    PlayResult lostGame = new PlayResult(androidId, numberOfDigits, originalValue,
                            -1, 0, Integer.MAX_VALUE);
                    dbStorageHelper.insertInDb(lostGame);
                    serverRequestHelper.postRequest(lostGame);
                }
                reset(layout);
            }
        });
    }

    private void initializeSubmitButton(final View layout) {
        Button submitButton = (Button) layout.findViewById(R.id.submit);
        androidId = Secure.getString(layout.getContext().getContentResolver(),
                Secure.ANDROID_ID);
        submitButton.setEnabled(false);
        SubmitOnClickListener submitListener =
                new SubmitOnClickListener(layout, numberOfDigits, originalValue, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        winGame = true;
                        return null;
                    }
                });
        submitButton.setOnClickListener(submitListener);
        submitListener.reset();
    }

    private void reset(View layout) {
        originalValue = new NewNumberGenerator().generate(numberOfDigits);
        initializeSubmitButton(layout);
        initializeNewGameButton(layout);
        winGame = false;
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
        clearGuessTableLayout((TableLayout) layout.findViewById(R.id.guess_display));
        clearResultLayout((LinearLayout) layout.findViewById(R.id.result_display));
        for (int i = 0; i < currentValue.length; i++) {
            currentValue[i] = '0';
        }
    }

    private void clearGuessTableLayout(TableLayout table) {
        int count = table.getChildCount();
        for (int i = 1; i < count; i++) {
            View child = table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    private void clearResultLayout(LinearLayout layout) {
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
            np.setMinValue(0);
            np.setMaxValue(9);
            np.setValue(0);
            np.setWrapSelectorWheel(true);
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
