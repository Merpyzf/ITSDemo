package com.example.administrator.itsdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.itsdemo.fragment.AccountPayFragment;
import com.example.administrator.itsdemo.fragment.BusQueryTwoFragment;
import com.example.administrator.itsdemo.fragment.CarManagerFragment;
import com.example.administrator.itsdemo.fragment.GuideFragment;
import com.example.administrator.itsdemo.fragment.LightMonitorFragment;
import com.example.administrator.itsdemo.fragment.LightSortFragment;
import com.example.administrator.itsdemo.fragment.LightTimeFragment;
import com.example.administrator.itsdemo.fragment.LucencyDialogFragment;
import com.example.administrator.itsdemo.fragment.MySensorFragment;
import com.example.administrator.itsdemo.fragment.NoticeTightFragment;
import com.example.administrator.itsdemo.fragment.ParkLogFragment;
import com.example.administrator.itsdemo.fragment.ParkRecordFragment;
import com.example.administrator.itsdemo.fragment.QueryCarBalanceFragment;
import com.example.administrator.itsdemo.fragment.RechargeCarFragment;
import com.example.administrator.itsdemo.fragment.RechargeHistoryFragment;
import com.example.administrator.itsdemo.fragment.RoadLightManagerFragment;
import com.example.administrator.itsdemo.fragment.RoadQueryFragment;
import com.example.administrator.itsdemo.fragment.RoadStatusFragment;
import com.example.administrator.itsdemo.fragment.SensorHistoryFragment;
import com.example.administrator.itsdemo.fragment.SetCarMoneyFragment;
import com.example.administrator.itsdemo.fragment.SetCarSpeedFragment;
import com.example.administrator.itsdemo.fragment.T33Fragment;
import com.example.administrator.itsdemo.fragment.T34Fragment;
import com.example.administrator.itsdemo.fragment.T35Fragment;
import com.example.administrator.itsdemo.fragment.T36Fragment;
import com.example.administrator.itsdemo.fragment.T37Fragment;
import com.example.administrator.itsdemo.fragment.T38Fragment;
import com.example.administrator.itsdemo.fragment.T39Fragment;
import com.example.administrator.itsdemo.fragment.T40Fragment;
import com.example.administrator.itsdemo.fragment.T42Fragment;
import com.example.administrator.itsdemo.fragment.T43Fragment;
import com.example.administrator.itsdemo.fragment.T44Fragment;
import com.example.administrator.itsdemo.fragment.T45Fragment;
import com.example.administrator.itsdemo.fragment.T47Fragment;
import com.example.administrator.itsdemo.fragment.T48Fragment;
import com.example.administrator.itsdemo.fragment.TrafficControlFragment;
import com.example.administrator.itsdemo.fragment.TravelAdviceFragment;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;
import com.example.administrator.itsdemo.utils.WangkeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.administrator.itsdemo.R.id.index_sliding;

public class MainActivity extends FragmentActivity {

    private TextView tv_title;

    private SlidingPaneLayout slidingPane;

    private SimpleAdapter adapter;

    private ListView listview;

    private String[] name;

    private int[] image;

    private boolean isExit = true;

    private Context mContext;

    private TextView ip;

    private Handler handler = new Handler();


    private static final String TAG = "MainActivity";
    private String hostIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        iniUI();

        iniEvent();

        boolean netWorkAvailable = WangkeUtils.isNetWorkAvailable(this);

        Log.i("wk", "网络连接是否可用==》" + netWorkAvailable);

        new HttpAsyncUtils(Common.SET_CAR_MOVE, "{\"CarId\":1, \"CarAction\":\"Stop\"}") {
            @Override
            public void success(String data) {
                Log.i(TAG, "success: " + data);
            }

            @Override
            public void error() {
                Log.i(TAG, "success: error");
            }
        };

    }

    private void iniEvent() {
        if (getIntent().getStringExtra("page") != null) {
            switch (getIntent().getStringExtra("page")) {
                case "t31":
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_index, new QueryCarBalanceFragment())
                            .commit();
                    break;

                case "t35":
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout_index, new MySensorFragment())
                            .commit();
                    break;
            }
        }

        hostIp = WangkeUtils.getIpAddress();
        handler.post(runnable);

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingPane.isOpen()) {
                    slidingPane.closePane();
                } else {
                    slidingPane.openPane();
                }
            }
        });
        tv_title.setText("智能交通");
        //设置菜单
        adapter = new SimpleAdapter(this, getData(), R.layout.item_menu,
                new String[]{"menu_image", "menu_content"},
                new int[]{R.id.item_menu_image, R.id.item_menu_text});
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (name[position]) {
                    case "1-系统的启动导航功能":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new GuideFragment())
                                .commit();
                        break;

                    case "2,3-实时环境指标动态显示":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new MySensorFragment())
                                .commit();
                        break;

                    case "4-充值历史记录":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new RechargeHistoryFragment())
                                .commit();
                        break;

                    case "5-传感器数据历史记录":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new SensorHistoryFragment())
                                .commit();
                        break;

                    case "6-设置账户阈值":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new SetCarMoneyFragment())
                                .commit();
                        break;

                    case "7-设置速度阈值":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new SetCarSpeedFragment())
                                .commit();
                        break;

                    case "10-道路状况查询":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new RoadStatusFragment())
                                .commit();

                        break;

                    case "11-停车日志查询":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new ParkLogFragment())
                                .commit();
                        break;

                    case "12-路灯管理控制":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new RoadLightManagerFragment())
                                .commit();

                        break;

                    case "13-车辆管理控制":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new CarManagerFragment())
                                .commit();

                        break;
                    case "14-光照监测":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new LightMonitorFragment())
                                .commit();

                        break;

                    case "15-半透明DIALOG":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new LucencyDialogFragment())
                                .commit();

                        break;

                    case "18-路况查询":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new RoadQueryFragment())
                                .commit();
                        break;

                    case "21-出行建议":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new TravelAdviceFragment())
                                .commit();
                        break;

                    case "22-更改路灯时长":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new LightTimeFragment())
                                .commit();
                        break;
                    case "23-公交查询":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new BusQueryTwoFragment())
                                .commit();
                        break;

                    case "24-停车记录":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new ParkRecordFragment())
                                .commit();
                        break;
                    case "25-账户支付安全":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new AccountPayFragment())
                                .commit();
                        break;

                    case "26-车辆限行":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new TrafficControlFragment())
                                .commit();
                        break;

                    case "27-通知打开路灯":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new NoticeTightFragment())
                                .commit();
                        break;

                    case "28-交通灯指示灯排序":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new LightSortFragment())
                                .commit();
                        break;

                    case "31-小车余额查询":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new QueryCarBalanceFragment())
                                .commit();
                        break;

                    case "32-小车充值":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new RechargeCarFragment())
                                .commit();
                        break;

                    case "33-主页面退出按钮防碰撞":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T33Fragment())
                                .commit();
                        break;

                    case "34-小车充值和余额查询":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T34Fragment())
                                .commit();
                        break;


                    case "35-客户端警告消息推送":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T35Fragment())
                                .commit();
                        break;

                    case "36-实现路灯控制功能":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T36Fragment())
                                .commit();
                        break;

                    case "37-实现车载人数统计功能":

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T37Fragment())
                                .commit();
                        break;

                    case "38-道路状态功能":

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T38Fragment())
                                .commit();
                        break;

                    case "39-系统主界面布局1":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T39Fragment())
                                .commit();
                        break;

                    case "40-系统主界面布局2":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T40Fragment())
                                .commit();
                        break;

                    case "42-编码实现服务器地址设置和国际化":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T42Fragment())
                                .commit();
                        break;

                    case "43-实现主界面的功能":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T43Fragment())
                                .commit();
                        break;

                    case "44-检查网络状态":
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T44Fragment())
                                .commit();
                        break;


                    case "45-我的座驾功能":

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T45Fragment())
                                .commit();
                        break;


                    case "47-路灯管理":

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T47Fragment())
                                .commit();
                        break;

                    case "48-折线图动态刷新":

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout_index, new T48Fragment())
                                .commit();


                        break;


                }

                slidingPane.closePane();
            }
        });
    }

    private void iniUI() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        slidingPane = (SlidingPaneLayout) findViewById(index_sliding);
        listview = (ListView) findViewById(R.id.list_menu);
        ip = (TextView) findViewById(R.id.main_tv_ip);
    }

    private ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        name = new String[]{"1-系统的启动导航功能", "2,3-实时环境指标动态显示", "4-充值历史记录",
                "5-传感器数据历史记录", "6-设置账户阈值",
                "7-设置速度阈值", "8-小车账户充值",
                "9-公交车信息查询", "10-道路状况查询",
                "11-停车日志查询", "12-路灯管理控制",
                "13-车辆管理控制", "14-光照监测",
                "15-半透明DIALOG", "18-路况查询",
                "21-出行建议", "22-更改路灯时长",
                "23-公交查询", "24-停车记录",
                "25-账户支付安全", "26-车辆限行",
                "27-通知打开路灯", "28-交通灯指示灯排序",
                "29-用户登陆", "31-小车余额查询", "32-小车充值",
                "33-主页面退出按钮防碰撞", "34-小车充值和余额查询",
                "35-客户端警告消息推送", "36-实现路灯控制功能",
                "37-实现车载人数统计功能", "38-道路状态功能",
                "39-系统主界面布局1", "40-系统主界面布局2",
                "42-编码实现服务器地址设置和国际化", "43-实现主界面的功能",
                "44-检查网络状态", "45-我的座驾功能", "47-路灯管理"};

        image = new int[]{R.mipmap.btn_l_slideshow};

        for (int i = 0; i < name.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("menu_image", R.mipmap.btn_l_slideshow);
            map.put("menu_content", name[i]);
            list.add(map);
        }

        return list;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                Toast.makeText(mContext, "再按一次退出系统", Toast.LENGTH_SHORT).show();
                isExit = false;

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = true;
                    }
                }, 3000);
            } else {
                finish();
            }
        }
        return true;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM/dd HH:mm-ss");
            String time = format.format(new Date());
            ip.setText("ip:" + hostIp + "\n" + time);
            handler.postDelayed(this, 1000);
        }
    };

}
