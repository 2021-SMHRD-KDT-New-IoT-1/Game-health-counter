package com.bang.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_3, container, false);


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
                params.put("m_id", "shj");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return v;
    }

//    private void processResponse(String response) {
//        // gson을 이용해 자바 객체로 파싱
//        //Gson gson = new Gson();
//        Gson gson = new GsonBuilder().setDateFormat("MM dd, yyyy HH:mm:ss").create();
//        RaidVO raidvo = gson.fromJson(response, RaidVO.class);
//        raidVO_al.add("asd");
//    }

    private void jsonRead(String response) {
        try {
            jsonArray = new JSONArray(response);
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                // raidAl.add(gson.fromJson(jsonArray.getJSONObject(i).toString() , RaidVO.class));
                raidAl.add(gson.fromJson(jsonArray.get(i).toString() , RaidVO.class));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }



}