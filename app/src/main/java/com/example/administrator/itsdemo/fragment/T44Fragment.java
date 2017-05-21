package com.example.administrator.itsdemo.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.WangkeUtils;

public class T44Fragment extends Fragment {


    private AlertDialog alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t44, container, false);

        if(!WangkeUtils.isNetWorkAvailable(getActivity())){

            showSetNetWorkDialog(getActivity());

        }


        return view;
    }

    private void showSetNetWorkDialog(Context context) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("网络信息提示");

        builder.setMessage("当前网络不可用,请先进行设置");


        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                startActivity(intent);

            }
        });

        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();

            }
        });

        alertDialog = builder.create();

        alertDialog.show();



    }

//    2、	在主界面和登录界面实现连续两次点击返回键，并且两次点击的时间间隔小于2秒钟时直接退出该系统，否则提示“再按一次退出系统”的信息。

//       双击返回键退出程序的代码写到主界面了！！！
}
