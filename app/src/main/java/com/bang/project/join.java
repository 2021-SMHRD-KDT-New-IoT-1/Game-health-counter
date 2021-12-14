package com.bang.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class join extends AppCompatActivity {

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_join;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        btn_submit = findViewById(R.id.signupbutton);

        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 2. 전송할 URL
        String url_join = "http://211.48.213.139:8081/final_project2/Join";

        stringRequest_join = new StringRequest(Request.Method.POST, url_join, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("")){
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                }else{
                    // response를 자바객체로 파싱 메서드 호출


//                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                    processResponse(response);

                    Toast.makeText(getApplicationContext(), response+"님 환영합니다!", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(login.this, character.class);

//                    intent.putExtra("test",response);
//                    intent.putExtra("char_name",char_vo.getC_name());
//                    intent.putExtra("char_lv",char_vo.getC_level());
//                    startActivity(intent);
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
                params.put("m_id", "bykk");
                params.put("m_pwd", "b");
                params.put("m_gender", "c");
                params.put("m_name", "d");
                params.put("m_nickname", "e");
                params.put("m_email", "f");
                params.put("m_phone", "g");
                params.put("m_push_yn", "h");

                return params;
            }
        };

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest_join);
            }
        });

        Spinner genderSpinner = (Spinner)findViewById(R.id.spinner_gender);
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

    } // onCreate()
}