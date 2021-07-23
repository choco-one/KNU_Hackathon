package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MypageActivity extends AppCompatActivity {

    private RequestQueue queue;
    private static final String TAG = "MYPAGE";
    private TextView user_email;
    private TextView user_name;
    private TextView user_phone;
    private TextView user_major;
    private TextView user_stdnum;
    private TextView user_career;
    private TextView user_interest;
    private TextView user_gender;
    private TextView user_type;
    public User user;
    public Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        logout_btn = findViewById(R.id.logout_btn);
        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        user_major = findViewById(R.id.user_major);
        user_stdnum = findViewById(R.id.user_stdnum);
        user_career = findViewById(R.id.user_career);
        user_interest = findViewById(R.id.user_interest);
        user_gender = findViewById(R.id.user_gender);
        user_type = findViewById(R.id.user_type);

        queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String usr_id = intent.getStringExtra("usr_id");

        String url = "http://ec2-3-37-147-187.ap-northeast-2.compute.amazonaws.com/api/user/" + usr_id;

        Button btn_edit = findViewById(R.id.edit_btn);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);

                if(user.major.equals("ADVANCED")){
                        user.major = "심화컴퓨터공학";
                } else{
                    user.major = "글로벌SW융합";
                }

                if(user.gender.equals("FEMALE")){
                    user.gender = "여자";
                } else{
                    user.gender = "남자";
                }

                if(user.userType.equals("FRESHMAN")){
                    user.userType = "신입생";
                } else if(user.userType.equals("GRADUATE")) {
                    user.userType = "졸업생";
                } else{
                    user.userType = "재학생";
                }

                user_email.setText(user.email);
                user_name.setText(user.name);
                user_gender.setText(user.gender);
                user_phone.setText(user.tel_number);
                user_major.setText(user.major);
                user_stdnum.setText(user.std_number);
                user_career.setText(user.career);
                user_interest.setText(user.interest);

                user_type.setText(user.userType);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        stringRequest.setTag(TAG);
        queue.add(stringRequest);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, MypageEditActivity.class);
                intent.putExtra("user", user);

                startActivity(intent);
                //finish();
            }
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(MypageActivity.this, MainActivity.class);
                        a.putExtra("usr_id", usr_id);
                        startActivity(a);
                        break;
                    case R.id.navigation_list:
                        Intent b = new Intent(MypageActivity.this, MentoListActivity.class);
                        b.putExtra("usr_id", usr_id);
                        startActivity(b);
                        break;
                    case R.id.navigation_mypage:
                        break;
                }
                return false;
            }
        });
        navigation.getMenu().getItem(2).setChecked(true);
    }
}
