package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MentoListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mento_list);

        Intent intent = getIntent();
        String usr_id_from_login = intent.getStringExtra("usr_id");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(MentoListActivity.this, MainActivity.class);
                        a.putExtra("usr_id", usr_id_from_login);
                        startActivity(a);
                        break;
                    case R.id.navigation_list:
                        break;
                    case R.id.navigation_mypage:
                        Intent b = new Intent(MentoListActivity.this, MypageActivity.class);
                        b.putExtra("usr_id", usr_id_from_login);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
        navigation.getMenu().getItem(1).setChecked(true);
    }
}
