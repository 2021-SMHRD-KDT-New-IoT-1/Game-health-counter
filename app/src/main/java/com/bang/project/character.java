package com.bang.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

    FrameLayout frameLayout;
    BottomNavigationView bnView;

    TextView tv_nick;
    TextView tv_level;
    ProgressBar bar_exp;
    ImageView main_char;

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_CharInfo;
    RequestQueue requestQueue_exp;
    StringRequest stringRequest_exp;
    private int exp = 0;

    // 캐릭터 정보 변수들
    private int result_lv = 1;
    private int result_exp;
    private String result_nick;


    ImageButton btn_home;
    ImageButton mypage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        // SharedPreferences
        SharedPreferences spf = getSharedPreferences("UserSPF", Context.MODE_PRIVATE);

        // fragment 사용시 필요한 레이아웃, 버튼뷰
        frameLayout = findViewById(R.id.layout);
        bnView = findViewById(R.id.bnView);

        // 이미지 버튼(홈으로 이동)
        btn_home = findViewById(R.id.btn_home);
        mypage = findViewById(R.id.mypage);

        tv_nick = findViewById(R.id.tv_nick);
        tv_level = findViewById(R.id.tv_level);

        bar_exp = findViewById(R.id.bar_exp);


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

                String[] result = response.split(",");

                //tv_level.setText("Lv "+result[1]);
                result_nick = result[0];
                tv_nick.setText(result_nick);
//                bar_exp.setText(result[2]+"회");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
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
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout, new Fragment5()).commit();
            }
        });

        // 마이페이지 클릭리스너
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout,new Fragment6()).commit();
            }
        });


        // 아이템 선택시 반응하는 리스너
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.tab1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment1()).commit();
                }

                else if(item.getItemId() == R.id.tab2) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment2()).commit();
                }

                else if(item.getItemId() == R.id.tab3) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment3()).commit();
                }

                else if(item.getItemId() == R.id.tab4) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment4()).commit();
                }

                return true;
            }
        });

        // 경험치 가져오기
        requestQueue_exp = Volley.newRequestQueue(getApplicationContext());
        // 2. 전송할 URL
        String url_exp = "http://211.63.240.51:8087/final_project2/getExp";


        stringRequest_exp = new StringRequest(Request.Method.POST, url_exp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("-1")){
                    Toast.makeText(getApplicationContext(),"서버가 불안정합니다. 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    exp = Integer.parseInt(response);

                    if(exp > 0) {
                        //백분율로 나누기
                        result_lv = exp / 100;
                        result_exp = exp % 100;
                    }else{
                        result_lv = 1;
                        result_exp = 0;
                    }
                    // 경험치바
                    // 퍼센트(백분율)로 들어감
                    bar_exp.setProgress(result_exp);
                    tv_level.setText("Lv "+result_lv);

                    // SharedPreferences 에디터 열어서 값 put해주기
                    SharedPreferences.Editor edit = spf.edit();
                    edit.putString("result_lv", result_lv+"");
                    edit.putString("result_nick", result_nick);
                    edit.putString("result_exp", result_exp+"");
                    edit.commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user","unknown"));
                return params;
            }
        };
        requestQueue_exp.add(stringRequest_exp);
    } // onCreate()

}