package com.bang.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

    // 회원가입 양식
    EditText sign_id;
    EditText sign_pwd;
    EditText sign_name;
    Spinner sign_gender;
    EditText sign_nickname;
    EditText sign_email;
    EditText sign_phone;
    RadioGroup sign_push_yn;

    // 제출 버튼
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // 버튼
        btn_submit = findViewById(R.id.btn_submit);

        // 회원가입 양식
        sign_id = findViewById(R.id.sign_id);
        sign_pwd = findViewById(R.id.sign_pwd);
        sign_name = findViewById(R.id.sign_name);
        sign_gender = findViewById(R.id.sign_gender);
        sign_nickname = findViewById(R.id.sign_nickname);
        sign_email = findViewById(R.id.sign_email);
        sign_phone = findViewById(R.id.sign_phone);
        sign_push_yn = findViewById(R.id.rg1);

        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 2. 전송할 URL
        String url_join = "http://211.48.213.139:8081/final_project2/Join";

        stringRequest_join = new StringRequest(Request.Method.POST, url_join, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("성공")){
                    Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_LONG).show();

                    // intent로 화면 넘기기(로그인 창으로 되돌아감)
                    Intent intent = new Intent(join.this, login.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "빈칸을 모두 입력해주세요", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("JoinError", error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 라디오 그룹에서 선택된 라디오버튼 ID 얻어오기
                RadioButton rb1 = findViewById(sign_push_yn.getCheckedRadioButtonId());

                HashMap<String, String> params = new HashMap<>();

                params.put("m_id", sign_id.getText().toString());
                params.put("m_pwd", sign_pwd.getText().toString());
                params.put("m_gender", sign_gender.getSelectedItem().toString());
                params.put("m_name", sign_name.getText().toString());
                params.put("m_nickname", sign_nickname.getText().toString());
                params.put("m_email", sign_email.getText().toString());
                params.put("m_phone", sign_phone.getText().toString());
                params.put("m_push_yn", rb1.getText().toString());

                return params;
            }
        };

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue.add(stringRequest_join);
            }
        });
    
        // 스피너
        Spinner genderSpinner = (Spinner)findViewById(R.id.sign_gender);
        ArrayAdapter genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

    } // onCreate()
}