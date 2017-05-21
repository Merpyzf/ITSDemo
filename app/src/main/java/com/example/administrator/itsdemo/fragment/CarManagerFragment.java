package com.example.administrator.itsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

/**
 * Created by Administrator on 2017/5/14.
 */

public class CarManagerFragment extends Fragment{


    private View view;

    private Switch switchStop;

    private TextView speedShow;

    private Spinner spinner;

    private ArrayAdapter<String> adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_car_manager,container,false);


        iniUI();
        iniEvent();
        return view;
    }

    private void iniEvent() {

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,new String[]{"1","2","3","4"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        switchStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchStop.isChecked()){
                    Toast.makeText(getContext(), "启动成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "停止成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String i = spinner.getSelectedItem().toString();
                speedShow.setText((int)(Math.random() * 100)+ "km");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void iniUI() {
        switchStop = (Switch) view.findViewById(R.id.car_manager_switch);
        spinner = (Spinner) view.findViewById(R.id.car_manager_spinner_number);

        speedShow = (TextView) view.findViewById(R.id.car_manager_speed_show);

    }
}
