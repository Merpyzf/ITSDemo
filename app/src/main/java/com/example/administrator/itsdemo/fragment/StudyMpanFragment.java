package com.example.administrator.itsdemo.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.itsdemo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/16.
 */

public class StudyMpanFragment extends Fragment{

    private View view;

    private LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragmemt_study_mpan,container,false);

        lineChart = (LineChart) view.findViewById(R.id.my_line_chat);

        iniEvent();
        return view;
    }

    private void iniEvent() {
        lineChart.setNoDataText("没有数据");
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(true);

        XAxis xAxis =  lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);//禁用竖轴
        xAxis.setTextSize(15);
        xAxis.setEnabled(true);



        ArrayList<String> xData = new ArrayList<>();
        ArrayList<Entry> yData = new ArrayList<>();
        for(int i=0;i<15;i++){
            yData.add(new Entry(i*10,i));
            xData.add("X"+i);
        }

        LineDataSet set = new LineDataSet(yData ,"数据一");

        ArrayList<LineDataSet> list = new ArrayList<>();
        list.add(set);

        LineData result =  new LineData(xData,set);

        lineChart.setData(result);

    }


}
