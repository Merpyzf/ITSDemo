package com.example.administrator.itsdemo.domain;

/**
 * Created by simple_soul on 2017/4/26.
 */

public class Sensor
{
    private String name;
    private int value;
    private int limit;

    public Sensor(String name, int value, int limit)
    {
        this.name = name;
        this.value = value;
        this.limit = limit;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }
}
