package com.games.vishalanand23.bullsandcowsandroid;

import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.games.vishalanand23.bullsandcowsandroid.data.BullsAndCows;
import com.games.vishalanand23.bullsandcowsandroid.data.GameData;
import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;
import com.games.vishalanand23.bullsandcowsandroid.db.DbStorageHelper;
import com.games.vishalanand23.bullsandcowsandroid.network.ServerRequestHelper;
import com.games.vishalanand23.bullsandcowsandroid.resulthandler.GameResultHandler;
import com.games.vishalanand23.bullsandcowsandroid.resulthandler.RoundResultHandler;

public class SubmitOnClickListener implements View.OnClickListener {
    private final View layout;
    private final int numberOfDigits;
    private final String originalValue;
    private final GameData gameData;
    private final RoundResultHandler roundResulthandler;
    private final GameResultHandler gameResultHandler;
    private final ServerRequestHelper serverRequestHelper;
    private final DbStorageHelper dbStorageHelper;
    private final String androidId;

    public SubmitOnClickListener(View layout, int numberOfDigits, String originalValue,
                                 GameData gameData) {
        this.layout = layout;
        this.numberOfDigits = numberOfDigits;
        this.originalValue = originalValue;
        this.gameData = gameData;
        TableLayout guessTable = (TableLayout) layout.findViewById(R.id.guess_display);
        LinearLayout resultTable = (LinearLayout) layout.findViewById(R.id.result_display);
        this.roundResulthandler = new RoundResultHandler(guessTable);
        this.gameResultHandler = new GameResultHandler(resultTable);
        this.serverRequestHelper = new ServerRequestHelper(layout.getContext());
        this.dbStorageHelper = new DbStorageHelper(layout.getContext());
        this.androidId = Settings.Secure.getString(layout.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onClick(View view) {
        String guessedValue = new String(fetchCurrentValue());
        BullsAndCows result = BullsAndCows.calculate(originalValue, guessedValue);
        roundResulthandler.display(guessedValue, result);
        if (result.isGuessCorrect(numberOfDigits)) {
            gameData.setWin();
            int elapsedMillis = gameData.getGameTime();
            PlayResult playResult = new PlayResult(
                    androidId,
                    numberOfDigits,
                    originalValue,
                    gameData.numberOfRounds(),
                    1,
                    elapsedMillis);
            dbStorageHelper.insertInDb(playResult);
            serverRequestHelper.postRequest(playResult);
            gameResultHandler.displayGameResult(playResult, dbStorageHelper, numberOfDigits);
        } else {
            gameData.roundOver();
        }
        forceScrollerDown();
    }


    private char[] fetchCurrentValue() {
        char[] charArray = new char[numberOfDigits];
        for (int i = 0; i < numberOfDigits; i++) {
            NumberPicker picker = (NumberPicker) layout.findViewById(getScrollerId(i));
            charArray[i] = Character.forDigit(picker.getValue(), 10);
        }
        return charArray;
    }

    private int getScrollerId(int i) {
        switch (i) {
            case 0:
                return R.id.digit_1;
            case 1:
                return R.id.digit_2;
            case 2:
                return R.id.digit_3;
            case 3:
                return R.id.digit_4;
            case 4:
                return R.id.digit_5;
            case 5:
                return R.id.digit_6;
            default:
                throw new RuntimeException("Wrong number.");
        }
    }

    private void forceScrollerDown() {
        final ScrollView scroll = (ScrollView) layout.findViewById(R.id.guess_table_scroll_view);
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
