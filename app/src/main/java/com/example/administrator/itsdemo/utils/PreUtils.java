package com.example.administrator.itsdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/11.
 */

public class PreUtils
{

    public static String getString(Context context, String key, String defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void setString(Context context, String key, String value)
    {
        SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        config.edit().putString(key, value).commit();
    }

    public static void setInt(Context context, String key, int value)
    {
        SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        config.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue)
    {
        SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        return config.getInt(key, defValue);
    }

    public static void setLong(Context context, String key, long value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return preferences.getLong(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }
}
