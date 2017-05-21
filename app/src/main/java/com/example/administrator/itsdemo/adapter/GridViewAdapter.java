package com.example.administrator.itsdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.domain.Sensor;

import java.util.List;

/**
 * Created by simple_soul on 2017/4/26.
 */

public class GridViewAdapter extends BaseAdapter
{
    private Context context;
    private List<Sensor> data;

    public GridViewAdapter(Context context, List<Sensor> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            convertView = View.inflate(context, R.layout.item_sensor, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.item_tv_name);
            viewHolder.type = (TextView) convertView.findViewById(R.id.item_tv_type);
            viewHolder.num = (TextView) convertView.findViewById(R.id.item_tv_num);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.item_ll);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sensor sensor = data.get(position);
        viewHolder.name.setText(sensor.getName());
        viewHolder.num.setText(sensor.getValue()+"");
        if(sensor.getValue() >sensor.getLimit())
        {
            viewHolder.layout.setBackgroundColor(Color.RED);
        }
        else
        {
            viewHolder.layout.setBackgroundColor(Color.GREEN);
        }
        return convertView;
    }

    class ViewHolder
    {
        public TextView name, type, num;
        public LinearLayout layout;
    }
}
