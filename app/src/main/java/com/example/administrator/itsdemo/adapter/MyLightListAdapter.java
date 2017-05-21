package com.example.administrator.itsdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.domain.Road;

import java.util.List;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class MyLightListAdapter extends BaseAdapter
{
    private Context context;
    private List<Road> roads;

    public MyLightListAdapter(Context context, List<Road> roads)
    {
        this.context = context;
        this.roads = roads;
    }

    @Override
    public int getCount()
    {
        return roads.size();
    }

    @Override
    public Object getItem(int position)
    {
        return roads.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = View.inflate(context, R.layout.item_list_light, null);

        TextView road = (TextView) convertView.findViewById(R.id.item_tv_road_light);
        TextView red = (TextView) convertView.findViewById(R.id.item_tv_red_light);
        TextView green = (TextView) convertView.findViewById(R.id.item_tv_green_light);
        TextView yellow = (TextView) convertView.findViewById(R.id.item_tv_yellow_light);

        Road road1 = roads.get(position);
        road.setText(road1.getRoad()+"");
        red.setText(road1.getRed()+"");
        green.setText(road1.getGreen()+"");
        yellow.setText(road1.getYellow()+"");

        return convertView;
    }
}
