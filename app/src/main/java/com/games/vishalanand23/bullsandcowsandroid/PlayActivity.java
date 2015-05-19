package com.games.vishalanand23.bullsandcowsandroid;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.games.vishalanand23.bullsandcowsandroid.data.GameData;
import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;
import com.games.vishalanand23.bullsandcowsandroid.db.DbStorageHelper;
import com.games.vishalanand23.bullsandcowsandroid.network.ServerRequestHelper;


public class PlayActivity extends AppCompatActivity {

    private int numberOfDigits = 2;
    private String originalValue;

    private ServerRequestHelper serverRequestHelper;
    private String androidId;
    private DbStorageHelper dbStorageHelper;
    private GameData gameData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        new DbStorageHelper(this).createFile();
//        new DbStorageHelper(this).sanitizeDb();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        reset();
        serverRequestHelper = new ServerRequestHelper(this);
        dbStorageHelper = new DbStorageHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action postRequest if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action postRequest item clicks here. The action postRequest will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        pause();
        super.onPause();
    }

    private void reset() {
        if (numberOfDigits == 0) numberOfDigits = 4; // Base case
        originalValue = new NewNumberGenerator().generate(numberOfDigits);
        gameData = new GameData(this, numberOfDigits);
        initializeSubmitButton();
        initializeNewGameButton();
        initializePauseGameButton();
        clearGuessTableLayout((TableLayout) findViewById(R.id.guess_display));
        clearResultLayout((LinearLayout) findViewById(R.id.result_display));
        clearNumberPickerLayout((LinearLayout) findViewById(R.id.number_roller));
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
                break;
            case 5:
                initializeNumberPickerArray(
                        (NumberPicker) findViewById(R.id.digit_1),
                        (NumberPicker) findViewById(R.id.digit_2),
                        (NumberPicker) findViewById(R.id.digit_3),
                        (NumberPicker) findViewById(R.id.digit_4),
                        (NumberPicker) findViewById(R.id.digit_5));
                break;
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

    private void resume() {
        Button pauseButton = (Button) findViewById(R.id.pause);
        ScrollView roundTable = (ScrollView) findViewById(R.id.guess_table_scroll_view);
        TextView pauseLabel = (TextView) findViewById(R.id.pause_game_label);
        Button submitButton = (Button) findViewById(R.id.submit);
        LinearLayout numberPickerLayout = (LinearLayout) findViewById(R.id.number_roller);
        LinearLayout newGameLayout = (LinearLayout) findViewById(R.id.new_game_label);
        LinearLayout beginGameLayout = (LinearLayout) findViewById(R.id.begin_game);

        gameData.resume();
        pauseButton.setText(getResources().getString(R.string.pause_button_text));
        roundTable.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);
        if (gameData.allCharactersDifferent() && !gameData.isGameWon()) {
            submitButton.setEnabled(true);
        } else {
            submitButton.setEnabled(false);
        }
        numberPickerLayout.setVisibility(View.VISIBLE);
        newGameLayout.setVisibility(View.VISIBLE);
        beginGameLayout.setVisibility(View.VISIBLE);

        pauseLabel.setVisibility(View.GONE);
    }

    private void pause() {
        Button pauseButton = (Button) findViewById(R.id.pause);
        ScrollView roundTable = (ScrollView) findViewById(R.id.guess_table_scroll_view);
        TextView pauseLabel = (TextView) findViewById(R.id.pause_game_label);
        Button submitButton = (Button) findViewById(R.id.submit);
        LinearLayout numberPickerLayout = (LinearLayout) findViewById(R.id.number_roller);
        LinearLayout newGameLayout = (LinearLayout) findViewById(R.id.new_game_label);
        LinearLayout beginGameLayout = (LinearLayout) findViewById(R.id.begin_game);

        gameData.pause();
        pauseButton.setText(getResources().getString(R.string.unpause_button_text));
        roundTable.setVisibility(View.GONE);
        submitButton.setVisibility(View.INVISIBLE);
        numberPickerLayout.setVisibility(View.INVISIBLE);
        newGameLayout.setVisibility(View.GONE);
        beginGameLayout.setVisibility(View.GONE);

        pauseLabel.setVisibility(View.VISIBLE);
    }

    private void initializeSubmitButton() {
        final Button submitButton = (Button) findViewById(R.id.submit);
        androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        submitButton.setEnabled(false);
        SubmitOnClickListener submitListener =
                new SubmitOnClickListener(this, numberOfDigits, originalValue, gameData);
        submitButton.setOnClickListener(submitListener);
    }

    private void initializePauseGameButton() {
        final Button pauseButton = (Button) findViewById(R.id.pause);
        final TextView pauseLabel = (TextView) findViewById(R.id.pause_game_label);
        pauseLabel.setVisibility(View.GONE);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameData.isGamePaused()) {
                    resume();
                } else {
                    pause();
                }
            }
        });
    }

    private void initializeNewGameButton() {
        // TODO: Figure out how to pass winGame dynamically and then extract this to a new class.
        Button newGameButton2 = (Button) findViewById(R.id.new_game_2);
        newGameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 2;
                reset();
            }
        });

        Button newGameButton3 = (Button) findViewById(R.id.new_game_3);
        newGameButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 3;
                reset();
            }
        });

        Button newGameButton4 = (Button) findViewById(R.id.new_game_4);
        newGameButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 4;
                reset();
            }
        });

        Button newGameButton5 = (Button) findViewById(R.id.new_game_5);
        newGameButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 5;
                reset();
            }
        });

        Button newGameButton6 = (Button) findViewById(R.id.new_game_6);
        newGameButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLostGameIfNecessary();
                numberOfDigits = 6;
                reset();
            }
        });

    }

    private void initializeNumberPickerArray(NumberPicker... numberPickerArray) {
        float weight = 1.0f / numberOfDigits;
        for (int i = 0; i < numberPickerArray.length; i++) {
            NumberPicker np = numberPickerArray[i];
            np.setVisibility(View.VISIBLE);
            np.setMinValue(0);
            np.setMaxValue(9);
            np.setValue(0);
            np.setWrapSelectorWheel(true);
            np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            LinearLayout linearLayout = (LinearLayout) (findViewById(np.getId()));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(5, 300, weight);
            linearLayout.setLayoutParams(lp);
            final int i1 = i;
            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                Button submit = (Button) findViewById(R.id.submit);

                @Override
                public void onValueChange(NumberPicker numberPicker, int oldNum, int newNum) {
                    gameData.setChar(i1, Character.forDigit(newNum, 10));
                    if (gameData.allCharactersDifferent() && !gameData.isGameWon()) {
                        submit.setEnabled(true);
                    } else {
                        submit.setEnabled(false);
                    }
                }
            });
        }
    }

    private void saveLostGameIfNecessary() {
        if (!gameData.isGameWon()) {
            PlayResult lostGame = new PlayResult(androidId, numberOfDigits, originalValue,
                    -1, 0, Integer.MAX_VALUE);
            dbStorageHelper.insertInDb(lostGame);
            serverRequestHelper.postRequest(lostGame);
        }
    }

    private void clearNumberPickerLayout(LinearLayout linearLayout) {
        int count = linearLayout.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = linearLayout.getChildAt(i);
            child.setVisibility(View.INVISIBLE);
        }
    }

    private void clearGuessTableLayout(TableLayout table) {
        int count = table.getChildCount();
        for (int i = count - 1; i >= 1; i--) { // 1st row is header, so don't clear.
            View child = table.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
    }

    private void clearResultLayout(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.INVISIBLE);
        int count = linearLayout.getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            View child = linearLayout.getChildAt(i);
            linearLayout.removeView(child);
        }
    }
}
