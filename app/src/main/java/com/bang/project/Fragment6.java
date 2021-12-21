package com.bang.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// 마이페이지
public class Fragment6 extends Fragment {

    TextView tv_modify;
    TextView tv_logout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_6, container, false);

        tv_modify = v.findViewById(R.id.push_tcnt);
        tv_logout = v.findViewById(R.id.tv_logout);

        // SharedPreferences 초기화
        SharedPreferences spf = getActivity().getSharedPreferences("UserSPF", Context.MODE_PRIVATE);

        // 회원정보수정 클릭 리스너
        tv_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // join 페이지로 이동
                Intent intent = new Intent(getActivity(),join.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        // 로그아웃 클릭 리스너
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 세션 모두 삭제
                SharedPreferences.Editor editor = spf.edit();
                editor.clear();
                editor.commit();

                // login 페이지로 이동
                Intent intent = new Intent(getActivity(),login.class);
                startActivity(intent);
                getActivity().finish();

            }
        });






        return v;
    }

}