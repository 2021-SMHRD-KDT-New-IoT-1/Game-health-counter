package com.bang.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 퀘스트 탭
public class Fragment2 extends Fragment {
    private ListView listview;

    private ArrayList<QuestVO> data = new ArrayList<>();
    JSONArray jsonArray;

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_questList;
    String url_questList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        listview = v.findViewById(R.id.listview);

        Animation list_up = AnimationUtils.loadAnimation(getContext(),R.anim.list_up);
        listview.startAnimation(list_up);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);


        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getActivity());
        // 2. 전송할 URL
        url_questList = "http://211.48.213.139:8081/final_project2/QuestList"; // 퀘스트 서블릿

        stringRequest_questList = new StringRequest(Request.Method.POST, url_questList, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                jsonRead(response); // jsonArray 자바객체로 파싱하고 ArrayList<ChatVO> data에 add해줌
                
                //리스트뷰 어댑터 생성하고 set해줌
                ChatAdapter adapter =new ChatAdapter(getContext(), R.layout.chat, data);
                listview.setAdapter(adapter);
                
                // 값 넘어오는지 테스트
//                Toast.makeText(getActivity(), data.get(0).getQ_name(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("QuestListError", error.toString());
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

        requestQueue.add(stringRequest_questList);
        return v;
    }

    private void jsonRead(String response) {
        try {
            jsonArray = new JSONArray(response);
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                data.add(gson.fromJson(jsonArray.get(i).toString(), QuestVO.class));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}