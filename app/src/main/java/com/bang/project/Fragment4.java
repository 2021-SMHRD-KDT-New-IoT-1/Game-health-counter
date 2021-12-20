package com.bang.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class Fragment4 extends Fragment {
    private ListView r_listview;

    private RankVO vo;
    private ArrayList<RankVO> r_data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_4, container, false);

        r_listview = v.findViewById(R.id.r_listview);

        r_data.add(new RankVO(R.drawable.boss1, "Lv10", "nick", "100"));

        RankAdapter adapter = new RankAdapter(getContext(), R.layout.rank, r_data);
        r_listview.setAdapter(adapter);

        return v;
    }
}