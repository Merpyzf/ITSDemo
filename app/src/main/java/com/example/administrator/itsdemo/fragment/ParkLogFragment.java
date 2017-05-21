package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.itsdemo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ParkLogFragment extends Fragment {

    private Button btn_desc;

    private Button btn_asc;

    private ListView list;

    private View view;

    private SimpleAdapter adapter;

    private ArrayList<HashMap<String,Object>> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prak_log,container,false);

        iniUI();

        iniEvent();
        return view;
    }

    private void iniEvent() {
        getData(1);

        btn_asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                getData(1);
            }
        });
        btn_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.clear();
                getData(2);
            }
        });

    }

    private void iniUI() {
        btn_asc = (Button) view.findViewById(R.id.park_log_asc);
        btn_desc = (Button) view.findViewById(R.id.park_log_desc);
        list = (ListView) view.findViewById(R.id.list_park_log);
    }

    private void getData(final int type){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> map = new HashMap<>();
                map.put("number",1);
                map.put("startTime","2014-12-25 01:28:21");
                map.put("endTime","2014-12-25 01:28:21");
                map.put("money",20);
                dataList.add(map);


                map = new HashMap<>();
                map.put("number",2);
                map.put("startTime","2014-12-25 01:28:21");
                map.put("endTime","2014-12-25 01:28:21");
                map.put("money",30);
                dataList.add(map);

                map = new HashMap<>();
                map.put("number",3);
                map.put("startTime","2014-12-25 01:28:21");
                map.put("endTime","2014-12-25 01:28:21");
                map.put("money",40);
                dataList.add(map);

                map = new HashMap<>();
                map.put("number",4);
                map.put("startTime","2014-12-25 01:28:21");
                map.put("endTime","2014-12-25 01:28:21");
                map.put("money",99);
                dataList.add(map);

                if(type == 1){
                    //升序
                    for(int i=0;i<4;i++){

                        for(int j=0;j<4;j++){
                            HashMap<String,Object> map_1 = dataList.get(i);

                            HashMap<String,Object> map_2 = dataList.get(j);

                            int code_1 = (int) map_1.get("money");
                            int code_2 = (int) map_2.get("money");

                            if(code_1 < code_2){
                                dataList.set(i,map_2);
                                dataList.set(j,map_1);
                            }
                        }
                    }
                }
                if(type == 2){
                    //升序
                    for(int i=0;i<4;i++){

                        for(int j=0;j<4;j++){
                            HashMap<String,Object> map_1 = dataList.get(i);

                            HashMap<String,Object> map_2 = dataList.get(j);

                            int code_1 = (int) map_1.get("money");
                            int code_2 = (int) map_2.get("money");

                            if(code_1 > code_2){
                                dataList.set(i,map_2);
                                dataList.set(j,map_1);
                            }
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SimpleAdapter(getContext(),dataList,R.layout.item_park_log,new String[]{
                                "number","startTime","endTime","money"
                        },new int[]{R.id.item_park_log_number,R.id.item_park_log_startTime,R.id.item_park_log_endTime,R.id.item_park_log_money});
                        list.setAdapter(adapter);
                    }
                });


            }
        }).start();
    }

}
