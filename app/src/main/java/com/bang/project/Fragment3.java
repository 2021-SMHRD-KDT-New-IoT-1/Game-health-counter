package com.bang.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment3 extends Fragment {
    ArrayList<RaidVO> al = new ArrayList<>();
    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest;
    RaidVO raidvo;
    ArrayList<RaidVO> raidVO_al = new ArrayList<RaidVO>();
    private JSONArray jsonArray;
    ArrayList<RaidVO> raidAl = new ArrayList<>();
    private Button btn_giveUp;
    ConstraintLayout layout;
    ConstraintLayout layout2;
    ConstraintLayout layout3;
    AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_3, container, false);
        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);


        btn_giveUp = v.findViewById(R.id.btn_giveUp);
        builder = new AlertDialog.Builder(getActivity());



        v.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                layout = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
                builder.setView(layout);
                AlertDialog temp = builder.create();

                layout.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.dismiss();
                    }
                });
                temp.show();
            }
        });

        v.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                layout2 = (ConstraintLayout) inflater.inflate(R.layout.popup2, null);
                builder.setView(layout2);
                AlertDialog temp = builder.create();
                layout2.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.dismiss();
                    }
                });
                temp.show();
            }

        });

        v.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                layout3 = (ConstraintLayout) inflater.inflate(R.layout.popup3, null);
                builder.setView(layout3);
                AlertDialog temp = builder.create();
                layout3.findViewById(R.id.btn_giveUp).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp.dismiss();
                    }
                });
                temp.show();
            }
        });


        requestQueue = Volley.newRequestQueue(getContext());
        // 2. 전송할 URL
        String url_login = "http://211.63.240.51:8087/final_project2/RaidInfo";

        stringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.v("asdf", response);
                jsonRead(response);
                Toast.makeText(getContext(), raidAl.get(0).getRaid_name(), Toast.LENGTH_SHORT).show();

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
        requestQueue.add(stringRequest);
        return v;
    }


    private void jsonRead(String response) {
        try {
            jsonArray = new JSONArray(response);
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                // raidAl.add(gson.fromJson(jsonArray.getJSONObject(i).toString() , RaidVO.class));
                raidAl.add(gson.fromJson(jsonArray.get(i).toString(), RaidVO.class));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }
//
//    package com.bang.project;
//
//import android.app.AlertDialog;
//import android.os.Bundle;
//
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//
//import android.text.Layout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//    public class Fragment3 extends Fragment {
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//
//
//            View v = inflater.inflate(R.layout.fragment_3, container, false);
//
//            ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.popup, null);
//            ConstraintLayout layout2 = (ConstraintLayout) inflater.inflate(R.layout.popup2, null);
//            ConstraintLayout layout3 = (ConstraintLayout) inflater.inflate(R.layout.popup3, null);
//
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////        builder.setView(layout);
////        builder.create().show();
//
//            v.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setView(layout);
//                    builder.create().show();
//
//                }
//            });
//
//            v.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setView(layout2);
//                    builder.create().show();
//                }
//            });
//
//            v.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setView(layout3);
//                    builder.create().show();
//                }
//            });
//
//
//            return v;
//        }
//
//    }
//


}