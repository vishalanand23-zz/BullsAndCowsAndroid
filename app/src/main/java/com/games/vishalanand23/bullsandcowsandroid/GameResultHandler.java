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

    public void displayGameResult(PlayResult playResult) {
        TableRow row1 = new TableRow(table.getContext());
        TableRow row2 = new TableRow(table.getContext());
        TextView rounds = getTextView(table.getContext());
        rounds.setText("Rounds: " + playResult.numberOfGuesses);
        row1.addView(rounds);
        TextView time = getTextView(table.getContext());
        time.setText("Time: " + (playResult.timeInMillis / 1000) + " seconds");
        row2.addView(time);
        table.addView(row1);
        table.addView(row2);
    }


    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
