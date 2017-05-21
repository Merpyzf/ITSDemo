package com.example.administrator.itsdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 春水碧于天 on 2017/5/12.
 */

public class DBHelper extends SQLiteOpenHelper {

    private String sql;

    public DBHelper(Context context, String name,String sql) {
        super(context, name, null, 2);
        this.sql = sql;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(sql);

    }
}
