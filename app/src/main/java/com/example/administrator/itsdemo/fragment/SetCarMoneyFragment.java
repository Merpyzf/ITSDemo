package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;
import com.example.administrator.itsdemo.utils.PreUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/12.
 */

public class SetCarMoneyFragment extends Fragment {

    private Button btn_getCarSpeed;

    private SeekBar seekBar;

    private EditText edit_max;

    private EditText edit_min;

    private int max;

    private int min;

    private Button btn_setCarSpeed;

    private int currentValue;

    private View view;

    private TextView carShow;

    private Button carStar;//开始监控

    private Timer timer = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set_car_money, container,false);

        iniUI();

        iniEvent();

        return view;
    }

    private void iniEvent() {
        //获取小车阀值
        btn_getCarSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                currentValue = seekBar.getProgress();
                min = PreUtils.getInt(getContext(),currentValue+"号车min金额", 20);
                max = PreUtils.getInt(getContext(),currentValue+"号车max金额", 60);

                edit_min.setText("" + min);
//                edit_max.setText("" + max);

            }
        });

        //设置小车阀值
        btn_setCarSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _min = edit_min.getText().toString();
                String _max = edit_max.getText().toString();

                if(TextUtils.isEmpty(_max)) {
                    max = 60;
                }else{
                    max = Integer.parseInt(_max);
                }
                if(TextUtils.isEmpty(_min)) {
                    min = 20;
                }else{
                    min = Integer.parseInt(_min);
                }

                currentValue = seekBar.getProgress();

                PreUtils.setInt(getContext(), currentValue+"号车min金额",min);
                PreUtils.setInt(getContext(), currentValue+"号车max金额",max);
                Toast.makeText(getContext(), "设置成功", Toast.LENGTH_SHORT).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carShow.setText(progress+ "号车");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        carStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始监控
                currentValue = seekBar.getProgress();
                min = PreUtils.getInt(getContext(),currentValue+"号车min金额", 20);
                max = PreUtils.getInt(getContext(),currentValue+"号车max金额", 60);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {

                        new HttpAsyncUtils(Common.GET_CAR_ACCOUNT_BALANCE, "{\"CarId\":" + currentValue + ", " +
                                "\"UserName\":\"Z0004\"}") {
                            @Override
                            public void success(String data) {

                                try {
                                    JSONObject obj = new JSONObject(data);
                                    int money = (int) obj.get("Balance");
                                    Log.i("TAG", "success: " + money);
                                    if(money<min){
                                        Toast.makeText(getContext(), "余额不足，请充值", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }

                            @Override
                            public void error() {

                            }
                        };


                    }
                };
                timer = new Timer();
                timer.schedule(timerTask,0,5000);

            }
        });
    }

    private void iniUI() {
        btn_setCarSpeed = (Button) view.findViewById(R.id.car_set_money_value);
        btn_getCarSpeed = (Button) view.findViewById(R.id.car_get_money);
        carStar = (Button) view.findViewById(R.id.car_money_start);
        seekBar = (SeekBar) view.findViewById(R.id.car_money_seek_bar);
        edit_min = (EditText) view.findViewById(R.id.car_money_min);
        edit_max = (EditText) view.findViewById(R.id.car_money_max);
        carShow = (TextView) view.findViewById(R.id.car_money_value_show);
        seekBar.setProgress(1);
    }

    @Override
    public void onDestroy() {
        if(timer !=null){
            timer.cancel();
        }
        super.onDestroy();
    }
}
