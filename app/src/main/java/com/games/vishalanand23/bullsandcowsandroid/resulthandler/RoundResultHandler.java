package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.R;
import com.games.vishalanand23.bullsandcowsandroid.data.BullsAndCows;

public class RoundResultHandler {

    private final TableLayout table;
    private boolean isBlue = true;

    public RoundResultHandler(TableLayout table) {
        this.table = table;
    }

    public void display(String currentValue, BullsAndCows result) {
        TableRow row = new TableRow(table.getContext());
        TableLayout.LayoutParams lp =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);

        lp.setMargins(0, 2, 0, 2);
        row.setLayoutParams(lp);
        row.setBackgroundColor(table.getResources().getColor(R.color.light_blue));
        TextView value = getTextView(table.getContext());
        value.setText(currentValue);
        row.addView(value);

        TextView bulls = getTextView(table.getContext());
        bulls.setText(String.valueOf(result.getBulls()));
        row.addView(bulls);

        TextView cows = getTextView(table.getContext());
        cows.setText(String.valueOf(result.getCows()));
        row.addView(cows);
        table.addView(row, lp);
    }

    private TextView getTextView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
