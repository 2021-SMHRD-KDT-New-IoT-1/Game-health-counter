package com.bang.project;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;
    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    int msg = 0;

//    boolean ischecked;

    public ServiceThread(Handler handler, Context context){
        this.context = context;
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            try{
                Thread.sleep(1000*60*60*3); //3시간에 한번씩 알람온다.
            }catch (Exception e) {}

            //requestQueue = Volley.newRequestQueue(context);
            // 현재 날짜/시간
            Date now = Calendar.getInstance().getTime();
            // 포맷팅 정의
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            // 포맷팅 적용
            String formatedNow = formatter.format(now);
            Message msg = new Message();

            // 알림 띄울지 안띄울지 띄울거면 1 안띄울거면 0
            msg.arg1 = 1;

            handler.sendMessage(msg);//쓰레드에 있는 핸들러에게 메세지를 보냄

        }
    }


}