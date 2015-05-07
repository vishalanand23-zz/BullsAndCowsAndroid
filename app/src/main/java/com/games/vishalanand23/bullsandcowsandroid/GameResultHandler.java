package com.games.vishalanand23.bullsandcowsandroid;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;
import com.games.vishalanand23.bullsandcowsandroid.db.DbStorageHelper;

public class GameResultHandler {
    private final LinearLayout layout;

    public GameResultHandler(LinearLayout layout) {
        this.layout = layout;
    }

    public void displayGameResult(PlayResult playResult, DbStorageHelper storageHelper) {
        TextView rounds = getTextView(layout.getContext());
        rounds.setText("Rounds: " + playResult.getNumberOfGuesses());
        layout.addView(rounds);

        TextView time = getTextView(layout.getContext());
        time.setText("Time: " + (playResult.getTimeInMillis() / 1000f) + " seconds");
        layout.addView(time);

        TextView numberOfGames = getTextView(layout.getContext());
        numberOfGames.setText("Games Win/Played: " + storageHelper.numberOfWins()
                + "/" + storageHelper.numberOfGames());
        layout.addView(numberOfGames);

        TextView fastestTime = getTextView(layout.getContext());
        fastestTime.setText("Fastest Time: " + (storageHelper.fastestTime() / 1000f) + " seconds.");
        layout.addView(fastestTime);

        TextView score = getTextView(layout.getContext());
        fastestTime.setText("Score: " + storageHelper.score() / 1000f);
        layout.addView(score);
    }


    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
