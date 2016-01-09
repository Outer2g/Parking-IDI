package com.example.alexoses.parking.Persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 02/01/2016.
 */
public class ParkingDb extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Parking.db";
    public  Date dataCreacio;

    public ParkingDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            dataCreacio = sdf.parse("01-01-2000 00:00:00");
        } catch (ParseException e) {
            Log.e("kk",e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ParkingContract.SQL_CREATE_ENTRIES);
        db.execSQL(LogContract.SQL_CREATE_ENTRIES);
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
