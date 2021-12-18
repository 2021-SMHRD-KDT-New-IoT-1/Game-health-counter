package com.bang.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;


public class Fragment7 extends Fragment {
    private ImageView s_char;
    private TableLayout stat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_7, container, false);

        s_char = v.findViewById(R.id.s_char);
        stat = v.findViewById(R.id.stat);

        Animation left = AnimationUtils.loadAnimation(getContext(),R.anim.left);
        s_char.startAnimation(left);

        Animation alpha = AnimationUtils.loadAnimation(getContext(),R.anim.alpha);
        stat.startAnimation(alpha);

        s_char.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment5()).commit();
            }
        });


        return v;
    }
}