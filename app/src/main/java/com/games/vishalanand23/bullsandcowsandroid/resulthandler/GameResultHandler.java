package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.R;
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
        TextView youWin = getTextView(layout.getContext());
        youWin.setText("YOU WIN");
        youWin.setTextSize(20);
        youWin.setBackgroundColor(layout.getResources().getColor(R.color.light_blue));
        layout.addView(youWin);

        TextView rounds = getTextView(layout.getContext());
        rounds.setText("Rounds: " + playResult.getNumberOfGuesses());
        layout.addView(rounds);

        TextView time = getTextView(layout.getContext());
        time.setText("Time: " + (playResult.getTimeInMillis() / 1000f) + " seconds");
        layout.addView(time);


        TextView fastestTime = getTextView(layout.getContext());
        fastestTime.setText(numberOfDigits + " Digit Fastest Time: "
                + (storageHelper.fastestTime(numberOfDigits) / 1000f) + " seconds.");
        layout.addView(fastestTime);

        TextView score = getTextView(layout.getContext());
        score.setText(numberOfDigits + " Digit Score: "
                + storageHelper.score(numberOfDigits) / 1000f);
        score.setBackgroundColor(layout.getResources().getColor(R.color.light_blue));
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
