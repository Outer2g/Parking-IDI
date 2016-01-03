package com.example.alexoses.parking.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex on 02/01/2016.
 */
public class ParkingDb extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Parking.db";

    public ParkingDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LogContract.SQL_CREATE_ENTRIES);
        db.execSQL(ParkingContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LogContract.SQL_DELETE_ENTRIES);
        db.execSQL(ParkingContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void reset(SQLiteDatabase db){
        db.execSQL(LogContract.SQL_DELETE_ENTRIES);
        db.execSQL(ParkingContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
