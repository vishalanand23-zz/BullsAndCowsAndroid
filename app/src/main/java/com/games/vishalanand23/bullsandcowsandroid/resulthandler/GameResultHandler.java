package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.view.View;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.PlayActivity;
import com.games.vishalanand23.bullsandcowsandroid.R;
import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;

public class GameResultHandler {
    private final PlayActivity activity;

    public GameResultHandler(PlayActivity activity) {
        this.activity = activity;
    }

    public void displayGameResult(PlayResult playResult, int numberOfDigits) {
        TextView numberOfDigitView =
                (TextView) activity.findViewById(R.id.number_of_digits_text_in_play);
        numberOfDigitView.setText("Number of Digits: " + numberOfDigits);
        TextView numberOfRoundsView =
                (TextView) activity.findViewById(R.id.number_of_rounds_text_in_play);
        numberOfRoundsView.setText("Number of Rounds: " + playResult.getNumberOfGuesses());
        TextView timeTextView =
                (TextView) activity.findViewById(R.id.time_text_in_play);
        timeTextView.setText("Time: " + (playResult.getTimeInMillis() / 1000f) + " seconds");
        activity.findViewById(R.id.result_display).setVisibility(View.VISIBLE);
    }
}
