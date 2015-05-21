package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.R;
import com.games.vishalanand23.bullsandcowsandroid.data.BullsAndCows;

public class RoundResultHandler {

    private final TableLayout table;

    public RoundResultHandler(TableLayout table) {
        this.table = table;
    }

    public void display(String currentValue, BullsAndCows result) {
        TableRow row = new TableRow(table.getContext());
        TableLayout.LayoutParams lp =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 4, 0, 0);
        row.setLayoutParams(lp);
        row.setBackgroundColor(table.getResources().getColor(R.color.medium_blue));
        TextView value = getTextView();
        value.setText(currentValue);
        value.setTextColor(table.getResources().getColor(R.color.white));
        row.addView(value);

        TextView bulls = getTextView();
        bulls.setText(String.valueOf(result.getBulls()));
        bulls.setTextColor(table.getResources().getColor(R.color.white));
        row.addView(bulls);

        TextView cows = getTextView();
        cows.setText(String.valueOf(result.getCows()));
        cows.setTextColor(table.getResources().getColor(R.color.white));
        row.addView(cows);
        table.addView(row, lp);
    }

    private TextView getTextView() {
        TextView textView = new TextView(table.getContext());
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
