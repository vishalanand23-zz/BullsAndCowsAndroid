package com.games.vishalanand23.bullsandcowsandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class PlayActivity extends AppCompatActivity {
    private int numberOfDigits = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        switch (numberOfDigits) {
            case 2:
                initialize(
                        (Spinner) findViewById(R.id.digit_1),
                        (Spinner) findViewById(R.id.digit_2));
                break;
            case 3:
                initialize(
                        (Spinner) findViewById(R.id.digit_1),
                        (Spinner) findViewById(R.id.digit_2),
                        (Spinner) findViewById(R.id.digit_3));
                break;
            case 4:
                initialize(
                        (Spinner) findViewById(R.id.digit_1),
                        (Spinner) findViewById(R.id.digit_2),
                        (Spinner) findViewById(R.id.digit_3),
                        (Spinner) findViewById(R.id.digit_4));
                break;
            default:

        }
        setContentView(R.layout.activity_game);
    }

    private void initialize(Spinner... spinnerArray) {
        for (final Spinner spinner : spinnerArray) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.digit_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                    String ss = spinner.getSelectedItem().toString();
                    Toast.makeText(getBaseContext(), ss, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }

            });
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
