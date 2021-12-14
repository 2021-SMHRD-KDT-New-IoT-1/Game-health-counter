package com.bang.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText edt_id;
    EditText edt_pwd;

    Button btn_login;
    Button btn_join;

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_login;

//    CharVO char_vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout 갈아 끼우는 곳임
        setContentView(R.layout.activity_login);

        edt_id = findViewById(R.id.edt_id);
        edt_pwd = findViewById(R.id.edt_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_join = findViewById(R.id.btn_join);

        // SharedPreferences 초기화
        SharedPreferences spf = getSharedPreferences("loginSPF", Context.MODE_PRIVATE);

        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 2. 전송할 URL
        String url_login = "http://211.48.213.139:8081/final_project2/Login";

        stringRequest_login = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("성공")){
                    // 사용자 아이디 가져오기(안드 내부에서)
                    String user = edt_id.getText().toString();

                    // SharedPreferences 에디터 열어서 값 put해주기
                    SharedPreferences.Editor edit = spf.edit();
                    edit.putString("user", user);
                    edit.commit();

                    Toast.makeText(getApplicationContext(), user+"님 환영합니다!", Toast.LENGTH_LONG).show();

                    // intent로 화면 넘기기
                    Intent intent = new Intent(login.this, character.class);
                    startActivity(intent);
                    // response를 자바객체로 파싱 메서드 호출
//                    processResponse(response);
                }else{
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LoginError", error.toString());
            }
        }){
            @Nullable
            @Override
            // 안드에서 패러미터를 이용하여 이클립스 자바 서블릿(Login.java)으로 요청하는 부분
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", edt_id.getText().toString());
                params.put("m_pwd", edt_pwd.getText().toString());

                return params;
            }
        };

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest_login);
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, join.class);
                startActivity(intent);
            }
        });



    } // onCreate()

//    private void processResponse(String response) {
//        // gson을 이용해 자바 객체로 파싱
//        Gson gson = new Gson();
////        Gson gson = new GsonBuilder().setDateFormat("MM dd, yyyy HH:mm:ss").create();
//        char_vo = gson.fromJson(response, CharVO.class);
//
//    }
}

