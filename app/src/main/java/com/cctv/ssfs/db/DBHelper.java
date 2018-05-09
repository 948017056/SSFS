package com.cctv.ssfs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qi on 2018/3/27.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "recording.db";
    public static String TABLE_NAME = "records";
    private static Integer versionVERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, versionVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table TABLE_NAME" +
                "(id integer primary key autoincrement,name varchar,code varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}
