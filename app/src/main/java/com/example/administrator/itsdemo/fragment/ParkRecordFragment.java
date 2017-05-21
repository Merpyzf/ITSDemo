package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.adapter.ParkRecordAdapter;
import com.example.administrator.itsdemo.bean.ParkRecordBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ParkRecordFragment  extends Fragment{

    private View view;

    private ListView list;

    private Timer timer = null;

    private String data = "{\"RESULT\":\"S\",\"ERRMSG\":\"查询成功\",\"ROWS_DETAIL\":[{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-20 03:29:06\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-20 03:31:56\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-20 05:05:26\"},{\"Addr\":\"parkout\",\"Cost\":5,\"CarId\":1,\"Time\":\"2017-04-22 03:48:14\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-22 03:48:25\"},{\"Addr\":\"parkout\",\"Cost\":5,\"CarId\":1,\"Time\":\"2017-04-22 03:40:45\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-22 03:40:57\"},{\"Addr\":\"parkout\",\"Cost\":5,\"CarId\":1,\"Time\":\"2017-04-22 03:42:37\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-22 03:42:49\"},{\"Addr\":\"parkout\",\"Cost\":5,\"CarId\":1,\"Time\":\"2017-04-22 03:44:30\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-22 03:44:41\"},{\"Addr\":\"parkout\",\"Cost\":5,\"CarId\":1,\"Time\":\"2017-04-22 03:46:22\"},{\"Addr\":\"etcout\",\"Cost\":5,\"CarId\":0,\"Time\":\"2017-04-22 03:46:33\"},{\"Addr\":\"parkout\",\"Cost\":5,\"CarId\":1,\"Time\":\"2017-04-22 03:48:14\"}]}";

    private ArrayList<ParkRecordBean> beanList = new ArrayList<>();

    private ParkRecordAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_park_record,container,false);

        list = (ListView) view.findViewById(R.id.list_park_record);

        iniEvent();

        return view;
    }

    private void iniEvent() {
        try {
            JSONObject obj = new JSONObject(data);
            JSONArray arr = obj.getJSONArray("ROWS_DETAIL");
            ParkRecordBean bean = null;
            for(int i=0; i<arr.length();i++){
                JSONObject _obj = arr.getJSONObject(i);
                bean = new ParkRecordBean(_obj.getString("CarId"),_obj.getString("Addr"),_obj.getString("Cost"),_obj.getString("Time"));
                beanList.add(bean);
            }

            adapter = new ParkRecordAdapter(beanList);

            list.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
