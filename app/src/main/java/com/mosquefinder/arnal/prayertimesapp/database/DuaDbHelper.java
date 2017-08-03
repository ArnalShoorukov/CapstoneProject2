package com.mosquefinder.arnal.prayertimesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry;
/**
 * Created by arnal on 7/29/17.
 */

public class DuaDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "duas.db";
    private static final int DB_VERSION = 1;

    public DuaDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + DuaEntry.TABLE_NAME + " (" +
                DuaEntry._ID                            + " INTEGER PRIMARY KEY, " +
                DuaEntry.COLUMN_DUA_ID                  + " INTEGER NOT NULL, " +
                DuaEntry.COLUMN_TITLE                   +  " TEXT NOT NULL, " +
                DuaEntry.COLUMN_ENGLISH                 +  " TEXT NOT NULL, " +
                DuaEntry.COLUMN_REFERENCE               + " TEXT NOT NULL, " +
                DuaEntry.COLUMN_BENEFIT                 + " TEXT NOT NULL, " +
                DuaEntry.COLUMN_AUDIO_RESOURCE_ID       + " INTEGER NOT NULL, " +
                DuaEntry.COLUMN_IMAGE_RESOURCE_ID       + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DuaEntry.TABLE_NAME);
        onCreate(db);
    }
}
