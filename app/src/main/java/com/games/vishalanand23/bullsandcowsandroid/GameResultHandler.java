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
        numberOfGames.setText("Number of Games: " + storageHelper.numberOfGames());
        layout.addView(numberOfGames);

        TextView numberOfWins = getTextView(layout.getContext());
        numberOfWins.setText("Number of Wins: " + storageHelper.numberOfWins());
        layout.addView(numberOfWins);

        TextView fastestTime = getTextView(layout.getContext());
        fastestTime.setText("Fastest Time: " + (storageHelper.fastestTime() / 1000f) + " seconds.");
        layout.addView(fastestTime);
    }


    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
