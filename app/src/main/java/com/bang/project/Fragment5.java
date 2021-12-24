package com.bang.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

// 캐릭터 탭(메인화면)
public class Fragment5 extends Fragment {
    private ImageView main_char;

    // sharedpref 관련
    String req_user;

    // 통신관련
    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_Stat;
    String url_stat;

    // json 관련
//    private ArrayList<StatVO> data = new ArrayList<>();
//    JSONArray jsonArray;
    private StatVO data;

    // 누적횟수
    TextView push_cnt;
    TextView squart_cnt;
    TextView pull_cnt;

    // 타임어택횟수
    TextView push_tcnt;
    TextView squart_tcnt;
    TextView pull_tcnt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_5, container, false);

        main_char = v.findViewById(R.id.main_char);

        push_cnt = v.findViewById(R.id.push_cnt);
        squart_cnt = v.findViewById(R.id.squart_cnt);
        pull_cnt = v.findViewById(R.id.pull_cnt);
        push_tcnt = v.findViewById(R.id.push_tcnt);
        squart_tcnt = v.findViewById(R.id.squart_tcnt);
        pull_tcnt = v.findViewById(R.id.pull_tcnt);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);
        req_user = spf.getString("user", "unknown");

        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getActivity());
        // 2. 전송할 URL
        url_stat = "http://211.48.213.139:8081/final_project2/Stat"; // 스탯 서블릿

        stringRequest_Stat = new StringRequest(Request.Method.POST, url_stat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                jsonRead(response);
                // 넘어오는 값 확인용
//                Toast.makeText(getActivity(), data.getPush_cnt()+"", Toast.LENGTH_LONG).show();

                push_cnt.setText(data.getPush_cnt()+"회");
                squart_cnt.setText(data.getSquart_cnt()+"회");
                pull_cnt.setText(data.getPull_cnt()+"회");

                // 타임어택횟수 셋팅
                push_tcnt.setText(data.getPush_tcnt()+"회");
                squart_tcnt.setText(data.getSquart_cnt()+"회");
                pull_tcnt.setText(data.getPull_tcnt()+"회");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("StatError", error.toString());
            }
        }){
            @Nullable
            @Override
            // 안드에서 패러미터를 이용하여 이클립스 자바 서블릿(Login.java)으로 요청하는 부분
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", req_user);

                return params;
            }
        };
        requestQueue.add(stringRequest_Stat);


//        main_char.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment7()).commit();
//            }
//        });

        return v;
    }

    private void jsonRead(String response) {
        Gson gson = new Gson();
        data = gson.fromJson(response, StatVO.class);

    }
}