package com.example.homeworkweek6day3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ContentProviders extends ContentProvider {
    //Data persistence object
    CelebrityDbHelper celebrityDbHelper;

    //paths switch values
    public static final int CELEBRITY = 500;
    public static final int CELEBRITY_ID = 501;

    //URI Matcher
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        String content = ProviderContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, ProviderContract.TABLE_NAME_CELEBRITY, CELEBRITY);
        matcher.addURI(content, ProviderContract.TABLE_NAME_CELEBRITY + "/#", CELEBRITY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        celebrityDbHelper = new CelebrityDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase();
        Cursor retCursor;
        long _id;
        switch(sUriMatcher.match(uri)){
            case CELEBRITY:
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_CELEBRITY,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CELEBRITY_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        ProviderContract.TABLE_NAME_CELEBRITY,
                        projection,
                        ProviderContract.COLUMN_NAME + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set the notification URI for the cursor to the one passed into the function. This
        // causes the cursor to register a content observer to watch for changes that happen to
        // this URI and any of it's descendants. By descendants, we mean any URI that begins
        // with this path.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMatcher.match(uri)){
            case CELEBRITY:
                return ProviderContract.celebrityEntry.CONTENT_TYPE;
            case CELEBRITY_ID:
                return ProviderContract.celebrityEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        switch(sUriMatcher.match(uri)){
            case CELEBRITY:
                _id = db.insert(ProviderContract.TABLE_NAME_CELEBRITY, null, values);
                if(_id > 0){
                    returnUri = ProviderContract.celebrityEntry.buildMovieUri(_id);
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Use this on the URI passed into the function to notify any observers that the uri has
        // changed.
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch(sUriMatcher.match(uri)) {
            case CELEBRITY:
                rowsDeleted = db.delete(ProviderContract.TABLE_NAME_CELEBRITY, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = celebrityDbHelper.getWritableDatabase();
        int rows;

        switch(sUriMatcher.match(uri)){
             case CELEBRITY:
                rows = db.update(ProviderContract.TABLE_NAME_CELEBRITY, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }
}
