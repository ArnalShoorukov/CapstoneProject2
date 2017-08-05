package com.mosquefinder.arnal.prayertimesapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry;
import static com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry.TABLE_NAME;

/**
 * Created by arnal on 7/29/17.
 */

public class DuaContentProvider extends ContentProvider {

    public static final int DUA = 100;
    public static final int DUA_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DuaContract.AUTHORITY, DuaContract.PATH_DUA, DUA);
        uriMatcher.addURI(DuaContract.AUTHORITY, DuaContract.PATH_DUA + "/#", DUA_WITH_ID);

        return uriMatcher;
    }

    private DuaDbHelper duaDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        duaDbHelper = new DuaDbHelper(context);

        return true;    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase db = duaDbHelper.getReadableDatabase();

        // Cursor to hold the result of the query
        Cursor cursor;
        // Get the code that the URI matcher matches to
        int match = sUriMatcher.match(uri);
        switch (match) {
            case DUA:
                cursor = db.query(DuaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DUA_WITH_ID:
                selection = DuaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(DuaEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.i("ContentProvider", "Test Cursor inside Content Provider " + cursor);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DUA:
                return DuaEntry.CONTENT_LIST_TYPE;
            case DUA_WITH_ID:
                return DuaEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = duaDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri;

        switch (match){
            case DUA:
                //Inserting values into movie table
                long id = db.insert(TABLE_NAME, null, values);

                if (id > 0 ){

                    returnUri = ContentUris.withAppendedId(DuaContract.DuaEntry.CONTENT_URI, id);
                }else{
                    throw new android.database.SQLException("Failed to insert row into" + uri);
                }
                break;
            //Default case thorows an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase db = duaDbHelper.getWritableDatabase();

        // Number of rows deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case DUA:
                rowsDeleted = db.delete(DuaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DUA_WITH_ID:
                selection = DuaEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(DuaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // Notify all listeners if >= 1 row has been deleted
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (contentValues.containsKey(DuaEntry.COLUMN_DUA_ID)) {
            String duaId = contentValues.getAsString(DuaEntry.COLUMN_DUA_ID);
            if (duaId == null) {
                throw new IllegalArgumentException("Dua request an ID");
            }
        }
        // Do not update if there are no values to update
        if (contentValues.size() == 0) {
            return 0;
        }

        // Get writeable database
        SQLiteDatabase db = duaDbHelper.getWritableDatabase();

        // Number of rows updated
        int rowsUpdated = db.update(TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify all listeners if at least one row has been updated
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
