package com.example.hackathon_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class PopupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
    }

    public void mOnClickStudent(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Student");
        setResult(RESULT_OK, intent);
        finish();
        Intent intent2 = new Intent(PopupActivity.this, MatchingPopupActivity.class);
        startActivityForResult(intent2, 1);
    }

    public void mOnClickGraduate(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Graduate");
        setResult(RESULT_OK, intent);
        //액티비티(팝업) 닫기
        finish();
        Intent intent2 = new Intent(PopupActivity.this, MatchingPopupActivity.class);
        startActivityForResult(intent2, 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
