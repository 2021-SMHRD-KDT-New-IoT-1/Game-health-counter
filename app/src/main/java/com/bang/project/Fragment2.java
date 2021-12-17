package com.bang.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class Fragment2 extends Fragment {
    private ListView listview;

    private ChatVO vo;
    private ArrayList<ChatVO> data = new ArrayList<>();
    private int img[] = {R.id.checked};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        listview = v.findViewById(R.id.listview);

        data.add(new ChatVO("2021.12.17", "스쿼트를 10회 하세요", R.drawable.checked , "10EXP", "푸쉬업을 10회 하세요", R.drawable.checked, "10EXP", "풀업을 5회 하세요",R.drawable.checked,   "10EXP"));//넣고
        data.add(new ChatVO("2021.12.17", "스쿼트를 10회 하세요", R.drawable.checked , "10EXP", "푸쉬업을 10회 하세요", R.drawable.checked, "10EXP", "풀업을 5회 하세요",R.drawable.checked,   "10EXP"));//넣고

        ChatAdapter adapter =new ChatAdapter(getContext(), R.layout.chat, data);
        listview.setAdapter(adapter);

        return v;
    }
}