package com.bang.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

// 기록 탭
public class Fragment1 extends Fragment {

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_Athle;
    CalendarView calendarView;

    String date_result;
    String date;

    TextView tv_squart;
    TextView tv_push;
    TextView tv_pull;

    String url_athle;
    String req_user;


    // 통신 메서드
    public void server(String date) {
        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getActivity());
        // 2. 전송할 URL
        url_athle = "http://211.48.213.139:8081/final_project2/Athle";

        stringRequest_Athle = new StringRequest(Request.Method.POST, url_athle, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // 넘어오는 값 확인용
//                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

                String[] result = response.split(",");

                tv_squart.setText(result[0]+"회");
                tv_push.setText(result[1]+"회");
                tv_pull.setText(result[2]+"회");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("AthleError", error.toString());
            }
        }){
            @Nullable
            @Override
            // 안드에서 패러미터를 이용하여 이클립스 자바 서블릿(Login.java)으로 요청하는 부분
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("a_date", date);
                params.put("m_id", req_user);

                return params;
            }
        };
        requestQueue.add(stringRequest_Athle);
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);


        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        date = format.format(Calendar.getInstance().getTime());

        tv_squart = v.findViewById(R.id.tv_squart);
        tv_push = v.findViewById(R.id.tv_push);
        tv_pull = v.findViewById(R.id.tv_pull);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);
        req_user = spf.getString("user", "unknown");

        server(date);

        calendarView = v.findViewById(R.id.calendarView);
        // 캘린더뷰 리스너
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                date_result = ""+year+(month+1)+dayOfMonth;
                // 날짜 잘찍히는지 확인용 ex) 20211215 이런식으로 출력됨
//                Toast.makeText(getActivity(), date_result, Toast.LENGTH_SHORT).show();

                server(date_result);


            }
        });

        return v;

    }



}