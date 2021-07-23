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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class MatchingPopupActivity extends Activity {
    public RadioGroup rg_major;
    public RadioGroup rg_gender;
    public String stEmail;
    public String userType;
    public String uid;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public StringRequest stringRequest;
    public RequestQueue queue;


    String url = "http://ec2-3-37-147-187.ap-northeast-2.compute.amazonaws.com/api/matching/add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stEmail = getIntent().getStringExtra("usr_id");
        userType = getIntent().getStringExtra("usr_type");

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_matching_popup);

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        queue = Volley.newRequestQueue(this);

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
            intent.putExtra("check_gender", selected_gender);
            intent.putExtra("check_major", selected_major);

            setResult(RESULT_OK, intent);



            //액티비티(팝업) 닫기
            finish();
            uid = mAuth.getCurrentUser().getUid();
            System.out.println(userType + " "+stEmail + " "+ selected_gender + " "+selected_major + " " + uid);
            pushPop(stEmail, userType, selected_major, selected_gender, "STUDENT", uid);

        } else{
            //안된경우
            Toast.makeText(MatchingPopupActivity.this, "선택을 완료해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    void pushPop(String email, String userType, String major, String gender, String matching_type, String uid){
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MatchingPopupActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("userType", userType);
                params.put("major", major);
                params.put("gender", gender);
                params.put("matchingType", matching_type);
                params.put("uid", uid);

                return params;
            }
        };
        queue.add(stringRequest);
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
