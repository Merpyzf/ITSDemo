package com.example.administrator.itsdemo.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.Common;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.DBHelper;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class T45Fragment extends Fragment implements View.OnClickListener {

    private TextView tv_balance, tv_remotecontrol, tv_recharge;


    private ViewPager mViewPager;
    private ArrayList<View> mViewLists = new ArrayList<View>();
    String sql = "create table tab_recharge (_id int auto_increment primary key,car_id int,recharge_money int,recharge_date TIMESTAMP NOT NULL)";

    private Context context;
    private LinearLayout ll_tab;
    private Timer timer;
    private SQLiteDatabase write;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_t45, container, false);

        context = getContext();
        InitUI(view);
        InitData();

        DBHelper dbHelper = new DBHelper(context, "ITS.db", sql);
        write = dbHelper.getWritableDatabase();


        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                for (int i = 0; i < 3; i++) {


                    TextView textView = (TextView) ll_tab.getChildAt(i);
                    if (i == position) {
                   /*     TextPaint tp = textView.getPaint();
                        tp.setFakeBoldText(true);
                        Log.i("wk","加粗");*/

                    } else {


             /*           TextPaint tp = textView.getPaint();
                        tp.setFakeBoldText(false);
                        Log.i("wk","取消加粗");*/
                    }

                }


            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }

    private void InitData() {

        mViewLists.add(getT45M1View());
        mViewLists.add(getT45M2View());
        mViewLists.add(getT45M3View());

    }

    private View getT45M1View() {

        /**
         * 已放弃治疗/(ㄒoㄒ)/~~
         */
        final LinearLayout ll_bg1, ll_bg2, ll_bg3, ll_bg4;
        final TextView tv_status_1, tv_status_2, tv_status_3, tv_status_4;
        final TextView tv_values_1, tv_values_2, tv_values_3, tv_values_4;

        View view = View.inflate(context, R.layout.layout_t45m1, null);

        ll_bg1 = (LinearLayout) view.findViewById(R.id.ll_bg1);
        ll_bg2 = (LinearLayout) view.findViewById(R.id.ll_bg2);
        ll_bg3 = (LinearLayout) view.findViewById(R.id.ll_bg3);
        ll_bg4 = (LinearLayout) view.findViewById(R.id.ll_bg4);

        tv_status_1 = (TextView) view.findViewById(R.id.tv_status_1);
        tv_status_2 = (TextView) view.findViewById(R.id.tv_status_2);
        tv_status_3 = (TextView) view.findViewById(R.id.tv_status_3);
        tv_status_4 = (TextView) view.findViewById(R.id.tv_status_4);


        tv_values_1 = (TextView) view.findViewById(R.id.tv_values_1);
        tv_values_2 = (TextView) view.findViewById(R.id.tv_values_2);
        tv_values_3 = (TextView) view.findViewById(R.id.tv_values_3);
        tv_values_4 = (TextView) view.findViewById(R.id.tv_values_4);


        ll_bg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reChargeBalance(1);

            }
        });


        ll_bg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reChargeBalance(2);
            }
        });

        ll_bg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reChargeBalance(3);

            }
        });

        ll_bg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reChargeBalance(4);
            }


        });


        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {

                    case 1:

                        int balance1 = (int) msg.obj;

                        setColor(balance1, ll_bg1, tv_status_1);


                        tv_values_1.setText("" + balance1);

                        break;
                    case 2:
                        int balance2 = (int) msg.obj;

                        setColor(balance2, ll_bg2, tv_status_2);
                        tv_values_2.setText("" + balance2);
                        break;

                    case 3:
                        int balance3 = (int) msg.obj;
                        setColor(balance3, ll_bg3, tv_status_3);
                        tv_values_3.setText("" + balance3);
                        break;
                    case 4:
                        int balance4 = (int) msg.obj;
                        setColor(balance4, ll_bg4, tv_status_4);
                        tv_values_4.setText("" + balance4);
                        break;
                }


            }

            private void setColor(int balance, LinearLayout ll_bg, TextView tv_status) {

                if (balance >= 100) {

                    ll_bg.setBackgroundColor(Color.RED);
                    tv_status.setText("警告");

                } else {

                    ll_bg.setBackgroundColor(Color.GREEN);
                    tv_status.setText("正常");
                }


            }
        };


        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        for (int i = 1; i <= 4; i++) {

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                            }


                            final int finalI = i;
                            new HttpAsyncUtils(Common.GET_CAR_ACCOUNT_BALANCE, " {\"CarId\":" + finalI + ", \"UserName\":\"Z0004\"}") {
                                @Override
                                public void success(final String data) {

                                    Log.i("wk", data);

                                    try {
                                        Message msg = new Message();

                                        msg.what = finalI;

                                        JSONObject jsonObject = new JSONObject(data);

                                        msg.obj = jsonObject.getInt("Balance");

                                        handler.sendMessage(msg);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void error() {

                                }
                            };

                        }

                    }
                }).start();


            }
        }, 0, 5000);


        return view;

    }

    /**
     * 小车充值
     *
     * @param i
     */
    private void reChargeBalance(final int i) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = View.inflate(context, R.layout.dialog_t45m1, null);

        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final EditText editText = (EditText) view.findViewById(R.id.edt_charge);


        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //充值

                dialog.dismiss();

                final int money = Integer.valueOf(editText.getText().toString().trim());

                if (money > 0 && money <= 50) {

                    new HttpAsyncUtils(Common.SET_CAR_ACCOUNT_RECHARGE, "{\"CarId\":" + i + ",\"Money\":" + i + ", \"UserName\":\"Z0004\"}") {
                        @Override
                        public void success(String data) {


                            Log.i("wk", "json==>" + data);

                            try {
                                JSONObject jb = new JSONObject(data);

                                String result = jb.getString("ERRMSG");

                                if (result.equals("成功")) {

                                    Toast.makeText(context, i + "号小车充值成功", Toast.LENGTH_SHORT).show();



                                    ContentValues values = new ContentValues();
                                    values.put("car_id", i);
                                    values.put("recharge_money", money);


                                    Date date = new Date(System.currentTimeMillis());

                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:hh:ss");

                                    values.put("recharge_date", format.format(date));


                                    write.insert("tab_recharge", null, values);


                                } else {
                                    Toast.makeText(context, i + "号小车充值失败", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(context, "充值的金额不符合", Toast.LENGTH_SHORT).show();

                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


    }

    private View getT45M2View() {


        View view = View.inflate(context, R.layout.layout_t45m2, null);

        ListView listView = (ListView) view.findViewById(R.id.listview_t45m2);

        listView.setAdapter(new ListViewT45M2Adapter());

        return view;

    }

    private View getT45M3View() {

        View view = View.inflate(context, R.layout.layout_t45m3, null);

        ListView listView_t45m3 = (ListView) view.findViewById(R.id.listView_t45m3);

        DBHelper dbHelper = new DBHelper(context, "ITS.db", sql);
        write = dbHelper.getWritableDatabase();

        Cursor cursor = write.rawQuery("select *from tab_recharge",null);

        ArrayList<CarBean> CarList = new ArrayList<CarBean>();

        while (cursor.moveToNext()) {

            CarBean bean = new CarBean();

            bean.setCarId(cursor.getInt(cursor.getColumnIndex("car_id")));
            bean.setMoney(cursor.getInt(cursor.getColumnIndex("recharge_money")));
            bean.setDate(cursor.getString(cursor.getColumnIndex("recharge_date")));

            CarList.add(bean);




        }

        Log.i("wk","长度==》"+CarList.size());
        listView_t45m3.setAdapter(new ListViewT45M3Adapter(CarList));

        return view;

    }


    private void InitUI(View view) {

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tv_balance = (TextView) view.findViewById(R.id.tv_balance);
        tv_remotecontrol = (TextView) view.findViewById(R.id.tv_remotecontrol);
        tv_recharge = (TextView) view.findViewById(R.id.tv_recharge);
        ll_tab = (LinearLayout) view.findViewById(R.id.ll_tab);
        tv_balance.setOnClickListener(this);
        tv_remotecontrol.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.tv_balance:

                mViewPager.setCurrentItem(0);

                break;

            case R.id.tv_remotecontrol:

                mViewPager.setCurrentItem(1);

                break;


            case R.id.tv_recharge:

                mViewPager.setCurrentItem(2);

                break;


        }


    }

    class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mViewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;


        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(mViewLists.get(position));
            return mViewLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }
    }

    class ListViewT45M2Adapter extends BaseAdapter {

        String[] carId = new String[]{"1号小车", "2号小车", "3号小车", "4号小车"};

        @Override
        public int getCount() {
            return carId.length;
        }

        @Override
        public Object getItem(int position) {
            return carId[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = View.inflate(context, R.layout.item_t45m2, null);

            TextView btn_start = (TextView) view.findViewById(R.id.tv_start);
            TextView btn_stop = (TextView) view.findViewById(R.id.tv_stop);

            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setCarAction(position + 1, "Start");

                }


            });


            btn_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setCarAction(position + 1, "Stop");


                }
            });


            return view;
        }
    }

    class ListViewT45M3Adapter extends BaseAdapter {

        ArrayList<CarBean> CarList;

        public ListViewT45M3Adapter(ArrayList<CarBean> CarList) {

            this.CarList = CarList;

        }


        @Override
        public int getCount() {
            return CarList.size();
        }

        @Override
        public Object getItem(int position) {
            return CarList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(context, R.layout.item_t45m3, null);

            TextView tv_id = (TextView) view.findViewById(R.id.tv_id);


            TextView tv_car_id = (TextView) view.findViewById(R.id.tv_car_id);

            TextView tv_money = (TextView) view.findViewById(R.id.tv_money);

            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);


            CarBean bean = CarList.get(position);


            tv_id.setText(position + "");
            tv_car_id.setText(bean.getCarId() + "");
            tv_money.setText(bean.getMoney() + "");
            tv_date.setText(bean.getDate());

            return view;
        }
    }


    private void setCarAction(int CarId, String action) {

        String params = "{\"CarId\":" + CarId + ", \"CarAction\":\"" + action + "\"}";

        new HttpAsyncUtils(Common.SET_CAR_MOVE, params) {
            @Override
            public void success(String data) {


                Log.i("wk", "data==>" + data);

            }

            @Override
            public void error() {

            }


        };


    }


    class CarBean {

        private int CarId;
        private int Money;
        private String date;

        public int getCarId() {
            return CarId;
        }

        public void setCarId(int carId) {
            CarId = carId;
        }

        public int getMoney() {
            return Money;
        }

        public void setMoney(int money) {
            Money = money;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i("wk", "onPause==》");

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("wk", "onResume===>");
    }
}
