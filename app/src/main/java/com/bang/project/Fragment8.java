package com.bang.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;


public class Fragment8 extends Fragment {
    private ImageView middle_char;
    private TableLayout middle_stat;
    private static  int SPLASH_SCREEN_TIMEOUT = 2200;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v = inflater.inflate(R.layout.fragment_8, container, false);

        middle_char= v.findViewById(R.id.middle_char);
        middle_stat = v.findViewById(R.id.middle_stat);

        Animation right = AnimationUtils.loadAnimation(getContext(),R.anim.right);
        middle_char.startAnimation(right);

        Animation alpha_b = AnimationUtils.loadAnimation(getContext(),R.anim.alpha_b);
        middle_stat.startAnimation(alpha_b);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout, new Fragment5()).commit();
            }
        },SPLASH_SCREEN_TIMEOUT);


        return v;
    }
}