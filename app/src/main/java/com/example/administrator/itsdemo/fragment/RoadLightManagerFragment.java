package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/13.
 */

public class RoadLightManagerFragment extends Fragment {


    private Button btn_close;

    private Button btn_open;

    private Spinner spinner_status;

    private Spinner spinner_control;

    private Button btn_query;

    private View view;

    private TextView text_showStatus;

    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_road_light_manager,container,false);

        iniUI();

        iniEvent();
        return view;
    }

    private void iniEvent() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, new String[]{"1","2","3"});

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner_control.setAdapter(adapter);
        spinner_status.setAdapter(adapter);

        setStatus("1");


        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatus(spinner_status.getSelectedItem().toString());
            }
        });

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1,\"Action\":\"Open\", " +
                        "\"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {
                        Toast.makeText(getContext(), "打开成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {

                    }
                };
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncUtils(Common.SET_ROAD_LIGHT_STATUS_ACTION, "{\"RoadLightId\":1,\"Action\":\"Close\", " +
                        "\"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {
                        Toast.makeText(getContext(), "关闭成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {

                    }
                };
            }
        });
        //定时刷新

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setStatus(spinner_status.getSelectedItem().toString());
            }
        };

        timer = new Timer();
        timer.schedule(task,0,5000);

    }

    private void iniUI() {
        btn_close = (Button) view.findViewById(R.id.road_light_manager_close);
        btn_open = (Button) view.findViewById(R.id.road_light_manager_open);

        spinner_control = (Spinner) view.findViewById(R.id.spinner_road_light_manager_control);
        spinner_status = (Spinner) view.findViewById(R.id.spinner_road_light_manager_status);

        btn_query = (Button) view.findViewById(R.id.road_light_manager_query);
        text_showStatus = (TextView) view.findViewById(R.id.road_light_manager_show_status);
    }

    private void setStatus(final String lightId){
        new HttpAsyncUtils(Common.GET_ROAD_LIGHT_STATUS, "{\"RoadLightId\":"+lightId+", \"UserName\":\"Z0004\"}") {
            @Override
            public void success(String data) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(data);
                    String status = obj.getString("Status");
                    text_showStatus.setText("路灯"+lightId+"：" + status);
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
