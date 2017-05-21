package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/15.
 */

public class TrafficControlFragment extends Fragment {

    private View view;

    private TextView pm;

    private TextView trafficCar;

    private Timer timer = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_traffic_control,container,false);

        pm = (TextView) view.findViewById(R.id.traffic_control_pm);
        trafficCar = (TextView) view.findViewById(R.id.traffic_control_car);

        iniEvent();

        return view;
    }

    private void iniEvent() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new HttpAsyncUtils(Common.GET_SENSE_BY_NAME, "{\"SenseName\":\"pm2.5\", " +
                        "\"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {
                        try {
                            JSONObject obj = new JSONObject(data);
                            int value = obj.getInt("pm2.5");
                            pm.setText("当前PM2.5：" + value);

                            if(value < 100) trafficCar.setText("受管制车辆：无");
                            if(value > 100 && value<150) trafficCar.setText("受管制车辆：1号车");
                            if(value > 150 && value<200) trafficCar.setText("受管制车辆：1,2号车");
                            if(value > 150) trafficCar.setText("受管制车辆：全部小车");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void error() {

                    }
                };
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
}
