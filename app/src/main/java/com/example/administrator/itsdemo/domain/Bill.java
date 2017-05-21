package com.example.administrator.itsdemo.domain;

import java.util.Date;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class Bill
{
    private int num;
    private int car;
    private int money;
    private Date date;

    public Bill(int num, int car, int money, Date date)
    {
        this.num = num;
        this.car = car;
        this.money = money;
        this.date = date;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public int getCar()
    {
        return car;
    }

    public void setCar(int car)
    {
        this.car = car;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
