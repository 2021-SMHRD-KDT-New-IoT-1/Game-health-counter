package com.bang.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 2. 전송할 URL
        String url_login = "http://211.48.213.139:8081/final_project2/Login";

        stringRequest_login = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                }else{
                    // response를 자바객체로 파싱 메서드 호출


//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                    processResponse(response);

                    Toast.makeText(getApplicationContext(), response+"님 환영합니다!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(login.this, character.class);

//                    intent.putExtra("test",response);
//                    intent.putExtra("char_name",char_vo.getC_name());
//                    intent.putExtra("char_lv",char_vo.getC_level());
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("asdf", error.toString());
            }
        }){
            @Nullable
            @Override
            // 안드에서 패러미터를 이용하여 jsp 서블릿(Login.java)으로  요청 하는 부분
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

