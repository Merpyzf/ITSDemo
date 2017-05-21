package com.example.administrator.itsdemo.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.DBHelper;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple  {@link Fragment} subclass.
 */
public class T37Fragment extends Fragment {

    private int BusMaxCapacity = 13; //公交车最大可容纳人数
    private int BusId = 1; //当前监控的公交车id
    private NotificationManager notifyManager;
    private SQLiteDatabase mWrite;
    private LineChart mChart;
    private LineDataSet dataSet;
    private LineData lineData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t37, container, false);

        mChart = (LineChart) view.findViewById(R.id.lineChart1);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        entries.add(new Entry(0, 0));

        ArrayList<String> xvalues = new ArrayList<>();
        xvalues.add("0");
        xvalues.add("10s");


        dataSet = new LineDataSet(entries, "test");
        lineData = new LineData(xvalues, dataSet);

        mChart.setMaxVisibleValueCount(5);
        mChart.setData(lineData);
        mChart.notifyDataSetChanged();



        XAxis xAxis = mChart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis axisLeft = mChart.getAxisLeft();

        YAxis axisRight = mChart.getAxisRight();

        axisRight.setEnabled(false);




        axisLeft.setAxisMinValue(0);
        axisLeft.setAxisMaxValue(100);

//        addPoint();

        Timer timer = new Timer();
        String sql = "create table tab_overload (_id int auto_increment primary key,bus_id int,overload_num int,overload_date)";
        DBHelper dbHelper = new DBHelper(getContext(), Common.DB_NAME, sql);

        mWrite = dbHelper.getWritableDatabase();


        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                getBusCapacity();


            }
        }, 0, 10 * 1000);

        return view;
    }

    private void addPoint() {


        LineData lineData = mChart.getLineData();


        if (lineData != null) {

            Log.i("wk", "lineData不为空，执行到了");

            LineDataSet dataSet = lineData.getDataSetByIndex(0);

            if (dataSet == null) {

                Log.i("wk", "dataSet==null");

            }


        }


    }

    private int count = 1;
    private void getBusCapacity() {


        new HttpAsyncUtils(Common.GET_BUS_CAPACITY, "{\"BusId\":" + BusId + ",\"UserName\":\"Z0004\"}") {
            @Override
            public void success(String data) {

                Log.i("wk", "公交车容量:" + data);

                try {
                    JSONObject jsonObject = new JSONObject(data);

                    int currentCapacity = jsonObject.getInt("BusCapacity");


                    lineData.addXValue(count*10+"s");
                    dataSet.addEntry(new Entry(currentCapacity,count));

                    mChart.notifyDataSetChanged();


                    mChart.setVisibleXRange(6);
                    mChart.moveViewToX(dataSet.getEntryCount()-1);

                    count++;


                    if (currentCapacity > BusMaxCapacity) {

                        //通知栏显示通知

                        //将超载的人数，公交车id,时间记录到数据库

                        recordOverLoadInfo(currentCapacity);


                        //添加图表，动态添加当前公交车的人数


                        showNotification(currentCapacity);





                    } else {

                        //取消通知栏的通知显示


                        if (notifyManager != null) {


                            notifyManager.cancel(1);

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

    //记录超载信息
    private void recordOverLoadInfo(int currentCapacity) {

        ContentValues values = new ContentValues();

        values.put("bus_id", 1);
        values.put("overload_num", currentCapacity - BusMaxCapacity);

        Date date = new Date(System.currentTimeMillis());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        String dateStr = format.format(date);

        values.put("overload_date", dateStr);

        mWrite.insert("tab_overload", null, values);


    }


    public void showNotification(int currentCapacity) {


        //获取NotificationManager实例
        notifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
                //设置小图标
                .setSmallIcon(R.drawable.ic_action_name)
                //设置通知标题
                .setContentTitle("1号公交车超载信息")
                //设置通知内容
                .setContentText("超载人数:" + (currentCapacity - BusMaxCapacity));

        Notification notification = builder.build();

        notifyManager.notify(1, notification);

    }


}
