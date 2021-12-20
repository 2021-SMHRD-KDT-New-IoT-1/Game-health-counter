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
import android.widget.ListView;
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
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment4 extends Fragment {
    private ListView r_listview;

    private RankVO vo;
    private ArrayList<RankVO> r_data = new ArrayList<>();

    JSONArray jsonArray;

    RequestQueue requestQueue; // 전송통로
    StringRequest stringRequest_rankList;
    String url_rankList;

    TextView result_lv;
    TextView result_nick;
    TextView result_exp;

    // 사용자 캐릭터 정보
    String lv;
    String nick;
    String exp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_4, container, false);
        r_listview = v.findViewById(R.id.r_listview);

        result_lv = v.findViewById(R.id.co2);
        result_nick = v.findViewById(R.id.r_nick2);
        result_exp = v.findViewById(R.id.r_exp2);

        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);

//        lv = spf.getString("result_lv", "1");
        nick = spf.getString("nick", "unknown");
//        exp = spf.getString("result_exp", "0");




        // 1. 통로생성
        requestQueue = Volley.newRequestQueue(getActivity());
        // 2. 전송할 URL
        url_rankList = "http://211.48.213.139:8081/final_project2/RankList"; // 랭킹 서블릿

        stringRequest_rankList = new StringRequest(Request.Method.POST, url_rankList, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                jsonRead(response); // jsonArray 자바객체로 파싱하고 ArrayList<RankVO> data에 add해줌

                //r_리스트뷰 어댑터 생성하고 set해줌
                RankAdapter adapter = new RankAdapter(getContext(), R.layout.rank, r_data);
                r_listview.setAdapter(adapter);

                // 값 넘어오는지 테스트
//                Toast.makeText(getActivity(), data.get(0).getQ_name(), Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("RankListError", error.toString());
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

        requestQueue.add(stringRequest_rankList);
        return v;
    }

    private void jsonRead(String response) {
        try {
            jsonArray = new JSONArray(response);
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                r_data.add(gson.fromJson(jsonArray.get(i).toString(), RankVO.class));
//                Toast.makeText(getActivity(), r_data.get(i).getM_nickname(), Toast.LENGTH_SHORT).show();

                // 현재 사용자의 닉네임값이 들어있는 객체가 있을 시 그 객체 값을 받아와서 현재 사용자 랭킹 파트에 적용
                if(r_data.get(i).getM_nickname().equals(nick)) {
//                    Toast.makeText(getActivity(), nick, Toast.LENGTH_SHORT).show();
                    result_lv.setText("Lv "+r_data.get(i).getC_level());
                    result_nick.setText(r_data.get(i).getM_nickname()+"");
                    result_exp.setText(r_data.get(i).getTotal_exp()+"");
                }
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}