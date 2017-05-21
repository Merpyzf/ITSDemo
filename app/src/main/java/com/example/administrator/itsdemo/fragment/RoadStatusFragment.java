package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * 道路状况查询
 */

public class RoadStatusFragment extends Fragment {

    private ListView list;

    private Button btn_desc;

    private Button btn_asc;

    private View view;

    private SimpleAdapter adapter;

    private ArrayList<HashMap<String, Object>> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_road_status, container, false);

        iniUI();
        iniEvent();
        return view;
    }

    private void iniEvent() {

        Toast.makeText(getContext(), "正在刷新数据", Toast.LENGTH_SHORT).show();
        getData(1);

        btn_asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "正在刷新数据", Toast.LENGTH_SHORT).show();
                dataList.clear();
                getData(1);
            }
        });

        btn_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "正在刷新数据", Toast.LENGTH_SHORT).show();
                dataList.clear();
                getData(2);
            }
        });
    }

    private void iniUI() {
        btn_asc = (Button) view.findViewById(R.id.road_status_asc);
        btn_desc = (Button) view.findViewById(R.id.road_status_desc);
        list = (ListView) view.findViewById(R.id.list_road_status);
    }

    /**
     * @param type 1：升序2：降序
     */
    private void getData(final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 6; i++) {

                    final int finalI = i;
                    new HttpAsyncUtils(Common.GET_ROAD_STATUS, "{\"RoadId\":" + finalI + ",\"UserName\":\"Z0004\"}") {
                        @Override
                        public void success(String data) {
                            try {

                                JSONObject obj = new JSONObject(data);
                                String status = obj.getString("Balance");

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("name", finalI + "号道路");

                                map.put("status", status);
                                map.put("color", "#fff");

                                dataList.add(map);

                                if (finalI != 5) return;


                                if (type == 1) {
                                    //升序
                                    for (int i = 0; i < 5; i++) {

                                        for(int j=0;j<5;j++){
                                            HashMap<String, Object> _map = dataList.get(i);
                                            HashMap<String, Object> _map_2 = dataList.get(j);
                                            String _status = (String) _map.get("status");
                                            String _status_2 = (String) _map_2.get("status");


                                            int code = Integer.valueOf(_status);
                                            int code2 = Integer.valueOf(_status_2);

                                            if(code < code2){
                                                HashMap<String,Object> tmp = _map_2;

                                                dataList.set(j,_map);

                                                dataList.set(i,tmp);
                                            }
                                        }
                                    }
                                }
                                if (type == 2) {
                                    //降序
                                    for (int i = 0; i < 5; i++) {

                                        for(int j=0;j<5;j++){
                                            HashMap<String, Object> _map = dataList.get(i);
                                            HashMap<String, Object> _map_2 = dataList.get(j);
                                            String _status = (String) _map.get("status");
                                            String _status_2 = (String) _map_2.get("status");


                                            int code = Integer.valueOf(_status);
                                            int code2 = Integer.valueOf(_status_2);

                                            if(code > code2){
                                                HashMap<String,Object> tmp = _map_2;

                                                dataList.set(j,_map);

                                                dataList.set(i,tmp);
                                            }
                                        }


                                    }

                                }
                                adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_road_status, new
                                        String[]{"name", "status", "color"},
                                        new int[]{R.id.item_road_status_name, R.id.item_road_status_crowd, R.id
                                                .item_road_status_color});
                                list.setAdapter(adapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void error() {

                        }
                    };

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }
}
