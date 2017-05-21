package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * Created by Administrator on 2017/5/14.
 */

public class TravelAdviceFragment extends Fragment {

    private View view;

    private TextView show_wendu;
    private TextView show_shidu;
    private TextView show_pm;
    private TextView show_advice;

    private Timer timer = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_travel_advice,container,false);
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
        show_advice = (TextView) view.findViewById(R.id.travel_advice_advice);
        show_wendu = (TextView) view.findViewById(R.id.travel_advice_wendu);
        show_shidu = (TextView) view.findViewById(R.id.travel_advice_shidu);
        show_pm = (TextView) view.findViewById(R.id.travel_advice_pm);
    }

    private void start(){
        new HttpAsyncUtils(Common.GET_ALL_SENSOR, "{\"UserName\":\"Z0004\"}") {
            @Override
            public void success(String data) {
                try {
                    JSONObject obj = new JSONObject(data);
                    show_wendu.setText("温　度：" + obj.getString("temperature"));
                    show_shidu.setText("湿　度：" + obj.getString("humidity"));
                    show_pm.setText("pm2.5："+obj.getString("pm2.5"));

                    int pm = obj.getInt("pm2.5");

                    int co2 = obj.getInt("co2");

                    int wendu = obj.getInt("temperature");

                    int count = 0;

                    if(pm<100) count++;

                    if(co2<80) count++;

                    if(wendu <18 && wendu >15) count++;

                    if(count == 0) show_advice.setText("减少户外活动");
                    if(count == 1) show_advice.setText("可适当进行户外运动");
                    if(count == 2) show_advice.setText("可适当进行户外运动");
                    if(count == 3) show_advice.setText("快出去走走吧");

                    Log.i("TAG", "success: "+pm);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error() {

            }
        };
    }

    @Override
    public void onDestroy() {
        if(timer != null){
            timer.cancel();
        }
        super.onDestroy();
    }
}
