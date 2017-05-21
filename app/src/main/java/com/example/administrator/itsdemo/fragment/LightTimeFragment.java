package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/15.
 */

public class LightTimeFragment extends Fragment {

    private View view;

    private ListView list;

    private SimpleAdapter adapter;

    private Timer timer = null;

    private ArrayList<HashMap<String, Object>> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_light_time, container, false);

        iniUI();
        iniEvent();
        return view;
    }

    private void iniEvent() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                start();
            }
        };
        timer = new Timer();
        timer.schedule(task,0,5000);
    }

    private void iniUI() {
        list = (ListView) view.findViewById(R.id.list_light_time);
    }

    private void start() {


        dataList.clear();
        for (int i = 1; i < 4; i++) {
            final int finalI = i;
            new HttpAsyncUtils(Common.GET_ROAD_STATUS, "{\"RoadId\":" + finalI + ",\"UserName\":\"Z0004\"}") {
                @Override
                public void success(String data) {
                    try {
                        JSONObject obj = new JSONObject(data);
                        int value = obj.getInt("Balance");
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("road", finalI);
                        map.put("crowd", "" + value);
                        map.put("id", finalI);
                        map.put("origin", "20");

                        if (value > 2) {
                            map.put("current", 50);
                        } else {
                            map.put("current", 20);
                        }


                        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

                        map.put("time", format.format(new Date(System.currentTimeMillis())));

                        dataList.add(map);

                        if (finalI == 3) {
                            adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_light_time, new String[]{
                                    "road", "crowd", "id", "origin", "current", "time"
                            }, new int[]{R.id.item_light_time_road, R.id.item_light_time_crowd,
                                    R.id.item_light_time_light_id, R.id.item_light_time_light_origin,
                                    R.id.item_light_time_light_current, R.id.item_light_time_modify_time
                            });

                            list.setAdapter(adapter);

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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
