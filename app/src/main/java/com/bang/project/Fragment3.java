package com.bang.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private RequestQueue requestQueue_info;
    private StringRequest stringRequest_info;
    private RequestQueue requestQueue_update;
    private StringRequest stringRequest_update;

    private RaidVO raidvo;
    private JSONArray jsonArray;
    private ArrayList<RaidVO> raidAl = new ArrayList<>();

    private ConstraintLayout constaintLayout_pull;
    private ConstraintLayout constaintLayout_sqt;
    private ConstraintLayout constaintLayout_push;
    private AlertDialog.Builder builder;
    private TextView tv_applier_pull;
    private TextView tv_applier_sqt;
    private TextView tv_applier_push;
//    private Button btn_giveUp;

    private TextView tv_date;
    private TextView tv_allScore;
    private TextView tv_score;
    private ProgressBar bar_stat;
    private TextView tv_cnt;

    private String allScore;
    private String totalCnt;
    int aS;
    int mS;
    int tC;
    private String myScore;


    private String tag;

    // 보스 이미지 파일들
    private ImageView iv_boss1;
    private ImageView iv_boss2;
    private ImageView iv_boss3;

    TextView tv_raid1;

    int barGauge;

    // 캘린더 날짜 포맷
    private Calendar cal = Calendar.getInstance();
    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

    // 값 세팅 메서드
    public void valueSet(int mS) {

        // 레이아웃 테두리 넣기 (보류)
//        if(myScore.equals("0")) {
//            constaintLayout_pull.setBackgroundResource(R.drawable.white_edittext);
//        } else {
//            constaintLayout_pull.setBackgroundResource(R.drawable.red_edittext);
//        }
        cal.setTime(raidAl.get(0).getReg_date());
        String startDate = df.format(raidAl.get(0).getReg_date()).toString();

        cal.add(Calendar.DATE, 3);
        String endDate = df.format(cal.getTime()).toString();

        totalCnt = raidAl.get(0).getRaid_cnt();
        tC = Integer.parseInt(totalCnt);

        barGauge = (int)((double)aS/tC*100);
//        Toast.makeText(getActivity(), barGauge+"", Toast.LENGTH_SHORT).show();
        // 임시 데이터
//        barGauge = 50;
        bar_stat.setProgress(barGauge);

        if(barGauge<50) {
            iv_boss1.setImageResource(R.drawable.boss1_1);
        }
        else if(barGauge == 100) {
            iv_boss1.setImageResource(R.drawable.boss1_die);
            tv_raid1.setText("레이드 종료");
        } else {
            iv_boss1.setImageResource(R.drawable.boss1_2);
        }

        tv_date.setText(startDate + " ~ " + endDate);
        tv_score.setText("기여도  "+mS+" / "+aS);
        tv_cnt.setText(aS+"/"+tC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container, false);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);


//        btn_giveUp = v.findViewById(R.id.btn_giveUp);
        tv_applier_pull = v.findViewById(R.id.tv_applier_pull);
        tv_applier_sqt = v.findViewById(R.id.tv_applier_sqt);
        tv_applier_push = v.findViewById(R.id.tv_applier_push);
        builder = new AlertDialog.Builder(getActivity());

        constaintLayout_pull = v.findViewById(R.id.constaintLayout_pull);
        constaintLayout_pull.setTag("12");

        v.findViewById(R.id.constaintLayout_sqt).setTag("10");
        v.findViewById(R.id.constaintLayout_push).setTag("8");

        tv_date = v.findViewById(R.id.tv_date);
        bar_stat = v.findViewById(R.id.bar_stat);
        tv_score = v.findViewById(R.id.tv_score);
        tv_cnt = v.findViewById(R.id.tv_cnt);

        iv_boss1 = v.findViewById(R.id.iv_boss1);
        iv_boss2 = v.findViewById(R.id.iv_boss2);
        iv_boss3 = v.findViewById(R.id.iv_boss3);

        tv_raid1 = v.findViewById(R.id.tv_raid1);


        // 임시로 태그 설정 해놈(Null 방지)
        tag = "12";

        //======================레이드 팝업창 구성할 때
        requestQueue_info = Volley.newRequestQueue(getContext());
        // 2. 전송할 URL
        String url_info = "http://211.48.213.139:8081/final_project2/AppRaidInfo";

        stringRequest_info = new StringRequest(Request.Method.POST, url_info, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String data[] = response.split("#");
                // myScore, allScore 값 누락이 잘 남
                myScore = data[0];
                allScore = data[1];

                mS = Integer.parseInt(myScore);
                aS = Integer.parseInt(allScore);

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
                params.put("raid_seq", tag);

                return params;
            }
        };
        requestQueue_info.add(stringRequest_info);





        //====================================통신===========================================

        //====================== 참가하기, 포기하기
        requestQueue_update = Volley.newRequestQueue(getContext());
        // 2. 전송할 URL
        String url_update = "http://211.48.213.139:8081/final_project2/RaidUpdate";


        stringRequest_update = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("true")){
                    Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
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
                params.put("raid_seq", tag);

                return params;
            }
        };

        //======================레이드뷰 화면 구성할 때
        requestQueue_raid = Volley.newRequestQueue(getContext());
        String url_raid = "http://211.48.213.139:8081/final_project2/RaidInfo";

        stringRequest_raid = new StringRequest(Request.Method.POST, url_raid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.v("asdf", response);
                jsonRead(response);

                for(int i = 0; i<raidAl.size(); i++) {
                    if (v.findViewById(R.id.constaintLayout_pull).getTag().equals(raidAl.get(i).getRaid_seq())
                            && raidAl.get(i).getCheck().equals("true")) {

                        tv_applier_pull.setText("포기하기");

                        //  참여시 값 셋팅 메서드
                        valueSet(mS);

                    } else {
                        // 기본값으로 값 셋팅 해주는 메서드(참가하는 상태가 아닐때)
                        valueSet(mS);
                    }
//                    if (v.findViewById(R.id.constaintLayout_sqt).getTag().equals(raidAl.get(i).getRaid_seq())
//                            && raidAl.get(i).getCheck().equals("true")) {
//                        tv_applier_sqt.setText("참가중");
//                    }
//                    if (v.findViewById(R.id.constaintLayout_push).getTag().equals(raidAl.get(i).getRaid_seq())
//                            && raidAl.get(i).getCheck().equals("true")) {
//                        tv_applier_push.setText("참가중");
//                    }
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
                params.put("raid_seq_pull", "12");
                params.put("raid_seq_sqt", "10");
                params.put("raid_seq_push", "8");

//                params.put("raid_seq_sqt", Fragment3.this.constaintLayout_sqt.getTag().toString());
//                params.put("raid_seq_push", Fragment3.this.constaintLayout_push.getTag().toString());

                return params;
            }
        };
        requestQueue_raid.add(stringRequest_raid);




        //==============================풀업 시퀀스(태그 12)
        v.findViewById(R.id.constaintLayout_pull).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_applier_pull.getText().toString().equals("포기하기")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("포기 하시겠습니까?");
                    builder.setNegativeButton("아니오", null);
                    builder.setPositiveButton("포기하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 업데이트 insert!
                            requestQueue_update.add(stringRequest_update);
                            tv_applier_pull.setText("참가하기");

                            // 기본값으로 값 셋팅 해주는 메서드(참가하는 상태가 아닐때)
                            valueSet(0);

                        }
                    });
                    builder.create().show();

//                    builder = new AlertDialog.Builder(getActivity());
//                    constaintLayout_pull = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
//                    builder.setView(constaintLayout_pull);
//                    AlertDialog temp = builder.create();



//                    requestQueue_update.add(stringRequest_update);
//                    tv_applier_pull.setText("참가하기");
//
//                    // 기본값으로 값 셋팅 해주는 메서드(참가하는 상태가 아닐때)
//                    valueSet(0);





//                    constaintLayout_pull.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            requestQueue_update.add(stringRequest_update);
//                            tv_applier_pull.setText("참가하기");
//                            temp.dismiss();
//                        }
//                    });
//
//                    constaintLayout_pull.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            temp.dismiss();
//                        }
//                    });
//                    temp.show();
                }
                else if (tv_applier_pull.getText().toString().equals("참가하기")) {
                    tag = "12";
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("레이드에 참가하시겠습니까?");
                    builder.setPositiveButton("아니오", null);
                    builder.setNegativeButton("참가하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 업데이트 insert!
                            requestQueue_update.add(stringRequest_update);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            tv_applier_pull.setText("포기하기");

                            //  참여시 값 셋팅 메서드
                            valueSet(mS);

                        }
                    });
                    builder.create().show();
                }
            }
        });


//        //==============================스쿼트 시퀀스(태그 10)
//        v.findViewById(R.id.constaintLayout_sqt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tv_applier_sqt.getText().toString().equals("참가중")) {
//                    tag = "10";
//                    builder = new AlertDialog.Builder(getActivity());
//                    constaintLayout_sqt = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
//                    builder.setView(constaintLayout_sqt);
//
//                    tv_date = constaintLayout_sqt.findViewById(R.id.tv_date2);
//                    tv_allScore = constaintLayout_sqt.findViewById(R.id.tv_allScore2);
//                    bar_stat = constaintLayout_sqt.findViewById(R.id.bar_stat);
//                    tv_score = constaintLayout_sqt.findViewById(R.id.tv_score);
//                    tv_cnt = constaintLayout_sqt.findViewById(R.id.tv_cnt2);
//
//                    tv_cnt.setText(raidAl.get(1).getRaid_cnt());
//
//                    requestQueue_info.add(stringRequest_info);
//
//                    Calendar cal = Calendar.getInstance();
//                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    cal.setTime(raidAl.get(1).getReg_date());
//
//                    String startDate = df.format(raidAl.get(1).getReg_date()).toString();
//                    cal.add(Calendar.DATE, 3);
//                    String endDate = df.format(cal.getTime()).toString();
//
//                    tv_date.setText("참여기간 : " + startDate + " - " + endDate);
//
//                    AlertDialog temp = builder.create();
//
//                    constaintLayout_sqt.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            requestQueue_update.add(stringRequest_update);
//                            tv_applier_pull.setText("참가하기");
//                            temp.dismiss();
//                        }
//                    });
//
//                    constaintLayout_sqt.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            temp.dismiss();
//                        }
//                    });
//                    temp.show();
//
//                } else if (tv_applier_sqt.getText().toString().equals("참가하기")) {
//                    tag = "10";
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setMessage("레이드에 참가하시겠습니까?");
//                    builder.setPositiveButton("아니오", null);
//                    builder.setNegativeButton("참가하기", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 업데이트 insert!
//                            requestQueue_update.add(stringRequest_update);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            tv_applier_sqt.setText("참가중");
//                            builder = new AlertDialog.Builder(getActivity());
//                            constaintLayout_sqt = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
//                            builder.setView(constaintLayout_sqt);
//                            tv_date = constaintLayout_sqt.findViewById(R.id.tv_date2);
//                            tv_allScore = constaintLayout_sqt.findViewById(R.id.tv_allScore2);
//                            bar_stat = constaintLayout_sqt.findViewById(R.id.bar_stat);
//                            tv_score = constaintLayout_sqt.findViewById(R.id.tv_score);
//                            tv_cnt = constaintLayout_sqt.findViewById(R.id.tv_cnt2);
//
//                            tv_cnt.setText(raidAl.get(1).getRaid_cnt());
//
//
//                            requestQueue_info.add(stringRequest_info);
//
//                            Calendar cal = Calendar.getInstance();
//                            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                            cal.setTime(raidAl.get(1).getReg_date());
//
//                            String startDate = df.format(raidAl.get(1).getReg_date()).toString();
//                            cal.add(Calendar.DATE, 3);
//                            String endDate = df.format(cal.getTime()).toString();
//
//                            tv_date.setText("참여기간 : " + startDate + " - " + endDate);
//
//                            AlertDialog temp = builder.create();
//
//                            constaintLayout_sqt.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    // delete!!!
//                                    requestQueue_update.add(stringRequest_update);
//                                    tv_applier_sqt.setText("참가하기");
//                                    temp.dismiss();
//                                }
//                            });
//                            constaintLayout_sqt.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    temp.dismiss();
//                                }
//                            });
//                            temp.show();
//                        }
//                    });
//                    builder.create().show();
//                }
//            }
//        });
//
//        //==============================푸쉬업 시퀀스(태그 8)
//        v.findViewById(R.id.constaintLayout_push).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tv_applier_push.getText().toString().equals("참가중")) {
//                    tag = "8";
//                    builder = new AlertDialog.Builder(getActivity());
//                    constaintLayout_push = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
//                    builder.setView(constaintLayout_push);
//
//                    tv_date = constaintLayout_push.findViewById(R.id.tv_date2);
//                    tv_allScore = constaintLayout_push.findViewById(R.id.tv_allScore2);
//                    bar_stat = constaintLayout_push.findViewById(R.id.bar_stat);
//                    tv_score = constaintLayout_push.findViewById(R.id.tv_score);
//                    tv_cnt = constaintLayout_push.findViewById(R.id.tv_cnt2);
//
//                    tv_cnt.setText(raidAl.get(2).getRaid_cnt());
//
//                    requestQueue_info.add(stringRequest_info);
//
//                    Calendar cal = Calendar.getInstance();
//                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                    cal.setTime(raidAl.get(2).getReg_date());
//
//                    String startDate = df.format(raidAl.get(2).getReg_date()).toString();
//                    cal.add(Calendar.DATE, 3);
//                    String endDate = df.format(cal.getTime()).toString();
//
//                    tv_date.setText("참여기간 : " + startDate + " - " + endDate);
//
//                    AlertDialog temp = builder.create();
//
//                    constaintLayout_push.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            requestQueue_update.add(stringRequest_update);
//                            tv_applier_pull.setText("참가하기");
//                            temp.dismiss();
//                        }
//                    });
//
//                    constaintLayout_push.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            temp.dismiss();
//                        }
//                    });
//                    temp.show();
//
//                } else if (tv_applier_push.getText().toString().equals("참가하기")) {
//                    tag = "8";
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setMessage("레이드에 참가하시겠습니까?");
//                    builder.setPositiveButton("아니오", null);
//                    builder.setNegativeButton("참가하기", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // 업데이트 insert!
//                            requestQueue_update.add(stringRequest_update);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            tv_applier_push.setText("참가중");
//                            builder = new AlertDialog.Builder(getActivity());
//                            constaintLayout_push = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
//                            builder.setView(constaintLayout_push);
//                            tv_date = constaintLayout_push.findViewById(R.id.tv_date2);
//                            tv_allScore = constaintLayout_push.findViewById(R.id.tv_allScore2);
//                            bar_stat = constaintLayout_push.findViewById(R.id.bar_stat);
//                            tv_score = constaintLayout_push.findViewById(R.id.tv_score);
//                            tv_cnt = constaintLayout_push.findViewById(R.id.tv_cnt2);
//
//                            tv_cnt.setText(raidAl.get(2).getRaid_cnt());
//
//
//                            requestQueue_info.add(stringRequest_info);
//
//                            Calendar cal = Calendar.getInstance();
//                            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//                            cal.setTime(raidAl.get(2).getReg_date());
//
//                            String startDate = df.format(raidAl.get(2).getReg_date()).toString();
//                            cal.add(Calendar.DATE, 3);
//                            String endDate = df.format(cal.getTime()).toString();
//
//                            tv_date.setText("참여기간 : " + startDate + " - " + endDate);
//
//                            AlertDialog temp = builder.create();
//
//                            constaintLayout_push.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    // delete!!!
//                                    requestQueue_update.add(stringRequest_update);
//                                    tv_applier_pull.setText("참가하기");
//                                    temp.dismiss();
//                                }
//                            });
//                            constaintLayout_push.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    temp.dismiss();
//                                }
//                            });
//                            temp.show();
//                        }
//                    });
//                    builder.create().show();
//                }
//            }
//        });

        UpdateThread updateThread = new UpdateThread();
        updateThread.start();



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

    //DB새로고침 스레드
    Handler handelr = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // Thread에서 해당 handler한테 Message 보내면
            // 그거 처리하는 곳!
            // 그래서 Message가 매개변수다.
            // 여기는 MainThread 관할 구역이다.
            // 여기서는 UI업데이트가 가능하다.

            mS = msg.arg1;
            aS = msg.arg2;
            int result_tmp = (Integer) msg.obj;

//            cal.setTime(raidAl.get(0).getReg_date());
//            String startDate = df.format(raidAl.get(0).getReg_date()).toString();
//
//            cal.add(Calendar.DATE, 3);
//            String endDate = df.format(cal.getTime()).toString();
//
//            totalCnt = raidAl.get(0).getRaid_cnt();
//            tC = Integer.parseInt(totalCnt);

            barGauge = (int)((double)aS/tC*100);
//        Toast.makeText(getActivity(), barGauge+"", Toast.LENGTH_SHORT).show();
            // 임시 데이터
//        barGauge = 50;
            bar_stat.setProgress(barGauge);



            // 이거 켜놓으면 Null때문에 튕김
//            Toast.makeText(getContext(), "mS:"+mS+", result_tmp:"+result_tmp, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mS != result_tmp) {
                        iv_boss1.setImageResource(R.drawable.boss1_1);
                    }
                }
            }, 400);

            if(barGauge<50) {
                iv_boss1.setImageResource(R.drawable.boss1_1);
                tv_raid1.setText("PULL UP");
            }
            else if(barGauge == 100) {
                iv_boss1.setImageResource(R.drawable.boss1_die);
                tv_raid1.setText("레이드 종료");
            } else {
                iv_boss1.setImageResource(R.drawable.boss1_2);
                tv_raid1.setText("PULL UP");
            }

//            tv_date.setText(startDate + " ~ " + endDate);
            tv_score.setText("기여도  "+mS+" / "+aS);
            tv_cnt.setText(aS+"/"+tC);

        }
    };


    public class UpdateThread extends Thread {
        // 지금부터 extends Thread 했기 때문에 지금부터 얘는 Thread!
        private int start; //해당 Thread가 몇 초부터 시작할 건지 전달받을 거임~!

        public UpdateThread() {

        }
        @Override
        public void run() {
            while(true) {
                // 업데이트 되기 전에 값 저장
                int tmp = mS;
                Integer a = new Integer(tmp);

                requestQueue_info.add(stringRequest_info);
                Message msg = new Message();
                msg.obj = a;
                msg.arg1 = mS;
                msg.arg2 = aS;
                handelr.sendMessage(msg);
                try {
                    Thread.sleep(1000*60*60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}//끝 중괄호