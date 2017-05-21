package com.example.administrator.itsdemo.fragment;

/**
 * Created by Administrator on 2017/5/12.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.administrator.itsdemo.utils.PreUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * 账户充值
 */
public class AccountRechargeFragment extends Fragment{


    private View view;

    private EditText edit_carId;

    private EditText rechargeMoney;

    private Button btn_query;

    private Button btn_recharge;

    private TextView show;

    private int money;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_recharge,container,false);
        iniUI();
        iniEvent();
        return view;
    }

    private void iniEvent() {
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = edit_carId.getText().toString();
                new HttpAsyncUtils(Common.GET_CAR_ACCOUNT_BALANCE, "{\"CarId\":" + _id + ", \"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {
                        try {
                            JSONObject obj = new JSONObject(data);
                            money = (int) obj.get("Balance");
                            show.setText("账户余额：" + obj.get("Balance"));

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

        btn_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String _id = edit_carId.getText().toString();

                final int _money = PreUtils.getInt(getContext(),_id+"号车max金额",60);

                if(money>_money){
                    Toast.makeText(getContext(), "充值失败，余额已超过最大阀值", Toast.LENGTH_SHORT).show();
                    return;
                }

                new HttpAsyncUtils(Common.SET_CAR_ACCOUNT_RECHARGE, "{\"CarId\":" + _id + ",\"Money\":" +
                        rechargeMoney.getText().toString() + ", \"UserName\":\"Z0004\"}") {
                    @Override
                    public void success(String data) {

                        try {
                            JSONObject obj = new JSONObject(data);
                            Log.i("TAG", "success: " + obj.get("RESULT"));

                                Toast.makeText(getContext(), "充值成功", Toast.LENGTH_SHORT).show();
                                PreUtils.setInt(getContext(),_id+"号车max金额",_money+money);

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
    }

    private void iniUI() {
        btn_query = (Button) view.findViewById(R.id.account_recharge_car_query);
        btn_recharge = (Button) view.findViewById(R.id.account_recharge_start);
        edit_carId = (EditText) view.findViewById(R.id.account_recharge_car_id);
        rechargeMoney = (EditText) view.findViewById(R.id.account_recharge_car_money);
        show = (TextView) view.findViewById(R.id.account_recharge_car_account_show);
    }
}
