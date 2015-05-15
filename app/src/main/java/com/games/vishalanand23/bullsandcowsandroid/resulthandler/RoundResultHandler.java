package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.data.BullsAndCows;

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
        bulls.setText(String.valueOf(result.getBulls()));
        row.addView(bulls);

        TextView cows = getTextView(table.getContext());
        cows.setText(String.valueOf(result.getCows()));
        row.addView(cows);
        table.addView(row);
    }

    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
