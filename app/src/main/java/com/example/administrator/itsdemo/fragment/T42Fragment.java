package com.example.administrator.itsdemo.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.customui.IpEditText;
import com.example.administrator.itsdemo.utils.PreUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class T42Fragment extends Fragment implements View.OnClickListener {

    private TextView tv_setting_ip;

    private TextView tv_show_ip;

    private TextView tv_setting_language;
    private AlertDialog alertDialog;
    private int DefaultChoice = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t42, container, false);


        tv_setting_ip = (TextView) view.findViewById(R.id.tv_setting_ip);
        tv_setting_language = (TextView)view.findViewById(R.id.tv_setting_language);
        tv_show_ip = (TextView)view.findViewById(R.id.tv_show_ip);

        tv_setting_ip.setOnClickListener(this);
        tv_setting_language.setOnClickListener(this);
        tv_show_ip.setOnClickListener(this);
        changeLanguage();





        return view;
    }

    private void changeLanguage() {

        String language = PreUtils.getString(getContext(),"language","");

        if(language.equals("") || language.equals("chinese")){

            tv_setting_ip.setText("设置服务器ip");

            DefaultChoice = 0;


        }else if(language.equals("english")){

            tv_setting_ip.setText("setting server ip Address");

            DefaultChoice = 1;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.tv_setting_ip:


                Log.i("wk","被点击");

                ShowDialog();




                break;

            case R.id.tv_setting_language:

                showLanguageDialog();





                break;



        }


    }

    private void showLanguageDialog() {

        final int[] language = {0};

        new AlertDialog.Builder(getActivity())
                .setTitle("设置语言")
                .setSingleChoiceItems(new String[]{"简体中文", "English"}, DefaultChoice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        language[0] = which;


                    }
                })
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(language[0] == 0){


                            PreUtils.setString(getActivity(),"language","chinese");

                        }else if(language[0]==1){


                            PreUtils.setString(getActivity(),"language","english");
                        }

                        changeLanguage();



                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .show();



    }

    private void ShowDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = View.inflate(getContext(),R.layout.dialog_t42,null);

        Button btnOk = (Button) view.findViewById(R.id.btn_ok);

        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        final IpEditText ipEditText = (IpEditText) view.findViewById(R.id.ipEditText);

        builder.setView(view);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_show_ip.setText(ipEditText.getIpAddress());
                alertDialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();
            }
        });


        alertDialog = builder.create();


        alertDialog.show();


    }
}
