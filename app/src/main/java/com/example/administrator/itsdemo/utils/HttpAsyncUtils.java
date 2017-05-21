package com.example.administrator.itsdemo.utils;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public abstract class HttpAsyncUtils {


    private String url;

    private String params;

    private  OkHttpClient client = new OkHttpClient();



    private Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 0:


                        success((String) msg.obj);


                    break;

                case 1:


                    error();


                    break;



                default:
                    break;


            }

        }
    };


    public HttpAsyncUtils(String url, String params) {
        this.url = url;
        this.params = params;
        post();
    }

    private void get(){
        Request request = new Request.Builder().url(url).build();
        doCall(request);
    }

    private void post(){
        Request request = new Request.Builder().url(url).post(
                RequestBody.create(MediaType.parse("application/json"),params)
        ).build();
        doCall(request);
    }

    private void doCall(Request request){

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {


                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);

            }

            @Override
            public void onResponse(Response response) throws IOException {


                Message msg = mHandler.obtainMessage();
                msg.obj = response.body().string();
                msg.what = 0;
                mHandler.sendMessage(msg);


            }
        });
    }

    public abstract void success(String data);

    public abstract void error();

}
