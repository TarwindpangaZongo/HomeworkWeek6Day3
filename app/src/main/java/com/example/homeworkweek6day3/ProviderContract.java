package com.example.homeworkweek6day3;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ProviderContract {
    //Content authority and base URI
    public static final String CONTENT_AUTHORITY = "com.example.contentproviders";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Database base info
    public static final String DATABASE_NAME = "celebrity_db";
    public static final int DATABASE_VERSION = 1;
    //Table names
    public static final String TABLE_NAME_CELEBRITY = "celebrity_table";
    //Columns - Movies
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BIRTHDATE = "birthDate";
    public static final String COLUMN_OCCUPATION= "occupation";
    public static final String COLUMN_HOME_TOWN = "homeTown";
    public static final String COLUMN_RESIDENCE = "residence";
    public static final String COLUMN_PICTURE = "picture";


    public static final class celebrityEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME_CELEBRITY).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + TABLE_NAME_CELEBRITY;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + TABLE_NAME_CELEBRITY;

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
