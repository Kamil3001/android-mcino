package com.example.home.utility.sql;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DBAssetHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "county_details";
    private static final int DATABASE_VERSION = 10;
    private static SQLiteDatabase db;
    private static SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    private String currentCounty;
    private Cursor cursor;

    private static Cursor allServices;


    private String [] SELECT = {"*"};
    private String TABLES = "county, services";
    private String INNER_JOIN = "services INNER JOIN county ON (services.County = county.County_id)";
    private String WHERE_DUBLIN = "services.County='FING' OR services.County='SOUDUB' OR services.County='DUBCITY' OR services.County='DUNRATH'";
    private String WHERE_COUNTY = "county_name = '";
    private String SORT_COUNTY = "services.County";

    public DBAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getReadableDatabase();
        setAllCounties();
    }


    public Cursor getColumns(String userCounty) {

        qb = new SQLiteQueryBuilder();

        String sqlWhere = WHERE_COUNTY+userCounty+"'";

        // Initially show all counties on startup - can change to use current location
        if(userCounty.equals("Select county…")){
            allServices.moveToFirst();
            return allServices;

        }
        else if(userCounty.equals("Dublin")){
            qb.setTables(INNER_JOIN);
            cursor = qb.query(db, SELECT,WHERE_DUBLIN, null, null, null, SORT_COUNTY);
        }
        else {

            qb.setTables(TABLES);

            cursor = qb.query(db, SELECT, sqlWhere, null, null, null, null);
            cursor.moveToFirst();

            qb.setTables(INNER_JOIN);
            sqlWhere = "County_id = '" + cursor.getString(cursor.getColumnIndex("County_id")) + "'";

            cursor = qb.query(db, SELECT, sqlWhere, null, null, null, SORT_COUNTY);
        }
        cursor.moveToFirst();
        return cursor;
    }

    private void setAllCounties(){
        qb.setTables(INNER_JOIN);
        allServices = qb.query(db, SELECT, null, null, null, null, SORT_COUNTY);
        allServices.moveToFirst();
    }
    public String getCountyByColour(String hexValue){
        System.out.println("InGCBC "+hexValue);
        if(!hexValue.equals("000000") && !hexValue.equals("ffffff")) {
            qb.setTables("county_colours");
            String[] SELECT_COUNTY = {"County"};
            String WHERE = "county_colours.Colour='" + hexValue + "'";
            cursor = qb.query(db, SELECT_COUNTY, WHERE, null, null, null, null);
            cursor.moveToFirst();
            if(cursor.getCount()>0) {
                currentCounty = cursor.getString(cursor.getColumnIndex("County"));
                System.out.println("COUNTYBYCOLOUR " + currentCounty);
            }
        }
        return currentCounty;
    }
    public Cursor getCountyFigures(String county){
        qb.setTables("figures");
        String WHERE = "figures.County='" + county +"'";
        cursor = qb.query(db, SELECT, WHERE, null, null, null, null);
        cursor.moveToFirst();
        return cursor;

    }
}
