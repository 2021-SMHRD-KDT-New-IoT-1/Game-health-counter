package com.bang.project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class MyService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;


//1. onCreate() : 최초 생성되었을때 한번 실행됩니다.
//2. onStartComand() : 백그라운드에서 실행되는 동작들이 들어가는 곳입니다.
//3. onDestroy() : 서비스가 종료될 때 실행되는 함수가 들어갑니다.

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler, getApplicationContext());
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent(MyService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            // msg 1
            if(msg.arg1 == 1){
                NotificationChannel channel = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    channel = new NotificationChannel("channel_id", "channel_name", importance);
                    channel.setDescription("channel description");
                    Notifi_M.createNotificationChannel(channel);

                    Notifi = new Notification.Builder(getApplicationContext(), "channel_id")
                            .setContentTitle("건강을자바조")
                            .setContentText("요즘 저한테 소홀해지셨네요..")
                            .setTicker("알림!!!")
                            .setSmallIcon(R.drawable.quest_check)
                            .setContentIntent(pendingIntent)
                            .build();
                }

                //알림 소리를 한번만 내도록
                Notifi.flags = Notification.FLAG_ONLY_ALERT_ONCE;

                //확인하면 자동으로 알림이 제거 되도록
                Notifi.flags = Notification.FLAG_AUTO_CANCEL;

                Notifi_M.notify( 777 , Notifi);

                //토스트 띄우기
                //Toast.makeText(MyService.this, "뜸?", Toast.LENGTH_LONG).show();
            }

        }

    };



}