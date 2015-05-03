package com.games.vishalanand23.bullsandcowsandroid;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RoundResultHandler {

    private final TableLayout table;

    public RoundResultHandler(TableLayout table) {
        this.table = table;
    }

    public void display(String currentValue, BullsAndCows result) {
        TableRow row = new TableRow(table.getContext());
        TextView value = getTextView(table.getContext());
        value.setText(currentValue);
        row.addView(value);

        TextView bulls = getTextView(table.getContext());
        bulls.setText(String.valueOf(result.bulls));
        row.addView(bulls);

        TextView cows = getTextView(table.getContext());
        cows.setText(String.valueOf(result.cows));
        row.addView(cows);
        table.addView(row);
    }

    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
