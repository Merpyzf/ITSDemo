package com.example.administrator.itsdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.domain.Value;

import java.util.List;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class MyListAdapter extends BaseAdapter
{
    private Context context;
    private List<Value> values;
    private int limit;
    private String name;

    public MyListAdapter(Context context, List<Value> values, String name, int limit)
    {
        this.context = context;
        this.values = values;
        this.limit = limit;
        this.name = name;
    }

    @Override
    public int getCount()
    {
        return values.size();
    }

    @Override
    public Object getItem(int position)
    {
        return values.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = View.inflate(context, R.layout.item_list_sensor, null);

        TextView sensor = (TextView) convertView.findViewById(R.id.item_tv_sensor);
        TextView data = (TextView) convertView.findViewById(R.id.item_tv_data);
        TextView is = (TextView) convertView.findViewById(R.id.item_tv_is);
        TextView time = (TextView) convertView.findViewById(R.id.item_tv_time);

        Value value = values.get(position);
        sensor.setText(name);
        data.setText(value.getValue()+"");
        is.setText(value.getValue()<limit?"正常":"不正常");
        time.setText(value.getTime());
        return convertView;
    }
}
