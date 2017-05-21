package com.example.administrator.itsdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class T47Fragment extends Fragment {


    private TextView tv_light_status;
    private Button btn_manual;
    private Button btn_auto;
    private Button btn_open;
    private Button btn_close;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t47, container, false);

        tv_light_status = (TextView) view.findViewById(R.id.tv_light_status);
        btn_manual = (Button) view.findViewById(R.id.btn_manual);
        btn_auto = (Button)view.findViewById(R.id.btn_auto);
        btn_open = (Button) view.findViewById(R.id.btn_open);
        btn_close = (Button) view.findViewById(R.id.btn_close);


        getLightStatus();

        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setLightControl("Manual");


            }
        });


        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setLightControl("Auto");


            }
        });


        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setLighStatus("Open");


            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setLighStatus("Close");


            }
        });






        return view;
    }

    private void setLighStatus(String open) {

        Toast.makeText(getActivity(), "必须先将路灯模式设置为手动", Toast.LENGTH_SHORT).show();

        if (open.equals("Open")) {


            new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1,\"Action\":\"Open\", \"UserName\":\"Z0004\"}") {
                @Override
                public void success(String data) {

                    try {
                        JSONObject jsonObject = new JSONObject(data);

                        String errmsg = jsonObject.getString("ERRMSG");

                        if (errmsg.equals("成功")) {

                            btn_close.setBackgroundColor(Color.TRANSPARENT);
                            btn_open.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            getLightStatus();
                        } else {

                            Toast.makeText(getActivity(), "路灯打开失败", Toast.LENGTH_SHORT).show();


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void error() {

                }
            };


        } else if (open.equals("Close")) {


            new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1,\"Action\":\"Close\", \"UserName\":\"Z0004\"}") {
                @Override
                public void success(String data) {

                    try {
                        JSONObject jsonObject = new JSONObject(data);

                        String errmsg = jsonObject.getString("ERRMSG");

                        if (errmsg.equals("成功")) {

                            btn_open.setBackgroundColor(Color.TRANSPARENT);
                            btn_close.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            getLightStatus();
                        } else {

                            Toast.makeText(getActivity(), "路灯关闭失败", Toast.LENGTH_SHORT).show();


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

    /**
     * 设置路灯的控制模式
     */
    private void setLightControl(String action) {


        if(action.equals("Manual")){
            new HttpAsyncUtils(Common.SET_ROAD_LIGHT_CONTROL_MODE, "{\"ControlMode\":\"Manual\", \"UserName\":\"Z0004\"}") {
                @Override
                public void success(String data) {


                    try {
                        JSONObject jb = new JSONObject(data);

                        String errmsg = jb.getString("ERRMSG");

                        if(errmsg.equals("成功")){

                            Toast.makeText(getActivity(),"设置手动模式成功",Toast.LENGTH_SHORT).show();

                            btn_auto.setBackgroundColor(Color.TRANSPARENT);
                            btn_manual.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                        }else {


                            Toast.makeText(getActivity(),"设置手动模式失败",Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void error() {

                }
            };



        }else if(action.equals("Auto")){


            new HttpAsyncUtils(Common.SET_ROAD_LIGHT_CONTROL_MODE, "{\"ControlMode\":\"Auto\", \"UserName\":\"Z0004\"}") {
                @Override
                public void success(String data) {

                    try {
                        JSONObject jb = new JSONObject(data);

                        String errmsg = jb.getString("ERRMSG");

                        if(errmsg.equals("成功")){

                            Toast.makeText(getActivity(),"设置自动模式成功",Toast.LENGTH_SHORT).show();

                            btn_manual.setBackgroundColor(Color.TRANSPARENT);
                            btn_auto.setBackgroundColor(getResources().getColor(R.color.colorPrimary));




                        }else {


                            Toast.makeText(getActivity(),"设置自动模式失败",Toast.LENGTH_SHORT).show();
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


    /**
     * 获取路灯的开闭状态
     */
    private void getLightStatus() {


        new HttpAsyncUtils(Common.GET_ROAD_LIGHT_STATUS, "{\"RoadLightId\":1, \"UserName\":\"Z0004\"}") {
            @Override
            public void success(String data) {

                try {
                    JSONObject jb=  new JSONObject(data);

                    String lightStatus = jb.getString("Status");


                    tv_light_status.setText(lightStatus);

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
