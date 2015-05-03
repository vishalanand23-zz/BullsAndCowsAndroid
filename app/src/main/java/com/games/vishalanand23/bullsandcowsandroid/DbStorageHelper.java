package com.games.vishalanand23.bullsandcowsandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbStorageHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bulls_and_cows";
    private static final String TABLE_NAME = "play_results";
    private static final String ID = "_id";
    private static final String DEVICE_ID = "device" + ID;
    private static final String NUM_OF_DIGITS = "num_of_digits";
    private static final String PLAYING_NUMBER = "playing_number";
    private static final String NUMBER_OF_GUESSES = "number_of_guesses";
    private static final String WIN_GAME = "win_game";
    private static final String TIME_IN_MILLIS = "time_in_millis";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    ID + " INTEGER primary key autoincrement," +
                    DEVICE_ID + " TEXT ," +
                    NUM_OF_DIGITS + " INTEGER ," +
                    PLAYING_NUMBER + " TEXT ," +
                    NUMBER_OF_GUESSES + " INTEGER ," +
                    WIN_GAME + " INTEGER ," +
                    TIME_IN_MILLIS + " INTEGER );";

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
        db.execSQL("DROP TABLE IF EXISTS: " + TABLE_NAME);
        onCreate(db);
    }

    public void insertInDb(PlayResult playResult) {
        ContentValues values = new ContentValues();
        values.put(DEVICE_ID, playResult.deviceId);
        values.put(NUM_OF_DIGITS, playResult.numberOfDigits);
        values.put(PLAYING_NUMBER, playResult.playingNumber);
        values.put(NUMBER_OF_GUESSES, playResult.numberOfGuesses);
        values.put(WIN_GAME, playResult.winGame);
        values.put(TIME_IN_MILLIS, playResult.timeInMillis);
        getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public int fastestTime() {
        String[] projection = {TIME_IN_MILLIS};
        String[] whereArgs = {Integer.toString(1)};
        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                projection,
                WIN_GAME + " = ?",
                whereArgs,
                null,
                null,
                TIME_IN_MILLIS,
                "1");
        cursor.moveToFirst();
        int fastestTime = cursor.getInt(0);
        cursor.close();
        return fastestTime;
    }

    public int numberOfGames() {
        String[] projection = {ID};
        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        int number = cursor.getCount();
        cursor.close();
        return number;
    }

    public int numberOfWins() {
        String[] projection = {ID};
        String[] whereArgs = {Integer.toString(1)};
        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME,
                projection,
                WIN_GAME + " = ?",
                whereArgs,
                null,
                null,
                null);
        int number = cursor.getCount();
        cursor.close();
        return number;
    }
}