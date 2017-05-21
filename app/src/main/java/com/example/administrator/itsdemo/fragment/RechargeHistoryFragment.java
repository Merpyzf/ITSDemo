package com.example.administrator.itsdemo.fragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.adapter.MyBillListAdapter;
import com.example.administrator.itsdemo.domain.Bill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class RechargeHistoryFragment extends BaseFragment implements View.OnClickListener
{
    private Spinner spinner;
    private Button query;
    private ListView listView;

    private List<Bill> bills;
    private MyBillListAdapter adapter;

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_recharge_history, null);

        spinner = (Spinner) view.findViewById(R.id.recharge_spinner);
        query = (Button) view.findViewById(R.id.recharge_btn_query);
        listView = (ListView) view.findViewById(R.id.recharge_list);

        query.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData()
    {
        String[] data = {"时间升序", "时间降序"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_list_item_1, data);
        spinner.setAdapter(arrayAdapter);

        Bill bill1 = new Bill(1, 1, 100, randomDate("2017-01-01", "2017-04-01"));
        Bill bill2 = new Bill(2, 1, 8, randomDate("2017-01-01", "2017-04-01"));
        Bill bill3 = new Bill(3, 2, 80, randomDate("2017-01-01", "2017-04-01"));

        bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);

        Collections.sort(bills, new Comparator<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2)
            {
                if(o1.getDate().getTime() > o2.getDate().getTime())
                    return -1;
                else if(o1.getDate().getTime() < o2.getDate().getTime())
                    return 1;
                else
                    return 0;
            }
        });

        listView.addHeaderView(View.inflate(mActivity, R.layout.item_list_recharge, null));

        adapter = new MyBillListAdapter(mActivity, bills);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        if (spinner.getSelectedItemPosition() == 0)
        {
            Collections.sort(bills, new Comparator<Bill>() {
                @Override
                public int compare(Bill o1, Bill o2)
                {
                    if(o1.getDate().getTime() > o2.getDate().getTime())
                        return 1;
                    else if(o1.getDate().getTime() < o2.getDate().getTime())
                        return -1;
                    else
                        return 0;
                }
            });
        }
        else
        {
            Collections.sort(bills, new Comparator<Bill>() {
                @Override
                public int compare(Bill o1, Bill o2)
                {
                    if(o1.getDate().getTime() > o2.getDate().getTime())
                        return -1;
                    else if(o1.getDate().getTime() < o2.getDate().getTime())
                        return 1;
                    else
                        return 0;
                }
            });
        }
        adapter.notifyDataSetChanged();
    }

    private static Date randomDate(String beginDate, String endDate)
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);//构造开始日期
            Date end = format.parse(endDate);//构造结束日期
            //getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime())
            {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static long random(long begin, long end)
    {
        long rtn = begin + (long) (Math.random() * (end - begin));
        //如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end)
        {
            return random(begin, end);
        }
        return rtn;
    }
}
