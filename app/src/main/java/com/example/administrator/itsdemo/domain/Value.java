package com.example.administrator.itsdemo.domain;

/**
 * Created by simple_soul on 2017/5/3.
 */

public class Value
{
    private int value;
    private String time;


    public Value(int value, String time)
    {
        this.value = value;
        this.time = time;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }


}
