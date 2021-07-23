package com.example.hackathon_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class UserTypePopupActivity extends Activity {
    public String stEmail;
    public String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stEmail = getIntent().getStringExtra("usr_id");
        userType = getIntent().getStringExtra("usr_type");
        //타이틀바 없애기

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_usertype_popup);
    }

    public void mOnClickStudent(View v){
        //데이터 전달하기

        Intent intent = new Intent();
        intent.putExtra("result", "Student");
        setResult(RESULT_OK, intent);
        finish();
        Intent intent2 = new Intent(UserTypePopupActivity.this, MatchingPopupActivity.class);
        intent2.putExtra("usr_id", stEmail);
        intent2.putExtra("usr_type", userType);

        startActivityForResult(intent2, 1);
    }

    public void mOnClickGraduate(View v){
        //데이터 전달하기

        Intent intent = new Intent();
        intent.putExtra("result", "Graduate");

        setResult(RESULT_OK, intent);
        //액티비티(팝업) 닫기
        finish();

        Intent intent2 = new Intent(UserTypePopupActivity.this, GraduatePopupActivity.class);
        intent2.putExtra("usr_id", stEmail);
        intent2.putExtra("usr_type", userType);



        startActivityForResult(intent2, 1);
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
