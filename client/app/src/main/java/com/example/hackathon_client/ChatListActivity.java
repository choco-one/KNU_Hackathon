package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChatListActivity extends AppCompatActivity {
    private Button btn_mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);
        Intent intent = getIntent();
        String usr_id_from_login = intent.getStringExtra("usr_id");



        btn_mypage = findViewById(R.id.btn_mypage);

        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatListActivity.this, MypageActivity.class);
                intent.putExtra("usr_id", usr_id_from_login);

                startActivity(intent);
            }
        });
    }



}
