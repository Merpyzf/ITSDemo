package com.example.administrator.itsdemo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.itsdemo.MainActivity;
import com.example.administrator.itsdemo.R;
import com.example.administrator.itsdemo.utils.HttpAsyncUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.administrator.itsdemo.Common.GET_CAR_ACCOUNT_BALANCE;

public class MyService extends Service
{
    private Handler handler = new Handler();
    private Notification notification;

    private Runnable runnable = new Runnable() {
        @Override
        public void run()
        {
            new HttpAsyncUtils(GET_CAR_ACCOUNT_BALANCE, "{\"CarId\":1, \"UserName\":\"Z0004\"}")
            {
                @Override
                public void success(String data)
                {
                    Log.i("main", "小车速度："+data);
                    int balance = 0;
                    try
                    {
                        JSONObject object = new JSONObject(data);
                        balance = object.getInt("Balance");
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    initNotification(balance);
                    manager.notify(100, notification);
                }

                @Override
                public void error()
                {
                    Log.i("main", "小车速度error");
                }
            };
            handler.postDelayed(this, 5000);
        }
    };
    private NotificationManager manager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        handler.post(runnable);

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void initNotification(int balance)
    {

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("小车余额");
        builder.setContentText("小车余额为"+balance);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("page", "t31");
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 200, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        notification = builder.getNotification();
    }
}
