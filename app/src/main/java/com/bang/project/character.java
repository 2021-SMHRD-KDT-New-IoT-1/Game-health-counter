package com.bang.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class character extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        frameLayout = findViewById(R.id.layout);
        bnView = findViewById(R.id.bnView);
    }
}