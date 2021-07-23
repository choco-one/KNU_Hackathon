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

public class GraduatePopupActivity extends Activity {
    public String userType;
    public StringRequest stringRequest;
    public String stEmail;
    public RequestQueue queue;
    public RadioGroup rg_company;
    public String uid;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String url = "http://ec2-3-37-147-187.ap-northeast-2.compute.amazonaws.com/api/matching/gadd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stEmail = getIntent().getStringExtra("usr_id");
        userType = getIntent().getStringExtra("usr_type");

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_graduate_popup);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        queue = Volley.newRequestQueue(this);

        rg_company = (RadioGroup) findViewById(R.id.radioGroup_company);

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
            intent.putExtra("check_company", selected_company);
            setResult(RESULT_OK, intent);

            //액티비티(팝업) 닫기

            finish();
            uid = mAuth.getCurrentUser().getUid();
            System.out.println(stEmail + " "+ userType + " "+ selected_company + uid);
            pushGraduatePop( stEmail,  userType,  selected_company,  "GRADUATE", uid);
        } else{
            //unchecked
            Toast.makeText(GraduatePopupActivity.this, "선택을 완료해주세요.", Toast.LENGTH_LONG).show();
        }

    }

    void pushGraduatePop(String email, String userType, String company, String matching_type, String uid){
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //
                //Toast.makeText(GraduatePopupActivity.this, response, Toast.LENGTH_LONG).show();
//                String[] response_email = response.split(" ");
//                System.out.println(response_email[0]+response_email[1]);
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
                params.put("company", company);
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
