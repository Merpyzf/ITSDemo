package com.example.administrator.itsdemo.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class T34Fragment extends Fragment {

    private ListView listview_t34;
    private ArrayList<CarBalanceBean> CarList = new ArrayList<CarBalanceBean>();

    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t34, container, false);


        listview_t34 = (ListView) view.findViewById(R.id.listview_t34);

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {


                CarBalanceBean bean = (CarBalanceBean) msg.obj;

                CarList.add(bean);


                Log.i("wk", "小车长度==>" + CarList.size());


                if (CarList.size() == 5) {


                    Collections.sort(CarList,new Comparator<CarBalanceBean>() {
                        @Override
                        public int compare(CarBalanceBean o1, CarBalanceBean o2) {

                            if (o1.getCarId() < o2.getCarId()) {

                                return -1;
                            } else {

                                return 1;
                            }


                        }

                    });


                    listview_t34.setAdapter(new T34Adapter());

                }


            }
        };


        getCarBalanceInfo();


        return view;
    }

    private void getCarBalanceInfo() {


        new Thread(new Runnable() {
            @Override
            public void run() {


                if (CarList.size() != 0) {

                    CarList.clear();
                }

                for (int i = 1; i <= 5; i++) {


                    final int finalI = i;
                    new HttpAsyncUtils(Common.GET_CAR_ACCOUNT_BALANCE, "{\"CarId\":" + finalI + ", \"UserName\":\"Z0004\"}") {
                        @Override
                        public void success(String data) {

                            try {
                                CarBalanceBean carBalanceBean = new CarBalanceBean();

                                carBalanceBean.setCarId(finalI);

                                JSONObject jsonObject = new JSONObject(data);

                                int balance = jsonObject.getInt("Balance");

                                carBalanceBean.setBalance(balance);


                                Message msg = mHandler.obtainMessage();
                                msg.obj = carBalanceBean;
                                mHandler.sendMessage(msg);







                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void error() {

                        }
                    };


                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        }).start();

    }


    class T34Adapter extends BaseAdapter {

        private Context context = getContext();


        @Override
        public int getCount() {
            return CarList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = View.inflate(context, R.layout.item_t34, null);

            TextView tv_balance = (TextView) view.findViewById(R.id.tv_balance);
            Button btn_recharge = (Button) view.findViewById(R.id.btn_recharge);


            final CarBalanceBean bean = CarList.get(position);

            tv_balance.setText(bean.getCarId() + "号小车余额: " + bean.getBalance());


            btn_recharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    View view = View.inflate(context, R.layout.dialog_t34, null);


                    final EditText edt_money = (EditText) view.findViewById(R.id.edt_money);
                    Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
                    Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
                    builder.setView(view);
                    final AlertDialog alertDialog = builder.show();


                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String money = edt_money.getText().toString().trim();

                            if (!money.equals("")) {

                                final int money1 = Integer.valueOf(money);

                                if (money1 <= 1000) {


                                    new HttpAsyncUtils(Common.SET_CAR_ACCOUNT_RECHARGE, "{\"CarId\":" + bean.getCarId() + ",\"Money\":" + money1 + ", \"UserName\":\"Z0004\"}") {
                                        @Override
                                        public void success(String data) {


                                            try {
                                                JSONObject jsonObject = new JSONObject(data);


                                                String response =  jsonObject.getString("ERRMSG");

                                                if(response.equals("成功")){


                                                    Toast.makeText(context,"充值成功",Toast.LENGTH_SHORT).show();


                                                    bean.setBalance(bean.getBalance()+money1);

                                                    notifyDataSetChanged();


                                                }else {

                                                    Toast.makeText(context,"充值失败",Toast.LENGTH_SHORT).show();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }

                                        @Override
                                        public void error() {

                                        }
                                    };



                                } else {


                                    Toast.makeText(context, "充值金额最多不能超过1000", Toast.LENGTH_SHORT).show();
                                }


                            } else {

                                Toast.makeText(context, "检查不能输入为空", Toast.LENGTH_SHORT).show();
                            }


                            alertDialog.dismiss();

                        }
                    });

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            alertDialog.dismiss();

                        }
                    });


                }
            });


            return view;
        }


    }


    class CarBalanceBean {

        private int carId;
        private int balance;


        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }
    }
}
