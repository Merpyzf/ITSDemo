package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.example.administrator.itsdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */

public class BusQueryTwoFragment extends Fragment {

    private View view;

    private ExpandableListView expandableListView;

    private SimpleExpandableListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_bus_query_two,container,false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_bus_query);


        iniEvent();
        return view;
    }

    private void iniEvent() {

        ArrayList<HashMap<String,Object>> groupList = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        //group信息
        map.put("name","1号站台");
        groupList.add(map);

        map = new HashMap<>();
        map.put("name","2号站台");
        groupList.add(map);

        //分组信息
        ArrayList<HashMap<String,Object>> dataList = new ArrayList<>();
        ArrayList<ArrayList<HashMap<String,Object>>> childs = new ArrayList<>();//所有分组信息

        map = new HashMap<>();
        map.put("content","1号：距离站台："+(int)(Math.random()*10000)+"米");
        dataList.add(map);

        map.clear();
        map.put("content","2号：距离站台："+(int)(Math.random()*10000)+"米");
        dataList.add(map);
        childs.add(dataList);

        dataList.clear();
        map = new HashMap<>();
        map.put("content","1号：距离站台："+(int)(Math.random()*10000)+"米");
        dataList.add(map);

        map.clear();
        map.put("content","2号：距离站台："+(int)(Math.random()*10000)+"米");
        dataList.add(map);
        childs.add(dataList);


        adapter = new SimpleExpandableListAdapter(getContext(),groupList,android.R.layout.simple_expandable_list_item_1,
                new String[]{"name"},new int[]{android.R.id.text1}, childs,android.R.layout.simple_expandable_list_item_1,
                new String[]{"content"},new int[]{android.R.id.text1});
        expandableListView.setAdapter(adapter);
    }
}
