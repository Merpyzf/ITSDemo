package com.example.administrator.itsdemo.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class NoticeTightFragment extends Fragment {

    private View view;

    private Button btn_start;

    private Timer timer = null;

    private TextView show;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notice_light,container,false);

        btn_start = (Button) view.findViewById(R.id.notice_light_start);

        show = (TextView) view.findViewById(R.id.notice_light_value);
        iniEvent();

        return view;
    }

    private void iniEvent() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotice();
            }
        });
    }

    private void startNotice() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new HttpAsyncUtils(Common.GET_SENSE_BY_NAME, "{\"SenseName\":\"LightIntensity\", " +
                        "\"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {
                        try {
                            JSONObject obj = new JSONObject(data);
                            int value = obj.getInt("LightIntensity");
                            show.setText("当前光照强度："+ value);
                            if(value<1000){
                                NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification.Builder builder =  new Notification.Builder(getContext());
                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                builder.setContentText("请打开1,2,3路路灯");
                                builder.setContentTitle("请打开路灯");
                                builder.setWhen(System.currentTimeMillis());

                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    Notification notice = builder.build();
                                    manager.notify(0x1,notice);
                                }
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
