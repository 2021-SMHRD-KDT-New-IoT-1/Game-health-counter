package com.bang.project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

// 캐릭터 탭(메인화면)
public class Fragment5 extends Fragment {
    private ImageView main_char;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_5, container, false);

        main_char = v.findViewById(R.id.main_char);



        출처: https://neoroid.tistory.com/317 [그린 블로그]

        main_char.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment7()).commit();
            }
        });

        return v;
    }
}