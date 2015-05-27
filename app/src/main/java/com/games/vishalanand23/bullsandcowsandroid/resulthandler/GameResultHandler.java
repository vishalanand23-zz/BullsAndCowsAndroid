package com.games.vishalanand23.bullsandcowsandroid.resulthandler;

import android.view.View;
import android.widget.TextView;

import com.facebook.share.widget.ShareButton;
import com.games.vishalanand23.bullsandcowsandroid.PlayActivity;
import com.games.vishalanand23.bullsandcowsandroid.R;
import com.games.vishalanand23.bullsandcowsandroid.ShareHandler;
import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;

public class GameResultHandler {
    private final PlayActivity activity;

    public GameResultHandler(PlayActivity activity) {
        this.activity = activity;
    }

    public void displayGameResult(final PlayResult playResult, int numberOfDigits) {
        TextView numberOfDigitView =
                (TextView) activity.findViewById(R.id.number_of_digits_text_in_play);
        numberOfDigitView.setText("Number of Digits: " + numberOfDigits);
        TextView numberOfRoundsView =
                (TextView) activity.findViewById(R.id.number_of_rounds_text_in_play);
        numberOfRoundsView.setText("Number of Rounds: " + playResult.getNumberOfGuesses());
        TextView timeTextView =
                (TextView) activity.findViewById(R.id.time_text_in_play);
        timeTextView.setText("Time: " + (playResult.getTimeInMillis() / 1000f) + " sec");
        final ShareHandler shareHandler = new ShareHandler(activity);
        final ShareButton shareButton = (ShareButton) activity.findViewById(R.id.fb_game_share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareButton.setShareContent(shareHandler.shareGame(
                        playResult.getNumberOfGuesses(),
                        playResult.getTimeInMillis(),
                        playResult.getNumberOfDigits()
                ));
            }
        });
        activity.findViewById(R.id.result_display).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.number_roller).setVisibility(View.GONE);
    }
}
