package com.example.administrator.itsdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by simple_soul on 2017/4/26.
 */

public class DBOpenHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "sensor";

    public static final String TABLE_NAME = "parks";
    public static final String MAIN_TABLE = "main";


    public DBOpenHelper(Context context, String name)
    {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists " + TABLE_NAME +
                "(_id integer primary key autoincrement, time DATETIME DEFAULT CURRENT_TIMESTAMP, pm25 integer, " +
                "co2 integer, temperature integer, LightIntensity integer, humidity integer)");

        db.execSQL("create table if not exists " + MAIN_TABLE +
                "(_id integer primary key autoincrement, time DATETIME DEFAULT CURRENT_TIMESTAMP, pm25 integer, " +
                "co2 integer, temperature integer, LightIntensity integer, humidity integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
