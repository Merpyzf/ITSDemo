package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/14.
 */

public class RoadQueryFragment extends Fragment {


    private View view;

    private TextView road_1;
    private TextView road_2;
    private TextView road_3;

    private Switch switch_1;
    private Switch switch_2;
    private Switch switch_3;

    private String[] roadStatus = new String[]{"顺畅","良好","拥挤","拥堵","爆表"};

    private Timer timer =null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_road_query,container,false);

        iniUI();

        iniEvent();
        return view;

    }

    private void iniEvent() {
        getStatus();

        switch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!switch_1.isChecked()) return;
                new HttpAsyncUtils(Common.SET_CAR_MOVE, "{\"CarId\":1, \"CarAction\":\"Stop\"}") {
                    @Override
                    public void success(String data) {
                        Toast.makeText(getContext(), "小车已停止", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {

                    }
                };
            }
        });
        switch_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!switch_2.isChecked()) return;
                new HttpAsyncUtils(Common.SET_CAR_MOVE, "{\"CarId\":2, \"CarAction\":\"Stop\"}") {
                    @Override
                    public void success(String data) {
                        Toast.makeText(getContext(), "小车已停止", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {

                    }
                };
            }
        });
        switch_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!switch_3.isChecked()) return;
                new HttpAsyncUtils(Common.SET_CAR_MOVE, "{\"CarId\":3, \"CarAction\":\"Stop\"}") {
                    @Override
                    public void success(String data) {
                        Toast.makeText(getContext(), "小车已停止", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {

                    }
                };
            }
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getStatus();
            }
        };
        timer = new Timer();
        timer.schedule(task,0,5000);
    }

    private void iniUI() {
        road_1 = (TextView) view.findViewById(R.id.road_query_1);
        road_2 = (TextView) view.findViewById(R.id.road_query_2);
        road_3 = (TextView) view.findViewById(R.id.road_query_3);

        switch_1 = (Switch) view.findViewById(R.id.road_query_switch_1);
        switch_2 = (Switch) view.findViewById(R.id.road_query_switch_2);
        switch_3 = (Switch) view.findViewById(R.id.road_query_switch_3);
    }

    private void getStatus(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<4;i++){
                    final int finalI = i;
                    new HttpAsyncUtils(Common.GET_ROAD_STATUS, "{\"RoadId\":"+ finalI +",\"UserName\":\"Z0004\"}") {
                        @Override
                        public void success(String data) {
                            try {
                                JSONObject obj = new JSONObject(data);
                                String status = obj.getString("Balance");

                                switch (finalI){
                                    case 1:
                                        road_1.setText("1号道路：" + roadStatus[Integer.valueOf(status)]);
                                        break;
                                    case 2:
                                        road_2.setText("2号道路：" + roadStatus[Integer.valueOf(status)]);
                                        break;
                                    case 3:
                                        road_3.setText("3号道路：" + roadStatus[Integer.valueOf(status)]);
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void error() {

                        }
                    };

                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        if(timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }
}
