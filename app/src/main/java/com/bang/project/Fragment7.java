package com.bang.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
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

// 스텟창
public class Fragment7 extends Fragment {
    private ImageView s_char;
    private TableLayout stat;
    private static  int SPLASH_SCREEN_TIMEOUT = 2200;

    // sharedpref 관련
    String req_user;

    // 통신관련
    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_Stat;
    String url_stat;

    // json 관련
//    private ArrayList<StatVO> data = new ArrayList<>();
    private StatVO data;
    JSONArray jsonArray;

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
        View v = inflater.inflate(R.layout.fragment_7, container, false);

        s_char = v.findViewById(R.id.main_char);
        stat = v.findViewById(R.id.stat);

        pull_cnt = v.findViewById(R.id.pull_cnt);
        squart_cnt = v.findViewById(R.id.squart_cnt);
        pull_cnt = v.findViewById(R.id.pull_cnt);
        push_tcnt = v.findViewById(R.id.push_tcnt);
        squart_tcnt = v.findViewById(R.id.squart_tcnt);
        pull_tcnt = v.findViewById(R.id.pull_tcnt);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);
        req_user = spf.getString("user", "unknown");


        Animation left = AnimationUtils.loadAnimation(getContext(),R.anim.left);
        s_char.startAnimation(left);

        Animation alpha = AnimationUtils.loadAnimation(getContext(),R.anim.alpha);
        stat.startAnimation(alpha);

        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getActivity());
        // 2. 전송할 URL
        url_stat = "http://211.48.213.139:8081/final_project2/Stat"; // 스탯 서블릿

        stringRequest_Stat = new StringRequest(Request.Method.POST, url_stat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 넘어오는 값 확인용
                jsonRead(response);
                Toast.makeText(getActivity(), data.getPush_cnt()+"", Toast.LENGTH_LONG).show();

//                push_cnt.setText(data.getPush_cnt()+"");
//                squart_cnt.setText(data.getSquart_cnt()+"");
//                pull_cnt.setText(data.getPull_cnt()+"");
//
//                // 타임어택횟수 셋팅
//                push_tcnt.setText(data.getPush_tcnt()+"");
//                squart_tcnt.setText(data.getSquart_cnt()+"");
//                pull_tcnt.setText(data.getPull_tcnt()+"");



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

        // 클릭시 애니메이션 발동 리스너
        s_char.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation right = AnimationUtils.loadAnimation(getContext(),R.anim.right);
                s_char.startAnimation(right);

                Animation alpha_b = AnimationUtils.loadAnimation(getContext(),R.anim.alpha_b);
                stat.startAnimation(alpha_b);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment5()).commit();
                    }
                },SPLASH_SCREEN_TIMEOUT);

            }
        });

//        Toast.makeText(getActivity(), data.getPush_cnt()+"", Toast.LENGTH_SHORT).show();


        return v;
    }

    private void jsonRead(String response) {
        Gson gson = new Gson();
        data = gson.fromJson(response, StatVO.class);

//        Toast.makeText(getActivity(), data.getPush_cnt()+"갯수ㅜㅜㅜㅜㅜㅜ", Toast.LENGTH_SHORT).show();

//        Toast.makeText(getActivity(), data.getPush_tcnt()+"", Toast.LENGTH_SHORT).show();



    }
}