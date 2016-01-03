package com.example.alexoses.parking.Persistencia;

import android.provider.BaseColumns;

/**
 * Created by Alex on 02/01/2016.
 */
public class LogContract {

    //inner class that defines the table contents:
    public static abstract class ParkingEntry implements BaseColumns {
        public static final String LOG_TABLE_NAME = "log";
        public static final String COLUMN_NAME_CAR_ID ="carid";
        public static final String COLUMN_NAME_DATE_IN = "datein";
        public static final String COLUMN_NAME_DATE_OUT = "dateout";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ParkingEntry.LOG_TABLE_NAME + " (" +
                    ParkingEntry._ID + " INTEGER PRIMARY KEY," +
                    ParkingEntry.COLUMN_NAME_CAR_ID + TEXT_TYPE + COMMA_SEP +
                    ParkingEntry.COLUMN_NAME_DATE_IN + TEXT_TYPE + COMMA_SEP +
                    ParkingEntry.COLUMN_NAME_DATE_OUT + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ParkingEntry.LOG_TABLE_NAME;
}
