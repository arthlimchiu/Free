package com.android.startupweekend.free.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.android.startupweekend.free.database.FreeDatabaseHelper;
import com.android.startupweekend.free.database.PromoItemTable;

/**
 * Created by shang on 6/6/2015.
 */
public class FreeContentProvider extends ContentProvider {

    private FreeDatabaseHelper database;

    // Used for the UriMatcher
    private static final int PROMOS = 1;
    private static final int PROMO_ID = 2;

    private static final String AUTHORITY = "com.android.startupweekend.free.contentprovider";
    private static final String PATH_PROMOITEMS = "promoitems";

    public static final Uri CONTENT_URI_PROMOITEMS = Uri.parse("content://" + AUTHORITY + "/" + PATH_PROMOITEMS);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/promoitems";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/promoitem";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, PATH_PROMOITEMS, PROMOS);
        sURIMatcher.addURI(AUTHORITY, PATH_PROMOITEMS + "/#", PROMO_ID);
    }

    @Override
    public boolean onCreate() {
        database = new FreeDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case PROMOS:
                builder.setTables(PromoItemTable.TABLE_PROMOITEM);
                break;
            case PROMO_ID:
                builder.setTables(PromoItemTable.TABLE_PROMOITEM);
                builder.appendWhere(PromoItemTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();

        long id;
        String path;

        switch (uriType) {
            case PROMOS:
                id = db.insert(PromoItemTable.TABLE_PROMOITEM, null, values);
                path = PATH_PROMOITEMS;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(path + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted;

        String id;

        switch (uriType) {
            case PROMOS:
                rowsDeleted = db.delete(PromoItemTable.TABLE_PROMOITEM, selection, selectionArgs);
                break;
            case PROMO_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(PromoItemTable.TABLE_PROMOITEM, PromoItemTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = db.delete(PromoItemTable.TABLE_PROMOITEM, PromoItemTable.COLUMN_ID + "=" + id + " and "
                            + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated;

        String id;

        switch (uriType) {
            case PROMOS:
                rowsUpdated = db.update(PromoItemTable.TABLE_PROMOITEM, values, selection, selectionArgs);
                break;
            case PROMO_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(PromoItemTable.TABLE_PROMOITEM, values, PromoItemTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(PromoItemTable.TABLE_PROMOITEM, values, PromoItemTable.COLUMN_ID + "=" + id +
                            " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
