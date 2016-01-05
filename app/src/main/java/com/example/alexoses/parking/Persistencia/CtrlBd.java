package com.example.alexoses.parking.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexoses.parking.domain.VehicleParking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.example.alexoses.parking.Persistencia.LogContract.LogEntry;
import static com.example.alexoses.parking.Persistencia.LogContract.SQL_CREATE_ENTRIES;
import static com.example.alexoses.parking.Persistencia.ParkingContract.ParkingEntry;

/**
 * Created by Alex on 02/01/2016.
 */
public class CtrlBd {
    private ParkingDb io;

    public CtrlBd(Context context){
        io = new ParkingDb(context);
    }
    // Parking state operations :
    public void insertCar (VehicleParking v,int spot){
        SQLiteDatabase db = io.getWritableDatabase();

        //creem nova entrada en el log
        ContentValues values = new ContentValues();
        values.put(LogEntry.COLUMN_NAME_CAR_ID,v.getNumberPlate());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        values.put(LogEntry.COLUMN_NAME_DATE_IN,sdf.format(v.getDataEntrada()));
        values.put(LogEntry.COLUMN_NAME_PREU,0);

        long logID = db.insert(LogEntry.LOGE_TABLE_NAME, LogEntry.COLUMN_NAME_DATE_OUT,values);

        values = new ContentValues();
        values.put(ParkingEntry.COLUMN_NAME_SPOT,spot);
        values.put(ParkingEntry.COLUMN_NAME_CAR_ID,v.getNumberPlate());
        values.put(ParkingEntry.COLUMN_NAME_DATE_IN,sdf.format(v.getDataEntrada()));
        values.put(ParkingEntry.COLUMN_NAME_LOG_ID,logID);

        db.insert(ParkingEntry.PARK_TABLE_NAME, null, values);
    }
    public HashMap<Integer,VehicleParking> getVehiclesPark(){
        Log.e("KE", SQL_CREATE_ENTRIES);
        HashMap<Integer,VehicleParking> ret = new HashMap<Integer, VehicleParking>();
        SQLiteDatabase db = io.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null) cursor = db.rawQuery("SELECT * FROM "+ ParkingEntry.PARK_TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Log.e("Vehicles: ","Spot: "+cursor.getInt(0) +
                        " Matricula: "+cursor.getString(1) + " Data: "+cursor.getString(2));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                try {
                    ret.put(cursor.getInt(0),new VehicleParking(cursor.getString(1),sdf.parse(cursor.getString(2))));
                } catch (ParseException e) {
                    Log.e("GETTING VEHICLES DB",e.getMessage());
                }
            }while(cursor.moveToNext());
        }
        return ret;
    }
    public void delCar (int spot,Date dataout,Double cost){
        SQLiteDatabase db = io.getWritableDatabase();
        Cursor cursor = null;
        if (db!=null) cursor = db.rawQuery("SELECT "+ ParkingEntry.COLUMN_NAME_LOG_ID +" FROM " +
                ParkingEntry.PARK_TABLE_NAME + " WHERE " + spot + " = " + ParkingEntry.COLUMN_NAME_SPOT,null);
        if(cursor.moveToFirst()){
            do{
                Log.e("Vehicles: ","ID: "+cursor.getInt(0));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String update = "UPDATE "+ LogEntry.LOGE_TABLE_NAME +
                        " SET " + LogEntry.COLUMN_NAME_DATE_OUT + " = " +"\"" +(sdf.format(dataout))+"\" ," + LogEntry.COLUMN_NAME_PREU + "=" + cost +
                        " WHERE " + LogEntry._ID +"=" + cursor.getInt(0);
                db.rawQuery(update,null);
            }while(cursor.moveToNext());
        }
        try {
            db.execSQL("DELETE FROM " + ParkingEntry.PARK_TABLE_NAME + " WHERE " + ParkingEntry.COLUMN_NAME_SPOT + " = " + spot + ";");
        }
        catch (Exception e){
            Log.e("DEL FROM DB",e.getMessage());
        }
    }
    //Log operations
    //debug utils
    public void reset(){
        SQLiteDatabase db = io.getWritableDatabase();
        io.reset(db);
    }

}
