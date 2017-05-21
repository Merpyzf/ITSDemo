package com.example.administrator.itsdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.domain.Bill;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class MyBillListAdapter extends BaseAdapter
{
    private Context context;
    private List<Bill> bills;

    public MyBillListAdapter(Context context, List<Bill> bills)
    {
        this.context = context;
        this.bills = bills;
    }

    @Override
    public int getCount()
    {
        return bills.size();
    }

    @Override
    public Object getItem(int position)
    {
        return bills.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = View.inflate(context, R.layout.item_list_recharge, null);

        TextView num = (TextView) convertView.findViewById(R.id.item_tv_num_recharge);
        TextView car = (TextView) convertView.findViewById(R.id.item_tv_car_recharge);
        TextView money = (TextView) convertView.findViewById(R.id.item_tv_money_recharge);
        TextView user = (TextView) convertView.findViewById(R.id.item_tv_user_recharge);
        TextView date = (TextView) convertView.findViewById(R.id.item_tv_date_recharge);

        Bill bill = bills.get(position);
        num.setText(bill.getNum()+"");
        car.setText(bill.getCar()+"");
        money.setText(bill.getMoney()+"");
        user.setText("admin");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = format.format(bill.getDate());
        date.setText(s);

        return convertView;
    }
}
