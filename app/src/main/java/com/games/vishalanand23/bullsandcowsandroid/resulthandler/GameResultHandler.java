package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;
import com.games.vishalanand23.bullsandcowsandroid.db.DbStorageHelper;

public class GameResultHandler {
    private final LinearLayout layout;

    public GameResultHandler(LinearLayout layout) {
        this.layout = layout;
    }

    public void displayGameResult(PlayResult playResult,
                                  DbStorageHelper storageHelper,
                                  int numberOfDigits) {
        clearResultLayout();
        TextView digits = getTextView(layout.getContext());
        digits.setText("Number of digits: " + numberOfDigits);
        layout.addView(digits);

        TextView rounds = getTextView(layout.getContext());
        rounds.setText("Rounds: " + playResult.getNumberOfGuesses());
        layout.addView(rounds);

        TextView time = getTextView(layout.getContext());
        time.setText("Time: " + (playResult.getTimeInMillis() / 1000f) + " seconds");
        layout.addView(time);

        TextView numberOfGames = getTextView(layout.getContext());
        numberOfGames.setText("Games Win/Played: " + storageHelper.numberOfWins(numberOfDigits)
                + "/" + storageHelper.numberOfGames(numberOfDigits));
        layout.addView(numberOfGames);

        TextView fastestTime = getTextView(layout.getContext());
        fastestTime.setText("Fastest Time: "
                + (storageHelper.fastestTime(numberOfDigits) / 1000f) + " seconds.");
        layout.addView(fastestTime);

        TextView score = getTextView(layout.getContext());
        score.setText("Score: " + storageHelper.score(numberOfDigits) / 1000f);
        layout.addView(score);
    }


    private void clearResultLayout() {
        layout.setVisibility(View.VISIBLE);
        int count = layout.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            layout.removeView(child);
        }
    }
    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
