package com.example.hackathon_client;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.TextView;
import android.app.Activity;

public class MentoListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mento_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(MentoListActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_list:
                        break;
                    case R.id.navigation_mypage:
                        Intent b = new Intent(MentoListActivity.this, MypageActivity.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
    }
}
