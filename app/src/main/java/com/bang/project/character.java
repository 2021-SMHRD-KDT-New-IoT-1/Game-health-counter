package com.bang.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;

public class character extends AppCompatActivity {
    private ImageView main_logo;

    FrameLayout frameLayout;
    BottomNavigationView bnView;

    TextView tv_nick;
    TextView tv_level;
    ProgressBar bar_exp;
    ImageView main_char;

    int user_level;

    int preSelect=3;

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_CharInfo;
    RequestQueue requestQueue_exp;
    StringRequest stringRequest_exp;
    private int exp = 0;

    // 캐릭터 정보 변수들
    private int result_lv = 1;
    //    private String result_nick;
    private int result_exp;


    ImageButton btn_home;
    ImageButton mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);


//        btnEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Service 끝",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this,MyService.class);
//                stopService(intent);
//            }
//        });



        // fragment 사용시 필요한 레이아웃, 버튼뷰
        frameLayout = findViewById(R.id.layout);
        bnView = findViewById(R.id.bnView);

        // 이미지 버튼(홈으로 이동)
   //     btn_home = findViewById(R.id.btn_home);
        mypage = findViewById(R.id.mypage);

        main_logo = findViewById(R.id.main_logo);
        tv_nick = findViewById(R.id.tv_nick);
        tv_level = findViewById(R.id.tv_level);

        bar_exp = findViewById(R.id.bar_exp);

        // SharedPreferences
        SharedPreferences spf = getSharedPreferences("UserSPF", Context.MODE_PRIVATE);
        user_level = spf.getInt("level", 0);

        if(user_level >= 10) {
            main_logo.setImageResource(R.drawable.logo3_alpha);
        }
        else if(user_level >= 5) {
            main_logo.setImageResource(R.drawable.logo2_alpha);
        }
        else if(user_level >= 1) {
            main_logo.setImageResource(R.drawable.logo1_alpha);
        }

        // 메인화면 프레그먼트 셋팅(캐릭터로)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout, new Fragment5()).commit();

        // *** 서버 request 및 response 부분 시작
        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 2. 전송할 URL
        String url_CharInfo = "http://211.48.213.139:8081/final_project2/CharInfo";

        stringRequest_CharInfo = new StringRequest(Request.Method.POST, url_CharInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                tv_nick.setText(response);

                // SharedPreferences 에디터 열어서 닉값 put해주기
                SharedPreferences.Editor edit = spf.edit();
                edit.putString("nick", response);
                edit.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user", "unknown"));

                return params;
            }
        };
        requestQueue.add(stringRequest_CharInfo);
        // 끝 ***















        // 홈 버튼 리스너
//        btn_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.layout, new Fragment5()).commit();
//                bnView.setSelectedItemId(R.id.invisible);
//            }
//
//        });


        // 마이페이지 클릭리스너
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment6()).commit();
            }


        });

        bnView.setSelectedItemId(R.id.tab5);

        // 아이템 선택시 반응하는 리스너
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.tab1){
                    //1번 메뉴를 클릭했다면
//                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit).commit();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                    if (preSelect > 1){
                        // 오른쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment1());
                        fragmentTransaction.commit();

                    }else{
                        // 왼쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment1());
                        fragmentTransaction.commit();
                    }

                    preSelect = 1;



                } else if(item.getItemId()==R.id.tab2){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (preSelect > 2){
                        // 오른쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment2());
                        fragmentTransaction.commit();

                    }else{
                        // 왼쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment2());
                        fragmentTransaction.commit();
                    }
                    preSelect = 2;

                }else if(item.getItemId()==R.id.tab3){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment3()).commit();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (preSelect > 4){
                        // 오른쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment3());
                        fragmentTransaction.commit();

                    }else{
                        // 왼쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment3());
                        fragmentTransaction.commit();
                    }
                    preSelect = 3;

                }else if(item.getItemId()==R.id.tab4){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment4()).commit();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (preSelect > 4){
                        // 오른쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment4());
                        fragmentTransaction.commit();

                    }else{
                        // 왼쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment4());
                        fragmentTransaction.commit();
                    }
                    preSelect = 4;
                }else if(item.getItemId()==R.id.tab5){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment4()).commit();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if (preSelect > 3){
                        // 오른쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_left_enter, R.anim.slide_left_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment5());
                        fragmentTransaction.commit();

                    }else{
                        // 왼쪽에서 왔음
                        fragmentTransaction.setCustomAnimations(R.anim.slide_right_enter, R.anim.slide_right_exit, R.anim.slide_right_enter, R.anim.slide_right_exit);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.layout,new Fragment5());
                        fragmentTransaction.commit();
                    }
                    preSelect = 5;
                }

                return true;
            }
        });

        // 경험치 가져오기
        requestQueue_exp = Volley.newRequestQueue(getApplicationContext());
        // 2. 전송할 URL
        String url_exp = "http://211.48.213.139:8081/final_project2/getExp";


        stringRequest_exp = new StringRequest(Request.Method.POST, url_exp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("-1")) {
                    Toast.makeText(getApplicationContext(), "서버가 불안정합니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    exp = Integer.parseInt(response);

                    if (exp > 0) {
                        //백분율로 나누기
                        result_lv = exp / 100 + 1;
                        result_exp = exp % 100;
                    } else {
                        result_lv = 1;
                        result_exp = 0;
                    }
                    // 경험치바
                    // 퍼센트(백분율)로 들어감
                    bar_exp.setProgress(result_exp);
                    tv_level.setText("Lv " + result_lv);

                    SharedPreferences.Editor edit = spf.edit();
                    edit.putInt("level", result_lv);
                    edit.commit();



                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user", "unknown"));
                return params;
            }
        };
        requestQueue_exp.add(stringRequest_exp);
    } // onCreate()

}