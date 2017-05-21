package com.example.administrator.itsdemo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;
import com.example.administrator.itsdemo.utils.PreUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by simple_soul on 2017/5/16.
 */

public class RechargeCarFragment extends BaseFragment implements View.OnClickListener
{
    private TextView balance;
    private Spinner spinner;
    private EditText edit;
    private Button query, recharge;
    private AlertDialog alertDialog;

    @Override
    public View initView()
    {
        View view = View.inflate(mActivity, R.layout.fragment_recharge_car, null);

        balance = (TextView) view.findViewById(R.id.recharge_tv_balance);
        spinner = (Spinner) view.findViewById(R.id.recharge_spinner_car);
        edit = (EditText) view.findViewById(R.id.recharge_edit);
        query = (Button) view.findViewById(R.id.recharge_btn_query_car);
        recharge = (Button) view.findViewById(R.id.recharge_btn_recharge);

        query.setOnClickListener(this);
        recharge.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData()
    {
        String[] array = {"1", "2", "3", "4", "5", "6", "7"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mActivity,
                android.R.layout.simple_list_item_1, array);
        spinner.setAdapter(arrayAdapter);
        queryBalance();

    }

    private void queryBalance()
    {
        new HttpAsyncUtils(Common.GET_CAR_ACCOUNT_BALANCE,
                "{\"CarId\":" + spinner.getSelectedItem() + ", \"UserName\":\"Z0004\"}")
        {
            @Override
            public void success(String data)
            {
                Log.i("main", "小车余额：" + data);
                try
                {
                    JSONObject object = new JSONObject(data);
                    balance.setText(object.getString("Balance"));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void error()
            {
                Log.i("main", "小车余额error");
            }
        };
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("小车充值");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.setMessage("充值时间：" + format.format(date) + "\n充值金额：" + edit.getText()
                .toString() + "\n小车号：" + spinner.getSelectedItem());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                recharge();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (!PreUtils.getBoolean(mActivity, "recharge", false))
                {
                    showWaringDialog(dialog);
                }
                else
                {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showWaringDialog(final DialogInterface d)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        View view = View.inflate(mActivity, R.layout.dialog_recharge, null);
        CheckBox check = (CheckBox) view.findViewById(R.id.dialog_check);
        Button ok = (Button) view.findViewById(R.id.dialog_btn_ok);
        Button cancel = (Button) view.findViewById(R.id.dialog_btn_cancel);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                PreUtils.setBoolean(mActivity, "recharge", isChecked);
            }
        });
        ok.setTag(d);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void recharge()
    {
        new HttpAsyncUtils(Common.SET_CAR_ACCOUNT_RECHARGE,
                "{'CarId':" + spinner.getSelectedItem() + ",'Money':" + edit.getText()
                        .toString() + ", 'UserName':'Z0004'}")
        {
            @Override
            public void success(String data)
            {
                try
                {
                    JSONObject object = new JSONObject(data);
                    if (object.getString("RESULT").equals("S"))
                    {
                        Toast.makeText(mActivity, "充值成功", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(mActivity, "充值失败", Toast.LENGTH_SHORT).show();
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
                Log.i("main", "小车充值error");
            }
        };
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.recharge_btn_query_car:
                queryBalance();
                break;

            case R.id.recharge_btn_recharge:
                showDialog();
                break;

            case R.id.dialog_btn_ok:
                AlertDialog dialog = (AlertDialog) v.getTag();
                dialog.dismiss();
                alertDialog.dismiss();
                break;

            case R.id.dialog_btn_cancel:
                alertDialog.dismiss();
                break;
        }
    }
}
