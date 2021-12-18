package com.bang.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private ArrayList<RaidVO> al = new ArrayList<>();
    private RequestQueue requestQueue_raid; // 전송통로
    private StringRequest stringRequest_raid;
    RequestQueue requestQueue_info;
    StringRequest stringRequest_info;
    RaidVO raidvo;
    private JSONArray jsonArray;
    private ArrayList<RaidVO> raidAl = new ArrayList<>();

    private ConstraintLayout constaintLayout_pull;
    private ConstraintLayout constaintLayout_sqt;
    private ConstraintLayout constaintLayout_push;
    private AlertDialog.Builder builder;
    private TextView tv_applier;
    private TextView tv_applier2;
    private TextView tv_applier3;
    private Button btn_giveUp;

    private TextView tv_date;
    private TextView tv_allScore;
    private TextView tv_score;
    private ProgressBar bar_stat;

    private String myScore;
    private String allScore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container, false);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);



        btn_giveUp = v.findViewById(R.id.btn_giveUp);
        tv_applier = v.findViewById(R.id.tv_applier);
        tv_applier2 = v.findViewById(R.id.tv_applier2);
        tv_applier3 = v.findViewById(R.id.tv_applier3);
        builder = new AlertDialog.Builder(getActivity());




        //====================================통신===========================================

        requestQueue_info = Volley.newRequestQueue(getContext());
        // 2. 전송할 URL
        String url_info = "http://211.63.240.51:8087/final_project2/AppRaidInfo";

        stringRequest_info = new StringRequest(Request.Method.POST, url_info, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String data[] = response.split("#");
                myScore = data[0];
                allScore = data[1];
                tv_allScore.setText(allScore + " / " + raidAl.get(0).getRaid_cnt().toString());
                tv_score.setText("내 기여 횟수 : " + myScore + "회");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LoginError", error.toString());
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user", "unknown"));
                params.put("raid_seq", raidAl.get(0).getRaid_seq());
                return params;
            }
        };

        requestQueue_raid = Volley.newRequestQueue(getContext());
        String url_raid = "http://211.63.240.51:8087/final_project2/RaidInfo";


        stringRequest_raid = new StringRequest(Request.Method.POST, url_raid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.v("asdf", response);
                jsonRead(response);
                if (raidAl.get(0).getCheck().equals("true")) {
                    tv_applier.setText("참가중");
                } else {
                    tv_applier.setText("참가하기");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("LoginError", error.toString());
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("m_id", spf.getString("user", "unknown"));

                return params;
            }
        };
        requestQueue_raid.add(stringRequest_raid);


        if(tv_applier.getText().toString().equals("참가중")){
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

                    tv_date.setText("참여기간 : " + startDate + " - " + endDate);

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
        }else if(tv_applier.getText().toString().equals("참가하기")){
            v.findViewById(R.id.constaintLayout_pull).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("레이드에 참가하시겠습니까?");
                    builder.setPositiveButton("아니오", null);
                    builder.setNegativeButton("참가하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            raidAl.get(0).setCheck("true");
                            tv_applier.setText("참가중");
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

                            tv_date.setText("참여기간 : " + startDate + " - " + endDate);

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
                    builder.create().show();
                }
            });
        }//else 끝

        if(tv_applier2.getText().toString().equals("참가중")){
            v.findViewById(R.id.constaintLayout_sqt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //tv_applier.setText("참여중");
                    builder = new AlertDialog.Builder(getActivity());
                    constaintLayout_sqt = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
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
        }//if끝
        else{
            v.findViewById(R.id.constaintLayout_sqt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("레이드에 참가하시겠습니까?");
                    builder.setPositiveButton("아니오", null);
                    builder.setNegativeButton("참가하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            raidAl.get(0).setCheck("true");
                            tv_applier2.setText("참가중");
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
                    builder.create().show();
                }
            });
        }//else 끝

        if(tv_applier3.getText().toString().equals("참가중")){
            v.findViewById(R.id.constaintLayout_push).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //tv_applier.setText("참여중");
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
        }//if끝
        else{
            v.findViewById(R.id.constaintLayout_push).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("레이드에 참가하시겠습니까?");
                    builder.setPositiveButton("아니오", null);
                    builder.setNegativeButton("참가하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            raidAl.get(0).setCheck("true");
                            tv_applier3.setText("참가중");
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
                    builder.create().show();
                }
            });
        }//else 끝



        return v;
    }//onCreateView 중괄호

    private void jsonRead (String response){
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

}//끝 중괄호