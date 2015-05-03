package com.games.vishalanand23.bullsandcowsandroid;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameResultHandler {
    private final TableLayout table;

    public GameResultHandler(TableLayout table) {
        this.table = table;
    }

    public void displayGameResult(PlayResult playResult, DbStorageHelper storageHelper) {
        TableRow row0 = new TableRow(table.getContext());
        TextView result = getTextView(table.getContext());
        result.setText("Results: ");
        row0.addView(result);
        table.addView(row0);

        TableRow row1 = new TableRow(table.getContext());
        TextView rounds = getTextView(table.getContext());
        rounds.setText("Rounds: " + playResult.numberOfGuesses);
        row1.addView(rounds);
        table.addView(row1);

        TableRow row2 = new TableRow(table.getContext());
        TextView time = getTextView(table.getContext());
        time.setText("Time: " + (playResult.timeInMillis / 1000) + " seconds");
        row2.addView(time);
        table.addView(row2);

        TableRow row3 = new TableRow(table.getContext());
        TextView numberOfGames = getTextView(table.getContext());
        numberOfGames.setText("Number of Games: " + storageHelper.numberOfGames());
        row3.addView(numberOfGames);
        table.addView(row3);

        TableRow row4 = new TableRow(table.getContext());
        TextView numberOfWins = getTextView(table.getContext());
        numberOfWins.setText("Number of Wins: " + storageHelper.numberOfWins());
        row4.addView(numberOfWins);
        table.addView(row4);

        TableRow row5 = new TableRow(table.getContext());
        TextView fastestTime = getTextView(table.getContext());
        fastestTime.setText("Fastest Time: " + (storageHelper.fastestTime() / 1000) + " seconds.");
        row5.addView(fastestTime);
        table.addView(row5);
    }


    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
