package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

public class MypageEditActivity extends AppCompatActivity {

    private RequestQueue queue;
    private static final String TAG = "MYPAGE_EDIT";
    private TextView user_email;
    private TextView user_name;
    private TextView user_gender;

    private EditText user_phone;
    private RadioGroup major;
    private RadioGroup type;
    private EditText user_career;
    private EditText user_interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        user_phone = findViewById(R.id.user_phone);
        user_career = findViewById(R.id.user_career);
        user_interest = findViewById(R.id.user_interest);
        major = findViewById(R.id.major);
        type = findViewById(R.id.type);

        user_phone.setHint(user.tel_number);
        user_career.setHint(user.career);
        user_interest.setHint(user.interest);
    }


}
