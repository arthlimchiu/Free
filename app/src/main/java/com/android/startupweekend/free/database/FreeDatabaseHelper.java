package com.android.startupweekend.free.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shang on 6/6/2015.
 */
public class FreeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "free.db";
    private static final int DATABASE_VERSION = 1;

    public FreeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PromoItemTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
