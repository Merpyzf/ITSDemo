package com.example.administrator.itsdemo.fragment;

import android.content.Intent;
import android.view.View;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.service.MyService;

/**
 * Created by simple_soul on 2017/5/15.
 */

public class QueryCarBalanceFragment extends BaseFragment
{
    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_query_car_balance, null);
        return view;
    }

    @Override
    public void initData()
    {
        Intent intent = new Intent(mActivity, MyService.class);
        mActivity.startService(intent);
    }

}
