package com.bang.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fragment3 extends Fragment {
    ArrayList<RaidVO> al = new ArrayList<>();
    RequestQueue requestQueue_raid; // 전송통로
    StringRequest stringRequest_raid;
    RequestQueue requestQueue_info;
    StringRequest stringRequest_info;
    RaidVO raidvo;
    private JSONArray jsonArray;
    ArrayList<RaidVO> raidAl = new ArrayList<>();

    ConstraintLayout constaintLayout_pull;
    ConstraintLayout constaintLayout_sqt;
    ConstraintLayout constaintLayout_push;
    AlertDialog.Builder builder;
    private Button btn_giveUp;

    TextView tv_date;
    TextView tv_allScore;
    TextView tv_score;
    ProgressBar bar_stat;

    String myScore;
    String allScore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_3, container, false);
        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);


        btn_giveUp = v.findViewById(R.id.btn_giveUp);
        builder = new AlertDialog.Builder(getActivity());




        v.findViewById(R.id.constaintLayout_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                builder = new AlertDialog.Builder(getActivity());
                constaintLayout_pull = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
                builder.setView(constaintLayout_pull);
                tv_date = constaintLayout_pull.findViewById(R.id.tv_date);
                tv_allScore = constaintLayout_pull.findViewById(R.id.tv_allScore);
                bar_stat = constaintLayout_pull.findViewById(R.id.bar_stat);
                tv_score = constaintLayout_pull.findViewById(R.id.tv_score);


                requestQueue_info.add(stringRequest_info);
                Calendar cal = Calendar.getInstance();
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                cal.setTime(raidAl.get(0).getReg_date());

                String startDate = df.format(raidAl.get(0).getReg_date()).toString();
                cal.add(Calendar.DATE, 3);
                String endDate = df.format(cal.getTime()).toString();

                tv_date.setText("참여기간 : "+startDate+" - "+endDate);

                AlertDialog temp = builder.create();

                constaintLayout_pull.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.dismiss();
                    }
                });
                temp.show();
            }
        });

        v.findViewById(R.id.constaintLayout_sqt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                constaintLayout_sqt = (ConstraintLayout) inflater.inflate(R.layout.popup2, null);
                builder.setView(constaintLayout_sqt);
                AlertDialog temp = builder.create();
                constaintLayout_sqt.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.dismiss();
                    }
                });
                temp.show();
            }

        });

        v.findViewById(R.id.constaintLayout_push).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                constaintLayout_push = (ConstraintLayout) inflater.inflate(R.layout.popup3, null);
                builder.setView(constaintLayout_push);
                AlertDialog temp = builder.create();
                constaintLayout_push.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.dismiss();
                    }
                });
                temp.show();
            }
        });

        requestQueue_info = Volley.newRequestQueue(getContext());
        // 2. 전송할 URL
        String url_info = "http://211.63.240.51:8087/final_project2/AppRaidInfo";

        stringRequest_info = new StringRequest(Request.Method.POST, url_info, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String data[] = response.split("-");
                myScore = data[0];
                allScore = data[1];
                tv_allScore.setText(allScore+" / "+raidAl.get(0).getRaid_cnt().toString());
                tv_score.setText("내 기여 횟수 : "+myScore+"회");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LoginError", error.toString());
            }
        }) {
            @Nullable
            @Override
            // 안드에서 패러미터를 이용하여 이클립스 자바 서블릿(Login.java)으로 요청하는 부분
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user", "unknown"));
                params.put("raid_seq", raidAl.get(0).getRaid_seq());
                return params;
            }
        };


        requestQueue_raid = Volley.newRequestQueue(getContext());
        // 2. 전송할 URL
        String url_raid = "http://211.63.240.51:8087/final_project2/RaidInfo";

        stringRequest_raid = new StringRequest(Request.Method.POST, url_raid, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.v("asdf", response);
                jsonRead(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LoginError", error.toString());
            }
        }) {
            @Nullable
            @Override
            // 안드에서 패러미터를 이용하여 이클립스 자바 서블릿(Login.java)으로 요청하는 부분
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user", "unknown"));

                return params;
            }
        };

        requestQueue_raid.add(stringRequest_raid);

        return v;

    }

    private void jsonRead(String response) {
        try {
            jsonArray = new JSONArray(response);
            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            for (int i = 0; i < jsonArray.length(); i++) {
                // raidAl.add(gson.fromJson(jsonArray.getJSONObject(i).toString() , RaidVO.class));
                raidAl.add(gson.fromJson(jsonArray.get(i).toString(), RaidVO.class));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }


}