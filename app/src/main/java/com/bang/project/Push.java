package com.bang.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class Push extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.push);
        }
        public void create(View view) {
            show();
        }
        private void show() {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default");

            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentText("알림 제목");
            builder.setContentText("요즘 운동에 소홀해지셧네요");

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            builder.setLargeIcon(largeIcon);

            // 알림 소리 uri 가져오기
            Uri ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(ringtoneUri);

            // 채널 id 등록
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
            }
            manager.notify(1,builder.build());
        }

        public void remove(View view) {
            hide();
        }

        private void hide() {
            NotificationManagerCompat.from(this).cancel(1);
        }
    }