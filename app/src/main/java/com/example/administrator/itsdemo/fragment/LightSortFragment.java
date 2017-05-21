package com.example.administrator.itsdemo.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.adapter.MyLightListAdapter;
import com.example.administrator.itsdemo.domain.Road;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.administrator.itsdemo.Common.GET_TRAFFIC_LIGHT_CONFIG_ACTION;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class LightSortFragment extends BaseFragment implements View.OnClickListener
{
    private Spinner spinner;
    private Button query;
    private ListView listView;

    private List<Road> roads = new ArrayList<>();
    private MyLightListAdapter adapter;

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_light_sort, null);

        spinner = (Spinner) view.findViewById(R.id.light_spinner);
        query = (Button) view.findViewById(R.id.light_btn_query);
        listView = (ListView) view.findViewById(R.id.light_list);

        query.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData()
    {
        String[] data = {"红灯升序", "绿灯降序"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_list_item_1, data);
        spinner.setAdapter(arrayAdapter);

        for (int i = 1; i < 4; i++)
        {
            final int finalI = i;
            new HttpAsyncUtils(GET_TRAFFIC_LIGHT_CONFIG_ACTION,
                    "{'TrafficLightId':'"+ finalI +"', 'UserName':'Z0004'}")
            {
                @Override
                public void success(String data)
                {
                    Log.i("main", data);
                    try
                    {
                        JSONObject object = new JSONObject(data);
                        Road road = new Road(finalI, object.getInt("RedTime"), object.getInt("GreenTime"), object.getInt("YellowTime"));
                        roads.add(road);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    if(adapter == null)
                    {
                        adapter = new MyLightListAdapter(mActivity, roads);
                        listView.setAdapter(adapter);
                    }
                    else
                    {
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void error()
                {
                    Log.i("main", "light error");
                }
            };
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        listView.addHeaderView(View.inflate(mActivity, R.layout.item_list_light, null));

    }

    @Override
    public void onClick(View v)
    {
        if (spinner.getSelectedItemPosition() == 0)
        {
            Collections.sort(roads, new Comparator<Road>()
            {
                @Override
                public int compare(Road o1, Road o2)
                {
                    if (o1.getRed() > o2.getRed())
                    { return 1; }
                    else if (o1.getRed() < o2.getRed())
                    { return -1; }
                    else
                    { return 0; }
                }
            });
        }
        else
        {
            Collections.sort(roads, new Comparator<Road>()
            {
                @Override
                public int compare(Road o1, Road o2)
                {
                    if (o1.getGreen() > o2.getGreen())
                    { return -1; }
                    else if (o1.getGreen() < o2.getGreen())
                    { return 1; }
                    else
                    { return 0; }
                }
            });
        }
        adapter.notifyDataSetChanged();
    }
}
