package com.example.administrator.itsdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by 春水碧于天 on 2017/5/13.
 */

public class WangkeUtils
{


    /**
     * 判断网络是否可用
     *
     * @param context
     * @return true, false
     */
    public static boolean isNetWorkAvailable(Context context)
    {

        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectManager.getActiveNetworkInfo();


        return (info != null && info.isAvailable());

    }

    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getIpAddress()
    {
        String ipaddress = "";
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); )
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                     enumIpAddr.hasMoreElements(); )
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        ipaddress = inetAddress.getHostAddress().toString();
                        //过滤掉ipv6
                        if (!ipaddress.contains("::"))
                        {//ipV6的地址
                            return ipaddress;
                        }
                    }
                }
            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        return ipaddress;
    }
}