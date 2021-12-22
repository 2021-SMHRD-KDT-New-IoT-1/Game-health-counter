package com.bang.project;

import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.media.Image;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.WindowManager;
        import android.view.animation.AccelerateInterpolator;
        import android.view.animation.AlphaAnimation;
        import android.view.animation.Animation;
        import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private static  int SPLASH_SCREEN_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);

        setContentView(R.layout.activity_main);

        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1500);

        image = findViewById(R.id.imageView11);

        image.setAnimation(fadeOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);
    }

}