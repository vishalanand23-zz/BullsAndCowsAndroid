package com.games.vishalanand23.bullsandcowsandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.NumberPicker;


public class PlayActivity extends AppCompatActivity {
    private int numberOfDigits = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_picker_activity);
        initializeNumberPicker();
    }

    private void initializeNumberPicker() {
        switch (numberOfDigits) {
            case 2:
                initializeNumberPickerArray(
                        (NumberPicker) findViewById(R.id.digit_1),
                        (NumberPicker) findViewById(R.id.digit_2));
                break;
            case 3:
                initializeNumberPickerArray(
                        (NumberPicker) findViewById(R.id.digit_1),
                        (NumberPicker) findViewById(R.id.digit_2),
                        (NumberPicker) findViewById(R.id.digit_3));
                break;
            case 4:
                initializeNumberPickerArray(
                        (NumberPicker) findViewById(R.id.digit_1),
                        (NumberPicker) findViewById(R.id.digit_2),
                        (NumberPicker) findViewById(R.id.digit_3),
                        (NumberPicker) findViewById(R.id.digit_4));
            case 5:
                initializeNumberPickerArray(
                        (NumberPicker) findViewById(R.id.digit_1),
                        (NumberPicker) findViewById(R.id.digit_2),
                        (NumberPicker) findViewById(R.id.digit_3),
                        (NumberPicker) findViewById(R.id.digit_4),
                        (NumberPicker) findViewById(R.id.digit_5));
            case 6:
                initializeNumberPickerArray(
                        (NumberPicker) findViewById(R.id.digit_1),
                        (NumberPicker) findViewById(R.id.digit_2),
                        (NumberPicker) findViewById(R.id.digit_3),
                        (NumberPicker) findViewById(R.id.digit_4),
                        (NumberPicker) findViewById(R.id.digit_5),
                        (NumberPicker) findViewById(R.id.digit_6));
                break;
            default:

        }
    }


    private void initializeNumberPickerArray(NumberPicker... spinnerArray) {
        float weight = 1.0f / numberOfDigits;
        for (final NumberPicker np : spinnerArray) {
            np.setMinValue(0);
            np.setMaxValue(9);
            np.setWrapSelectorWheel(true);
            LinearLayout layout = (LinearLayout) findViewById(np.getId());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(5, 500, weight);
            layout.setLayoutParams(lp);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
