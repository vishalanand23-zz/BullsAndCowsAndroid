package com.games.vishalanand23.bullsandcowsandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbStorageHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bullsAndCows";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE play_results (" +
                    "  _id INTEGER primary key autoincrement," +
                    "  device_id TEXT ," +
                    "  num_of_digits INTEGER ," +
                    "  playing_number TEXT ," +
                    "  number_of_guesses INTEGER ," +
                    "  win_game INTEGER ," +
                    "  time_in_millis INTEGER ";

    public DbStorageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbStorageHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS: bullsAndCows");
        onCreate(db);
    }
}