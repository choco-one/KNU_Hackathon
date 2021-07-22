package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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
    private EditText user_major;
    private EditText user_stdnum;
    private EditText user_career;
    private EditText user_interest;
    private EditText user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_edit);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        user_phone = findViewById(R.id.user_phone);
        user_major = findViewById(R.id.user_major);
        user_stdnum = findViewById(R.id.user_stdnum);
        user_career = findViewById(R.id.user_career);
        user_interest = findViewById(R.id.user_interest);
        user_type = findViewById(R.id.user_type);

        user_phone.setHint(user.tel_number);
        user_major.setHint(user.major);
        user_stdnum.setHint(user.std_number);
        user_career.setHint(user.career);
        user_interest.setHint(user.interest);
        user_type.setHint(user.userType);
    }


}
