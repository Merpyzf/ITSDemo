package com.example.administrator.itsdemo.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.adapter.MyListAdapter;
import com.example.administrator.itsdemo.domain.Value;
import com.example.administrator.itsdemo.utils.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.itsdemo.Common.co2_limit;
import static com.example.administrator.itsdemo.Common.humidity_limit;
import static com.example.administrator.itsdemo.Common.light_limit;
import static com.example.administrator.itsdemo.Common.pm25_limit;
import static com.example.administrator.itsdemo.Common.temperature_limit;

public class SensorHistoryFragment extends BaseFragment implements View.OnClickListener
{
    private Spinner type, time;
    private Button query;
    private ListView listView;

    private SQLiteDatabase readableDatabase;
    private List<Value> values = new ArrayList<>();
    private MyListAdapter listAdapter;

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_senosr_history, null);

        type = (Spinner) view.findViewById(R.id.history_spinner_type);
        time = (Spinner) view.findViewById(R.id.history_spinner_time);
        query = (Button) view.findViewById(R.id.history_btn_query);
        listView = (ListView) view.findViewById(R.id.history_list);

        query.setOnClickListener(this);
        listView.addHeaderView(View.inflate(mActivity, R.layout.item_list_sensor, null));

        return view;
    }

    @Override
    public void initData()
    {
        String[] types = {"pm2.5", "co2", "光照强度", "湿度", "温度"};
        String[] times = {"3min", "5min"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_list_item_1, types);
        type.setAdapter(typeAdapter);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_list_item_1, times);
        time.setAdapter(timeAdapter);

        DBOpenHelper helper = new DBOpenHelper(mActivity, DBOpenHelper.DB_NAME);
        readableDatabase = helper.getReadableDatabase();

        initList("pm25", pm25_limit);
    }

    @Override
    public void onClick(View v)
    {
        switch (type.getSelectedItemPosition())
        {
            case 0:
                initList("pm25", pm25_limit);
                break;

            case 1:
                initList("co2", co2_limit);

                break;

            case 2:
                initList("LightIntensity", light_limit);

                break;

            case 3:
                initList("humidity", humidity_limit);

                break;

            case 4:
                initList("temperature", temperature_limit);

                break;
        }
    }

    private void initList(String name, int limit)
    {
        values.clear();
        Cursor cursor = readableDatabase.rawQuery(
                "select " + name + ", time from " + DBOpenHelper.MAIN_TABLE + " order by _id desc",
                null);
        int k;
        if (time.getSelectedItemPosition() == 0)
        {
            k = 3;
        }
        else
        {
            k = 5;
        }
        if (cursor != null)
        {
            for (int i = 0; i < cursor.getCount(); i += k)
            {
                cursor.moveToPosition(i);
                Value value = new Value(cursor.getInt(cursor.getColumnIndex(name)),
                        cursor.getString(cursor.getColumnIndex("time")));
                values.add(value);
            }
            listAdapter = new MyListAdapter(mActivity, values, (String) type.getSelectedItem(),
                    limit);
            listView.setAdapter(listAdapter);

        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        readableDatabase.close();
    }
}
