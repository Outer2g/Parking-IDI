package com.example.alexoses.parking.Persistencia;

/**
 * Created by Alex on 03/01/2016.
 */
public class ParkingContract {

    public static abstract class ParkingEntry {
        public static final String PARK_TABLE_NAME = "parkingState";
        public static final String COLUMN_NAME_SPOT = "spot";
        //matricula
        public static final String COLUMN_NAME_CAR_ID ="carid";
        public static final String COLUMN_NAME_DATE_IN = "datein";
        public static final String COLUMN_NAME_LOG_ID ="logid";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ParkingEntry.PARK_TABLE_NAME + " (" +
                    ParkingEntry.COLUMN_NAME_SPOT + " INTEGER PRIMARY KEY," +
                    ParkingEntry.COLUMN_NAME_CAR_ID + TEXT_TYPE + COMMA_SEP +
                    ParkingEntry.COLUMN_NAME_DATE_IN + TEXT_TYPE + COMMA_SEP +
                    ParkingEntry.COLUMN_NAME_LOG_ID + " INTEGER ,"+
                    "FOREIGN KEY ("+ ParkingEntry.COLUMN_NAME_LOG_ID+") REFERENCES "+
                    LogContract.LogEntry.LOGE_TABLE_NAME +"("+ LogContract.LogEntry._ID+")" +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ParkingEntry.PARK_TABLE_NAME;
}
