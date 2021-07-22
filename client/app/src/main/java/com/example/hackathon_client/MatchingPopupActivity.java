package com.example.hackathon_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MatchingPopupActivity extends Activity {
    public RadioGroup rg_major;
    public RadioGroup rg_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_matching_popup);

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        rg_major = (RadioGroup) findViewById(R.id.radioGroup_major);
        rg_gender = (RadioGroup) findViewById(R.id.radioGroup_gender);

        if(rg_major.getCheckedRadioButtonId() != -1 && rg_gender.getCheckedRadioButtonId() != -1){
            //체크된경우
            RadioButton rb_major = (RadioButton) findViewById(rg_major.getCheckedRadioButtonId());
            String selected_major = (String) rb_major.getText();

            RadioButton rb_gender = (RadioButton) findViewById(rg_gender.getCheckedRadioButtonId());
            String selected_gender = (String) rb_gender.getText();

            if(selected_major.equals("심컴")){
                selected_major = "ADVANCED";
            } else{
                selected_major = "GLOBALSW";
            }
            if(selected_gender.equals("남자")){
                selected_gender = "MALE";
            } else{
                selected_gender = "FEMALE";
            }

            //데이터 전달하기
            Intent intent = new Intent();
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);

            //액티비티(팝업) 닫기
            finish();
        } else{
            //안된경우
            Toast.makeText(MatchingPopupActivity.this, "선택을 완료해주세요.", Toast.LENGTH_LONG).show();
        }

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
