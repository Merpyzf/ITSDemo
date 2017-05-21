package com.example.administrator.itsdemo.fragment;

import android.content.pm.ProviderInfo;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SetCarSpeedFragment extends Fragment {

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
        view = inflater.inflate(R.layout.fragment_set_car_speed, container,false);

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
                max = PreUtils.getInt(getContext(),currentValue+"号车max", 60);
                min = PreUtils.getInt(getContext(),currentValue+"号车min", 20);

                edit_min.setText("" + min);
                edit_max.setText("" + max);

            }
        });

        //设置小车阀值
        btn_setCarSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _max = edit_max.getText().toString();
                String _min = edit_min.getText().toString();
                if(TextUtils.isEmpty( _max)) {
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

                PreUtils.setInt(getContext(), currentValue+"号车max",max);
                PreUtils.setInt(getContext(), currentValue+"号车min",min);

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
                max = PreUtils.getInt(getContext(),currentValue+"号车max", 60);
                min = PreUtils.getInt(getContext(),currentValue+"号车min", 20);

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        final int rand = (int) (Math.random() * 100);
                        Log.i("TAG", "run: " + rand + "max=" +max+"min="+min);

                        if(rand<min){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "监测到速度过慢", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        if(rand>max){
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url(Common.SET_CAR_MOVE).post(
                                    RequestBody.create(MediaType.parse("application/json"),"{\"CarId\":"+currentValue+", " +
                                            "\"CarAction\":\"Stop\",\"UserName\":\"Z0004\"}")
                            ).build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {

                                }

                                @Override
                                public void onResponse(Response response) throws IOException {
                                    Log.i("TAG", "onResponse: " + response.body().string());
                                   getActivity().runOnUiThread(new Runnable() {
                                       @Override
                                       public void run() {
                                           Toast.makeText(getContext(), "已超速，小车已已停止", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                                }
                            });


                        }
                    }
                };
                timer = new Timer();
                timer.schedule(timerTask,0,5000);

            }
        });
    }

    private void iniUI() {
        btn_setCarSpeed = (Button) view.findViewById(R.id.car_set_value);
        btn_getCarSpeed = (Button) view.findViewById(R.id.car_get_value);
        carStar = (Button) view.findViewById(R.id.car_start);
        seekBar = (SeekBar) view.findViewById(R.id.car_seek_bar);
        edit_max = (EditText) view.findViewById(R.id.car_speed_max);
        edit_min = (EditText) view.findViewById(R.id.car_speed_min);
        carShow = (TextView) view.findViewById(R.id.car_value_show);
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
