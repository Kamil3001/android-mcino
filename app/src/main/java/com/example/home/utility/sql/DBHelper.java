package com.example.home.utility.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HOME";
    public static final String REPORTS_TABLE_NAME = "REPORTS";
    public static final String REPORTS_COLUMN_ID = "ID";
    public static final String REPORTS_COLUMN_LOCATION = "LOCATION";
    public static final String REPORTS_COLUMN_PEOPLE = "PEOPLE";
    public static final String REPORTS_COLUMN_SHELTERED = "SHELTERED";
    public static final String REPORTS_COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String REPORTS_COLUMN_IMAGE = "IMAGE";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + REPORTS_TABLE_NAME +
                        " (" + REPORTS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        REPORTS_COLUMN_LOCATION + " TEXT, " +
                        REPORTS_COLUMN_PEOPLE + " TEXT, " +
                        REPORTS_COLUMN_SHELTERED + " TEXT, " +
                        REPORTS_COLUMN_DESCRIPTION + " TEXT, " +
                        REPORTS_COLUMN_IMAGE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + REPORTS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertReport(String location, String people, String sheltered, String description, String image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(REPORTS_COLUMN_LOCATION, location);
        cv.put(REPORTS_COLUMN_PEOPLE, people);
        cv.put(REPORTS_COLUMN_SHELTERED, sheltered);
        cv.put(REPORTS_COLUMN_DESCRIPTION, description);
        cv.put(REPORTS_COLUMN_IMAGE, image);
        db.insert(REPORTS_TABLE_NAME, null, cv);
        return true;
    }
}
