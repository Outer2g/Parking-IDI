package com.example.alexoses.parking.Persistencia;

import android.provider.BaseColumns;

/**
 * Created by Alex on 02/01/2016.
 */
public class LogContract {

    //inner class that defines the table contents:
    public static abstract class LogEntry implements BaseColumns {
        public static final String LOGE_TABLE_NAME = "logE";
        public static final String COLUMN_NAME_CAR_ID ="carid";
        public static final String COLUMN_NAME_DATE = "datein";
        public static final String COLUMN_NAME_E_S = "es";
        public static final String COLUMN_NAME_PREU ="preu";
    }
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LogEntry.LOGE_TABLE_NAME + " (" +
                    LogEntry._ID + " INTEGER PRIMARY KEY," +
                    LogEntry.COLUMN_NAME_CAR_ID + TEXT_TYPE + COMMA_SEP +
                    LogEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    LogEntry.COLUMN_NAME_E_S + TEXT_TYPE + COMMA_SEP +
                    LogEntry.COLUMN_NAME_PREU + " DOUBLE " +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LogEntry.LOGE_TABLE_NAME;
}
