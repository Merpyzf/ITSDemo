package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class LightMonitorFragment extends Fragment {

    private View view;

    private Switch aSwitch;

    private TextView show;

    private Button btn_query;

    private Timer timer = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_light_monitor,container,false);

        iniUI();
        iniEvent();
        return view;
    }

    private void iniEvent() {

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                start();
            }
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                start();
            }
        };

        timer = new Timer();
        timer.schedule(task,0,5000);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    timer.cancel();
                }
            }
        });
    }

    private void iniUI() {
        aSwitch = (Switch) view.findViewById(R.id.light_monitor_switch);
        show = (TextView) view.findViewById(R.id.light_monitor_show);
        btn_query = (Button) view.findViewById(R.id.light_monitor_query);
    }

    private void start(){
        new HttpAsyncUtils(Common.GET_SENSE_BY_NAME, "{\"SenseName\":\"LightIntensity\", " +
                "\"UserName\":\"Z0004\"}") {
            @Override
            public void success(String data) {
                try {
                    JSONObject obj = new JSONObject(data);
                    String value = obj.getString("LightIntensity");
                    if(Integer.valueOf(value) < 1000){
                        show.setText("当前值：" + value +"；光线太暗 已打开路灯");
                        new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1," +
                                "\"Action\":\"Close\", \"UserName\":\"Z0004\"}") {
                            @Override
                            public void success(String data) {
                                Toast.makeText(getContext(), "打开路灯成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void error() {

                            }
                        };
                    }else{
                        show.setText("当前值：" + value);
                    }

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
