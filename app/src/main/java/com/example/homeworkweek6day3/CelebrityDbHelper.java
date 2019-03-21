package com.example.homeworkweek6day3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static com.example.homeworkweek6day3.ProviderContract.COLUMN_BIRTHDATE;
import static com.example.homeworkweek6day3.ProviderContract.COLUMN_HOME_TOWN;
import static com.example.homeworkweek6day3.ProviderContract.COLUMN_NAME;
import static com.example.homeworkweek6day3.ProviderContract.COLUMN_OCCUPATION;
import static com.example.homeworkweek6day3.ProviderContract.COLUMN_PICTURE;
import static com.example.homeworkweek6day3.ProviderContract.COLUMN_RESIDENCE;
import static com.example.homeworkweek6day3.ProviderContract.DATABASE_NAME;
import static com.example.homeworkweek6day3.ProviderContract.DATABASE_VERSION;
import static com.example.homeworkweek6day3.ProviderContract.TABLE_NAME_CELEBRITY;

public class CelebrityDbHelper extends SQLiteOpenHelper {

    public CelebrityDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCelebrityTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    //Create celebrity Table
    private void createCelebrityTable(SQLiteDatabase db) {
        String createCelebrityTableQuery = "CREATE TABLE " + TABLE_NAME_CELEBRITY + " ("
                + COLUMN_NAME + " text, "
                + COLUMN_BIRTHDATE + " text, "
                + COLUMN_OCCUPATION+ " text, "
                + COLUMN_HOME_TOWN+ " text, "
                + COLUMN_RESIDENCE + " text,"
                + COLUMN_PICTURE + " text)";

        db.execSQL(createCelebrityTableQuery);

    }

    //insert into celebrity Table
    public long insertcelebrity(Celebrity celebrity) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME , celebrity.getName());
        contentValues.put(COLUMN_BIRTHDATE, celebrity.getBirthDate());
        contentValues.put(COLUMN_OCCUPATION, celebrity.getOccupation());
        contentValues.put(COLUMN_HOME_TOWN, celebrity.getHomeTown());
        contentValues.put(COLUMN_RESIDENCE, celebrity.getResidence());
        contentValues.put(COLUMN_PICTURE, celebrity.getPicture());

        return database.insert(TABLE_NAME_CELEBRITY, null, contentValues);
    }

    //update celebrity Table
    public long updateCelebrity(Celebrity celebrity) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String whereClause = " WHERE " + COLUMN_NAME + " = \"" + celebrity.getName() + "\"";

        contentValues.put(COLUMN_NAME , celebrity.getName());
        contentValues.put(COLUMN_BIRTHDATE, celebrity.getName());
        contentValues.put(COLUMN_OCCUPATION, celebrity.getOccupation());
        contentValues.put(COLUMN_HOME_TOWN, celebrity.getHomeTown());
        contentValues.put(COLUMN_RESIDENCE, celebrity.getResidence());
        contentValues.put(COLUMN_PICTURE, celebrity.getPicture());

        return database.update(TABLE_NAME_CELEBRITY, contentValues, whereClause, null);
    }

    //delete from celebrity Table
    public long deletecelebrity(String celebrityName) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_NAME_CELEBRITY,
                " WHERE " + COLUMN_NAME + " = \"" + celebrityName+ "\""
                ,null);
    }

    public ArrayList<Celebrity> retriveAllCelebrities() {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_CELEBRITY;
        ArrayList<Celebrity> celebritiesArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery(selectionQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String birthDate = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDATE));
                String occupation = cursor.getString(cursor.getColumnIndex(COLUMN_OCCUPATION));
                String homeTown = cursor.getString(cursor.getColumnIndex(COLUMN_HOME_TOWN));
                String residence = cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENCE));
                String picture = cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE));

                celebritiesArrayList.add(new Celebrity(name,birthDate,occupation,homeTown,residence,picture));
            } while(cursor.moveToNext());
        }

        cursor.close();
        return celebritiesArrayList;
    }

    //Retrieve one celebrity
    public Celebrity getSingleCelebrity(String celebrityName) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectionQuery = "SELECT * FROM " + TABLE_NAME_CELEBRITY
                + " WHERE " + COLUMN_NAME + " = \"" + celebrityName + "\"";
        Celebrity returncelebrity = null;

        Cursor cursor = database.rawQuery(selectionQuery, null);
        if(cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String birthDate = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDATE));
            String occupation = cursor.getString(cursor.getColumnIndex(COLUMN_OCCUPATION));
            String homeTown = cursor.getString(cursor.getColumnIndex(COLUMN_HOME_TOWN));
            String residence = cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENCE));
            String picture = cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE));

            returncelebrity = new Celebrity(name,birthDate,occupation,homeTown,residence,picture);
        }
        cursor.close();
        return returncelebrity;
    }
}
