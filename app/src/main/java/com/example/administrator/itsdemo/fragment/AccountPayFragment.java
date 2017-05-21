package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AccountPayFragment extends Fragment{

    private View view;


    private Button btn_query;

    private Button btn_charge;

    private TextView show;

    private AlertDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_pay,container,false);

        btn_charge = (Button) view.findViewById(R.id.account_pay_recharge);

        btn_query = (Button) view.findViewById(R.id.account_pay_query);

        show = (TextView) view.findViewById(R.id.account_pay_show);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncUtils(Common.GET_CAR_ACCOUNT_BALANCE, "{\"CarId\":2, \"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {
                        try {
                            JSONObject obj = new JSONObject(data);
                            Log.i("TAG", "success: " + data);
                            show.setText("当前账户余额："+obj.getString("Balance"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void error() {

                    }
                };
            }
        });

        btn_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View _view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_account_pay,null,false);
                Button submit = (Button) _view.findViewById(R.id.dialog_account_pay_submit);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText password = (EditText) _view.findViewById(R.id.dialog_account_pay_pass);
                        String pass = password.getText().toString();

                        if("123456".equals(pass)){
                            Toast.makeText(getContext(), "密码正确", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                        .setTitle("安全支付")
                        .setView(_view)
                        ;
                dialog = dialogBuilder.create();
                dialog.show();
            }
        });

        return view;
    }
}

