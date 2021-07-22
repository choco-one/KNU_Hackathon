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

public class GraduatePopupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_graduate_popup);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        final RadioGroup rg_company = (RadioGroup) findViewById(R.id.radioGroup_company);

        if(rg_company.getCheckedRadioButtonId() != -1){
            //check
            RadioButton rb_company = (RadioButton) findViewById(rg_company.getCheckedRadioButtonId());
            String selected_company = (String) rb_company.getText();

            if(selected_company.equals("공기업")){
                selected_company = "PUBLICCO";
            } else{
                selected_company = "PRIVATECO";
            }
            //데이터 전달하기
            Intent intent = new Intent();
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);

            //액티비티(팝업) 닫기
            finish();
        } else{
            //unchecked
            Toast.makeText(GraduatePopupActivity.this, "선택을 완료해주세요.", Toast.LENGTH_LONG).show();
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
