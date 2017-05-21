package com.example.administrator.itsdemo.domain;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class Road
{
    private int road;
    private int red;
    private int green;
    private int yellow;

    public Road(int road, int red, int green, int yellow)
    {
        this.road = road;
        this.red = red;
        this.green = green;
        this.yellow = yellow;
    }

    public int getRoad()
    {
        return road;
    }

    public void setRoad(int road)
    {
        this.road = road;
    }

    public int getRed()
    {
        return red;
    }

    public void setRed(int red)
    {
        this.red = red;
    }

    public int getGreen()
    {
        return green;
    }

    public void setGreen(int green)
    {
        this.green = green;
    }

    public int getYellow()
    {
        return yellow;
    }

    public void setYellow(int yellow)
    {
        this.yellow = yellow;
    }
}
