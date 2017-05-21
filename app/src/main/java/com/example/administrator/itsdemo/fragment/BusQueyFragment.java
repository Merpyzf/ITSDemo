package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/12.
 */

public class BusQueyFragment extends Fragment {

    private View view;

    private TextView one_juli;
    private TextView one_time;
    private TextView two_juli;
    private TextView two_time;
    private TextView three_juli;
    private TextView three_time;
    private TextView four_juli;
    private TextView four_time;

    private Timer timer = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bus_query,container,false);
        iniUI();
        iniEvent();
        return view;
    }

    private void iniEvent() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        one_juli.setText("距离"+(int)(Math.random()*10000)+"米");
                        two_juli.setText("距离"+(int)(Math.random()*10000)+"米");
                        three_juli.setText("距离"+(int)(Math.random()*10000)+"米");
                        four_juli.setText("距离"+(int)(Math.random()*10000)+"米");

                        one_time.setText("剩余时间"+(int)(Math.random()*100) +"分钟");
                        two_time.setText("剩余时间"+(int)(Math.random()*100) +"分钟");
                        three_time.setText("剩余时间"+(int)(Math.random()*100) +"分钟");
                        four_time.setText("剩余时间"+(int)(Math.random()*100) +"分钟");
                    }
                });
            }
        };

        timer = new Timer();
        timer.schedule(task,0,5000);

    }

    @Override
    public void onDestroy() {
        if(timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }

    private void iniUI() {
        one_juli = (TextView) view.findViewById(R.id.bus_query_station_one_juli);
        one_time = (TextView) view.findViewById(R.id.bus_query_station_one_time);
        two_juli = (TextView) view.findViewById(R.id.bus_query_station_two_juli);
        two_time = (TextView) view.findViewById(R.id.bus_query_station_two_time);
        three_juli = (TextView) view.findViewById(R.id.bus_query_station_three_juli);
        three_time = (TextView) view.findViewById(R.id.bus_query_station_three_time);
        four_juli = (TextView) view.findViewById(R.id.bus_query_station_four_juli);
        four_time = (TextView) view.findViewById(R.id.bus_query_station_four_time);

    }
}
