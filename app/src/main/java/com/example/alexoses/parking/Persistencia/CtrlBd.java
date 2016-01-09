package com.example.alexoses.parking.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alexoses.parking.domain.VehicleParking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.alexoses.parking.Persistencia.LogContract.LogEntry;
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
        HashMap<Integer,VehicleParking> ret = new HashMap<Integer, VehicleParking>();
        SQLiteDatabase db = io.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null) cursor = db.rawQuery("SELECT * FROM "+ ParkingEntry.PARK_TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String update = "UPDATE "+ LogEntry.LOGE_TABLE_NAME +
                        " SET " + LogEntry.COLUMN_NAME_DATE_OUT + " = " +"\"" +(sdf.format(dataout))+"\" ," + LogEntry.COLUMN_NAME_PREU + "=" + cost +
                        " WHERE " + LogEntry._ID +"=" + cursor.getInt(0);
                Log.e("UPDATE: ",update);
                db.execSQL(update);
            }while(cursor.moveToNext());
        }
        try {
            db.execSQL("DELETE FROM " + ParkingEntry.PARK_TABLE_NAME + " WHERE " + ParkingEntry.COLUMN_NAME_SPOT + " = " + spot + ";");
        }
        catch (Exception e){
            Log.e("DEL FROM DB",e.getMessage());
        }
        db.close();
    }
    //Log operations
    public List<String> getLog(Date inici, Date fi){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dataIn = sdf.format(inici);
        String dataOut = sdf.format(fi);
        ArrayList<String> ret = new ArrayList();
        SQLiteDatabase db = io.getReadableDatabase();
        Cursor cursor = null;
        String j ="SELECT * FROM "+ LogEntry.LOGE_TABLE_NAME + " WHERE "+
                LogEntry.COLUMN_NAME_DATE_IN +" >= \"" + dataIn + "\" AND " + LogEntry.COLUMN_NAME_DATE_OUT +" <= \""+
                dataOut+"\""  ;
        if (db!=null) cursor = db.rawQuery(j,null);
        if(cursor.moveToFirst()){
            do{
                String aux = "Matricula: "+ cursor.getString(1)+" Data Entrada: "+ cursor.getString(2)+" Data Sortida: "+cursor.getString(3)+
                        " pagat: "+cursor.getDouble(4);
                ret.add(aux);
            }while(cursor.moveToNext());
        }
        return ret;
    }
    public double getRecaudacio(Date entrada,Date sortida){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Log.e("ARGS : ","data: "+sdf.format(entrada)+" out: "+sdf.format(sortida));
        double total = 0.0;
        SQLiteDatabase db = io.getReadableDatabase();
        Cursor cursor = null;
        String aux ="SELECT "+LogEntry.COLUMN_NAME_PREU+" FROM "+ LogEntry.LOGE_TABLE_NAME+ " WHERE " +
                LogEntry.COLUMN_NAME_DATE_IN +" >= \"" + (sdf.format(entrada)) + "\" AND " + LogEntry.COLUMN_NAME_DATE_OUT +" <= \""+
                (sdf.format(sortida))+"\"";
        //Log.e("RECAUDACIO OK",aux);
        if (db!=null) cursor = db.rawQuery(aux,null);
        if(cursor.moveToFirst()){
            do{
                total += cursor.getDouble(0);
            }while(cursor.moveToNext());
        }
        return total;
    }
    //Other
    public Date getDataCreacio(){
        return io.dataCreacio;
    }
    //debug utils
    public void reset(){
        SQLiteDatabase db = io.getWritableDatabase();
        io.reset(db);
    }

}
