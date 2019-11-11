package com.example.home.utility.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

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

    private boolean captured = false;

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

    public boolean insertReport(String location, String people, String sheltered, String description, Bitmap image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(REPORTS_COLUMN_LOCATION, location);
        cv.put(REPORTS_COLUMN_PEOPLE, people);
        cv.put(REPORTS_COLUMN_SHELTERED, sheltered);
        cv.put(REPORTS_COLUMN_DESCRIPTION, description);

        try{
            int check = image.getWidth(); // if no image was captured, this should throw an exception
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] b = baos.toByteArray();
            captured = true;
            cv.put(REPORTS_COLUMN_IMAGE, Base64.encode(b, Base64.DEFAULT));
            Log.i("SQL", "Detected that an image was captured...");
        }catch(Exception e){
            Log.i("SQL", "Detected that no image was captured...");
            captured = false;
            cv.put(REPORTS_COLUMN_IMAGE, "-");
        }


        db.insert(REPORTS_TABLE_NAME, null, cv);
        return true;
    }

    public String getLastEntry(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + REPORTS_TABLE_NAME +
                " WHERE " + REPORTS_COLUMN_ID +
                "= (SELECT MAX(" +REPORTS_COLUMN_ID+") FROM " + REPORTS_TABLE_NAME+");",
                null);

        CursorWindow cw = new CursorWindow("test", 5000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) cur;
        ac.setWindow(cw);
        try {
            ac.moveToNext();
        } catch (SQLiteBlobTooBigException expected) {
            Log.v(TAG, expected.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ac.getString(ac.getColumnIndex(REPORTS_COLUMN_ID)));
        sb.append("  ");
        sb.append(ac.getString(ac.getColumnIndex(REPORTS_COLUMN_LOCATION)));
        sb.append("  ");
        sb.append(ac.getString(ac.getColumnIndex(REPORTS_COLUMN_PEOPLE)));
        sb.append("  ");
        sb.append(ac.getString(ac.getColumnIndex(REPORTS_COLUMN_SHELTERED)));
        sb.append("  ");
        sb.append(ac.getString(ac.getColumnIndex(REPORTS_COLUMN_DESCRIPTION)));
        sb.append("  ");

        if(captured) {
            sb.append(Base64.encodeToString(ac.getBlob(ac.getColumnIndex(REPORTS_COLUMN_IMAGE)), Base64.DEFAULT));
        }else{
            sb.append(ac.getString(ac.getColumnIndex(REPORTS_COLUMN_IMAGE)));
        }

        return sb.toString();
    }
}
