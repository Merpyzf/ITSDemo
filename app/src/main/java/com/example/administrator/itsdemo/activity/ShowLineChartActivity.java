package com.example.administrator.itsdemo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.domain.Value;
import com.example.administrator.itsdemo.utils.DBOpenHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.itsdemo.Common.co2_limit;
import static com.example.administrator.itsdemo.Common.humidity_limit;
import static com.example.administrator.itsdemo.Common.light_limit;
import static com.example.administrator.itsdemo.Common.pm25_limit;
import static com.example.administrator.itsdemo.Common.temperature_limit;

public class ShowLineChartActivity extends AppCompatActivity
{



    private LineChart lineChart;
    private TextView title;
    private ImageView back;

    private String name;
    private List<Value> values;
    private int limit;
    private int colors[] = new int[5];
    private int cColors[] = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_line_chart);

        initView();
        initData();



    }

    private void initView()
    {
        lineChart = (LineChart) findViewById(R.id.chart_line_sensor);
        title = (TextView) findViewById(R.id.titlebar_tv_title);
        back = (ImageView) findViewById(R.id.titlebar_image_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

//        ActionBar actionBar = getActionBar();
//        actionBar.hide();
    }

    private void initData()
    {
        cColors[0] = Color.GREEN;
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        switch (name)
        {
            case "pm25":
                limit = pm25_limit;
                break;

            case "co2":
                limit = co2_limit;
                break;

            case "LightIntensity":
                limit = light_limit;
                break;

            case "humidity":
                limit = humidity_limit;
                break;

            case "temperature":
                limit = temperature_limit;
                break;
        }
        getValues();

    }

    private void getValues()
    {
        values = new ArrayList<>();
        DBOpenHelper helper = new DBOpenHelper(this, DBOpenHelper.DB_NAME);
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(
                "select " + name + ", time from " + DBOpenHelper.MAIN_TABLE + " order by _id desc limit 5",
                null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                Log.i("main", "value="+cursor.getInt(cursor.getColumnIndex(name)));

                Value value = new Value(cursor.getInt(cursor.getColumnIndex(name)),
                        cursor.getString(cursor.getColumnIndex("time")).substring(10));
                values.add(value);
            }
        }
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = values.size()-1; i > -1; i--) {
            xValues.add(values.get(i).getTime());
        }

        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i = 0; i < values.size(); i++)
        {
            int value = values.get((values.size()-i-1)).getValue();
            addColor(i, value > limit ? true : false);
            yValue.add(new Entry(value, i));
        }
        initSingleLineChart(xValues, yValue);
    }

    private void addColor(int index, boolean isRed)
    {
        if(isRed)
        {
            colors[index] = Color.RED;
            cColors[index+1] = Color.RED;
        }
        else
        {
            colors[index] = Color.GREEN;
            cColors[index+1] = Color.GREEN;
        }
    }

    public void initSingleLineChart(ArrayList<String> xValues, ArrayList<Entry> yValue)
    {
        initDataStyle();
        //设置折线的样式
        LineDataSet dataSet = new LineDataSet(yValue, name);
        dataSet.setDrawValues(false);
        dataSet.setCircleColors(cColors);
        dataSet.setColors(colors);
//        dataSet.setValueFormatter(new PercentFormatter(new DecimalFormat("%").format()));

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);


        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(xValues, dataSets);
        //将数据插入
        lineChart.setData(lineData);

//        //设置动画效果
//        mLineChart.animateY(2000, Easing.EasingOption.Linear);
//        mLineChart.animateX(2000, Easing.EasingOption.Linear);
        lineChart.invalidate();
    }

    private void initDataStyle() {
        //设置图表是否支持触控操作
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(true);
        //设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        mLineChart.setMarkerView(mv);
        //设置折线的描述的样式（默认在图表的左下角）
//        Legend title = lineChart.getLegend();
//        title.setForm(Legend.LegendForm.LINE);
        //设置x轴的样式
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawGridLines(false);
        //设置是否显示x轴
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);

        //设置右边y轴的样式
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);

    }
}
