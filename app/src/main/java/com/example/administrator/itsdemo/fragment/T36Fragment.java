package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


public class T36Fragment extends Fragment implements View.OnClickListener {

    private Button btn_manual;
    private Button btn_auto;
    private Button btn_open;
    private Button btn_close;
    private TextView tv_show_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t36, container, false);

        initUI(view);


        return view;
    }

    private void initUI(View view) {

        btn_manual = (Button) view.findViewById(R.id.btn_manual);
        btn_auto = (Button) view.findViewById(R.id.btn_auto);
        btn_close = (Button) view.findViewById(R.id.btn_close);
        btn_open = (Button) view.findViewById(R.id.btn_open);
        tv_show_status = (TextView) view.findViewById(R.id.tv_show_status);


        btn_manual.setOnClickListener(this);
        btn_auto.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_open.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_manual:


                setControlMode("Manual");

                break;


            case R.id.btn_auto:
                setControlMode("Auto");

                break;


            case R.id.btn_open:

                setControlStatus("Open");


                break;

            case R.id.btn_close:


                setControlStatus("Close");

                break;


        }


    }

    private void setControlStatus(final String status) {


        new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1,\"Action\":\"" + status + "\", \"UserName\":\"Z0004\"}") {
            @Override
            public void success(String data) {

                try {
                    JSONObject jb = new JSONObject(data);
                    String errmsg = jb.getString("ERRMSG");

                    if (errmsg.equals("成功")) {


                        if(status.equals("Open")) {

                            Toast.makeText(getContext(), "1号路灯被开启", Toast.LENGTH_SHORT).show();

                        }else{

                            Toast.makeText(getContext(), "1号路灯被关闭", Toast.LENGTH_SHORT).show();


                        }

                        Timer timer = new Timer();


                        if(status.equals("Open")) {


                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {


                                    new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1,\"Action\":\"Close\", \"UserName\":\"Z0004\"}") {
                                        @Override
                                        public void success(String data) {

                                            JSONObject jb = null;
                                            try {
                                                jb = new JSONObject(data);
                                                String errmsg = jb.getString("ERRMSG");

                                                if (errmsg.equals("成功")) {

                                                    Toast.makeText(getContext(), "10s钟后自动关闭成功", Toast.LENGTH_SHORT).show();

                                                } else {

                                                    Toast.makeText(getContext(), "10s钟后自动关闭失败", Toast.LENGTH_SHORT).show();

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
                            }, 10 * 1000);
                        }


                    } else {


                        Toast.makeText(getContext(), "1号路开启失败", Toast.LENGTH_SHORT).show();


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

    private void setControlMode(final String action) {


        new HttpAsyncUtils(Common.SET_ROAD_LIGHT_CONTROL_MODE, "{\"ControlMode\":\"" + action + "\", \"UserName\":\"Z0004\"} ") {
            @Override
            public void success(String data) {


                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(data);
                    String errmsg = jsonObject.getString("ERRMSG");

                    Log.i("wk", "结果:" + errmsg);

                    if (errmsg.equals("成功")) {

                        if (action.equals("Manual")) {

                            tv_show_status.setText("当前路灯控制状态:  " + "手动控制");

                            btn_open.setEnabled(true);
                            btn_close.setEnabled(true);
                        } else {


                            tv_show_status.setText("当前路灯控制状态:  " + "自动控制");

                            btn_open.setEnabled(false);
                            btn_close.setEnabled(false);

                        }

                    } else {


                        Toast.makeText(getContext(), "设置失败", Toast.LENGTH_SHORT).show();

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
}
