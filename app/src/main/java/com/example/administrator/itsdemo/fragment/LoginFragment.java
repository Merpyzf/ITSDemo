package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.PreUtils;

/**
 * Created by Administrator on 2017/5/15.
 */

public class LoginFragment extends Fragment {

    private View view;

    private Button btn_login;

    private Button btn_reg;

    private CheckBox box_auto;

    private CheckBox box_remember;

    private EditText edit_username;

    private EditText edit_userpass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login,container,false);

        iniUI();

        iniEvent();

        return view;
    }

    private void iniEvent() {

        if(PreUtils.getBoolean(getContext(),"login_remember",false)){
            edit_username.setText(PreUtils.getString(getContext(),"login_username",""));
            edit_userpass.setText(PreUtils.getString(getContext(),"login_userpass",""));
            box_remember.setChecked(true);
        }

        if(PreUtils.getBoolean(getContext(),"login_auto",false)){
            box_auto.setChecked(true);
            login();
        }

        box_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box_auto.isChecked()){
                    PreUtils.setBoolean(getContext(),"login_auto",true);
                }else{
                    PreUtils.setBoolean(getContext(),"login_auto",false);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void iniUI() {
        btn_login = (Button) view.findViewById(R.id.login_login);
        btn_reg = (Button) view.findViewById(R.id.login_reg);

        box_auto = (CheckBox) view.findViewById(R.id.login_auto);
        box_remember = (CheckBox) view.findViewById(R.id.login_remember);

        edit_username = (EditText) view.findViewById(R.id.login_username);
        edit_userpass = (EditText) view.findViewById(R.id.login_userpass);
    }

    private void login(){
        String name = edit_username.getText().toString();
        String pass = edit_userpass.getText().toString();

        if(name.matches("[a-zA-z]{6,12}")){

            if(pass.matches("a.{6,12}")){

                if(box_remember.isChecked()){
                    PreUtils.setBoolean(getContext(),"login_remember",true);

                    PreUtils.setString(getContext(),"login_username",name);

                    PreUtils.setString(getContext(),"login_userpass",pass);
                }else{
                    PreUtils.setBoolean(getContext(),"login_remember",false);

                    PreUtils.setString(getContext(),"login_username","");

                    PreUtils.setString(getContext(),"login_userpass","");
                }



                Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();




            }else{
                Toast.makeText(getContext(), "登陆失败，密码必须为6-12位以a开头", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getContext(), "用户名必须为6-12位纯英文", Toast.LENGTH_SHORT).show();
        }
    }
}
