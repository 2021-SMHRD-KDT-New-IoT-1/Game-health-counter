package com.bang.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class character extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomNavigationView bnView;

    TextView tv_nick;
    TextView tv_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        // fragment 사용시 필요한 레이아웃, 버튼뷰
        frameLayout = findViewById(R.id.layout);
        bnView = findViewById(R.id.bnView);

        tv_nick = findViewById(R.id.tv_nick);
        tv_level = findViewById(R.id.tv_level);

        // 아이템 선택시 반응하는 리스너
        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.tab1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment1()).commit();
                }

                else if(item.getItemId() == R.id.tab2) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment2()).commit();
                }

                else if(item.getItemId() == R.id.tab3) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment3()).commit();
                }

                else if(item.getItemId() == R.id.tab4) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout, new Fragment4()).commit();
                }

                return true;
            }
        });


        // intent값 받아와서 닉네임 set
        String nick = getIntent().getExtras().getString("char_name");
        tv_nick.setText(nick);

        // intent값 받아와서 레벨 set
        int lv = getIntent().getExtras().getInt("char_lv");
        tv_level.setText("Lv "+lv);



    }
}