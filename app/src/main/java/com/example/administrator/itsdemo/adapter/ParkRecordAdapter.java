package com.example.administrator.itsdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.bean.ParkRecordBean;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ParkRecordAdapter extends BaseAdapter {

    private ArrayList<ParkRecordBean> beanList;

    public ParkRecordAdapter(ArrayList<ParkRecordBean> beanList) {
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        Holder holder = null;

        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_park_record,parent,false);
            holder = new Holder();
            holder.tmp_id = (TextView) view.findViewById(R.id.item_park_record_id);
            holder.tmp_type = (TextView) view.findViewById(R.id.item_park_record_type);
            holder.tmp_cost = (TextView) view.findViewById(R.id.item_park_record_money);
            holder.tmp_time = (TextView) view.findViewById(R.id.item_park_record_time);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (Holder) view.getTag();
        }
        ParkRecordBean bean = beanList.get(position);

        holder.tmp_id.setText(bean.getId());
        holder.tmp_type.setText(bean.getType());
        holder.tmp_cost.setText(bean.getMoney());
        holder.tmp_time.setText(bean.getTime());

        if("parkout".equals(bean.getType())){
            holder.tmp_type.setBackgroundResource(R.color.red);

        }

        return view;
    }


    class Holder{
        TextView tmp_id;
        TextView tmp_type;
        TextView tmp_cost;
        TextView tmp_time;
    }
}
