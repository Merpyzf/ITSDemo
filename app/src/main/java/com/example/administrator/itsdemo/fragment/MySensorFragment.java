package com.example.administrator.itsdemo.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.MainActivity;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.activity.ShowLineChartActivity;
import com.example.administrator.itsdemo.adapter.GridViewAdapter;
import com.example.administrator.itsdemo.domain.Sensor;
import com.example.administrator.itsdemo.domain.Value;
import com.example.administrator.itsdemo.utils.DBOpenHelper;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.administrator.itsdemo.Common.co2_limit;
import static com.example.administrator.itsdemo.Common.humidity_limit;
import static com.example.administrator.itsdemo.Common.light_limit;
import static com.example.administrator.itsdemo.Common.pm25_limit;
import static com.example.administrator.itsdemo.Common.temperature_limit;

/**
 * Created by simple_soul on 2017/5/11.
 */

public class MySensorFragment extends BaseFragment implements AdapterView.OnItemClickListener
{
    private GridView gridView;

    private List<Sensor> data;
    private Handler handler = new Handler();
    private GridViewAdapter adapter;
    private SQLiteDatabase writableDatabase;
    private ContentValues contentValues;
    private DBOpenHelper helper;
    private SQLiteDatabase readableDatabase;
    private List<Value> values;
    private int time = 0;
    private ContentValues cv;
    private NotificationManager manager;

    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_sensor, null);

        gridView = (GridView) view.findViewById(R.id.sensor_grid);

        gridView.setOnItemClickListener(this);

        return view;
    }

    public void initData()
    {
        manager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);

        helper = new DBOpenHelper(mActivity, DBOpenHelper.DB_NAME);
        readableDatabase = helper.getReadableDatabase();
        writableDatabase = helper.getWritableDatabase();
        handler.postDelayed(runnable, 1);
    }

    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            Log.i("main", "循环");
            handler.postDelayed(runnable, 5000);
            httpClient();
            time++;
            if(time == 12)
            {
                time=0;
                setAverage();
            }
        }
    };

    private void setAverage()
    {
        Cursor cursor = readableDatabase.rawQuery(
                "select * from " + DBOpenHelper.TABLE_NAME + " order by _id desc limit 12",
                null);
        int pm25 = 0;
        int co2 = 0;
        int LightIntensity = 0;
        int humidity = 0;
        int temperature = 0;
        if(cv == null)
            cv = new ContentValues();
        while (cursor.moveToNext())
        {
            pm25+=cursor.getInt(cursor.getColumnIndex("pm25"));
            co2+=cursor.getInt(cursor.getColumnIndex("co2"));
            LightIntensity+=cursor.getInt(cursor.getColumnIndex("LightIntensity"));
            humidity+=cursor.getInt(cursor.getColumnIndex("humidity"));
            temperature+=cursor.getInt(cursor.getColumnIndex("temperature"));
        }
        cv.put("pm25", pm25/12);
        cv.put("co2", co2/12);
        cv.put("LightIntensity", LightIntensity/12);
        cv.put("humidity", humidity/12);
        cv.put("temperature", temperature/12);

        writableDatabase.insert(DBOpenHelper.MAIN_TABLE, null, cv);
        cv.clear();
    }

    private void httpClient()
    {
        new HttpAsyncUtils(Common.GET_ALL_SENSOR, "{'UserName':'Z0004'}")
        {
            @Override
            public void success(String s)
            {
                try
                {
                    if (data == null)
                    {
                        data = new ArrayList<>();
                    }
                    else
                    {
                        data.clear();
                    }
                    Log.i("main", s);
                    JSONObject jsonObject = new JSONObject(s);
                    Sensor pm25 = new Sensor("pm2.5", jsonObject.getInt("pm2.5"), pm25_limit);
                    Sensor co2 = new Sensor("co2", jsonObject.getInt("co2"), co2_limit);
                    Sensor LightIntensity = new Sensor("LightIntensity",
                            jsonObject.getInt("LightIntensity"), light_limit);
                    Sensor humidity = new Sensor("humidity", jsonObject.getInt("humidity"), humidity_limit);
                    Sensor temperature = new Sensor("temperature",
                            jsonObject.getInt("temperature"), temperature_limit);

                    data.add(pm25);
                    data.add(co2);
                    data.add(LightIntensity);
                    data.add(humidity);
                    data.add(temperature);

                    checkSensor();

                    if (contentValues == null)
                    {
                        contentValues = new ContentValues();
                    }
                    contentValues.put("pm25", pm25.getValue());
                    contentValues.put("co2", co2.getValue());
                    contentValues.put("LightIntensity", LightIntensity.getValue());
                    contentValues.put("humidity", humidity.getValue());
                    contentValues.put("temperature", temperature.getValue());
                    writableDatabase.insert(DBOpenHelper.TABLE_NAME, null, contentValues);
                    contentValues.clear();

                    if (adapter == null)
                    {
                        adapter = new GridViewAdapter(mActivity, data);
                        gridView.setAdapter(adapter);
                    }
                    else
                    {
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void error()
            {
                Log.i("main", "sensorFragment error");
            }
        };
    }

    private void checkSensor()
    {
        for (int i = 0; i < data.size(); i++)
        {
            Sensor sensor = data.get(i);
            if(sensor.getValue()>sensor.getLimit())
            {
                showNotification(i, sensor);
            }
            else
            {
                manager.cancel(i);
            }
        }
    }

    private void showNotification(int i, Sensor sensor)
    {
        Notification.Builder builder = new Notification.Builder(mActivity);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("警告");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        builder.setContentText(sensor.getName()+"超标\n阈值为"+sensor.getLimit()+",当前值为"+sensor.getValue()+"\n时间:"+format.format(new Date()));
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.putExtra("page", "t35");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(mActivity, 200, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.getNotification();
        manager.notify(i, notification);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (values == null)
        {
            values = new ArrayList<>();
        }
        Intent intent = new Intent(mActivity, ShowLineChartActivity.class);
        switch (position)
        {
            case 0:
                intent.putExtra("name", "pm25");
                break;
            case 1:
                intent.putExtra("name", "co2");
                break;
            case 2:
                intent.putExtra("name", "LightIntensity");
                break;
            case 3:
                intent.putExtra("name", "humidity");
                break;
            case 4:
                intent.putExtra("name", "temperature");
                break;
        }
        startActivity(intent);
        values.clear();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
//        readableDatabase.close();
//        writableDatabase.close();
    }
}
