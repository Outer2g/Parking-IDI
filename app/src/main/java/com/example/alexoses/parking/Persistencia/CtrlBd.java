package com.example.alexoses.parking.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexoses.parking.domain.VehicleParking;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 02/01/2016.
 */
public class CtrlBd {
    private ParkingDb io;
    GregorianCalendar c  = new GregorianCalendar(2015,03,28);
    public CtrlBd(Context context){
        io = new ParkingDb(context);
    }
    public void insertCar (VehicleParking v){
        SQLiteDatabase db = io.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LogContract.ParkingEntry.COLUMN_NAME_CAR_ID,v.getNumberPlate());
        values.put(LogContract.ParkingEntry.COLUMN_NAME_DATE_IN,"test");
        long newIDrow;
        newIDrow = db.insert(LogContract.ParkingEntry.LOG_TABLE_NAME, LogContract.ParkingEntry.COLUMN_NAME_DATE_OUT,values);
    }
    public void getVehiclesLog(){
        SQLiteDatabase db = io.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) cursor = db.rawQuery("SELECT * FROM " + LogContract.ParkingEntry.LOG_TABLE_NAME,null);
        if (cursor.moveToFirst()){
            do {
                Log.e("Vehicles: ","Matricula: "+cursor.getString(1) + " Data: "+cursor.getString(2));
            } while (cursor.moveToNext());
        }
        db.close();
    }
    public void reset(){
        SQLiteDatabase db = io.getWritableDatabase();
        io.reset(db);
    }

}
