package com.android.startupweekend.free.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shang on 6/6/2015.
 */
public class PromoItemTable {

    // Database Table
    public static final String TABLE_PROMOITEM = "promoitems";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CAPTION = "caption";
    public static final String COLUMN_DESCRIPTION = "description";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PROMOITEM
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_IMAGE + " integer, "
            + COLUMN_CAPTION + " text, "
            + COLUMN_DESCRIPTION + " text"
            + ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database) {

    }
}
