package com.games.vishalanand23.bullsandcowsandroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ShareHandler {
    private final Activity activity;

    public ShareHandler(Activity activity) {

        this.activity = activity;
    }

    public Intent shareApp() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                activity.getResources().getString(R.string.share_app_string));
        return shareIntent;
    }

    public ShareLinkContent shareScore(long score, int fastestTime, int numberOfDigits) {
        ShareDialog shareDialog = new ShareDialog(activity);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(numberOfDigits + " digit Score = " + score / 1000f + " Fastest time = " + fastestTime / 1000f + " sec")
                    .setContentDescription("Bulls and Cows Score")
                    .setContentUrl(Uri.parse(activity.getResources().getString(R.string.share_developer_url)))
                    .build();

            shareDialog.show(linkContent);
            return linkContent;
        }
        throw new RuntimeException();
    }

    public ShareLinkContent shareGame(int rounds, int time, int numberOfDigits) {
        ShareDialog shareDialog = new ShareDialog(activity);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Won a game of " + numberOfDigits + " digits in " + rounds + " rounds and " + time / 1000f + " sec")
                    .setContentDescription("Bulls and Cows Game")
                    .setContentUrl(Uri.parse(activity.getResources().getString(R.string.share_developer_url)))
                    .build();

            shareDialog.show(linkContent);
            return linkContent;
        }
        throw new RuntimeException();
    }
}
