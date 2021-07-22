package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    public EditText editTextEmail;
    public EditText editTextPassword;
    public EditText editTextName;
    public EditText editTextPhoneNum;
    public RadioGroup major;
    public RadioGroup gender;
    public RadioGroup state;
    public String user_type;
    public String user_gender;
    public String user_major;
    public String name;
    public String email;
    public String phone;
    public String password;

    public StringRequest stringRequest;

    public RequestQueue queue;

    private static final String TAG = "SignupActivity";

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseDatabase database;

    private Button btn_signup;
    String url = "http://ec2-3-37-147-187.ap-northeast-2.compute.amazonaws.com/api/user/join";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        queue = Volley.newRequestQueue(this);

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        editTextEmail = findViewById(R.id.signup_email);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    }

    private void signUp() throws Exception {

        editTextEmail = findViewById(R.id.signup_email);
        email = editTextEmail.getText().toString();

        editTextPassword = findViewById(R.id.signup_password);
        password = editTextPassword.getText().toString();

        editTextName = findViewById(R.id.signup_name);
        name = editTextName.getText().toString();

        editTextPhoneNum = findViewById(R.id.signup_phone);
        phone = editTextPhoneNum.getText().toString();

        major = findViewById(R.id.major);

        gender = findViewById(R.id.gender);

        state = findViewById(R.id.state);

        if (!email.equals("") && !password.equals("") && !name.equals("")) {
            // 이메일과 비밀번호가 공백이 아닌 경우
            if(major.getCheckedRadioButtonId() != -1 && gender.getCheckedRadioButtonId() != -1 && state.getCheckedRadioButtonId() != -1 ){
                //라디오버튼 체크 된 경우
                int selectedId_type = state.getCheckedRadioButtonId();
                RadioButton radioButton_type = (RadioButton) findViewById(selectedId_type);
                user_type = radioButton_type.getText().toString();

                if(user_type.equals("신입생")){
                    user_type = "FRESHMAN";
                } else if(user_type.equals("재학생")){
                    user_type = "SENIOR";
                } else{
                    user_type = "GRADUATE";
                }

                int selectedId_gender = gender.getCheckedRadioButtonId();
                RadioButton radioButton_gender = (RadioButton) findViewById(selectedId_gender);
                user_gender = radioButton_gender.getText().toString();

                if(user_gender.equals("남자")){
                    user_gender = "MALE";
                } else{
                    user_gender = "FEMALE";
                }

                int selectedId_major = major.getCheckedRadioButtonId();
                RadioButton radioButton_major = (RadioButton) findViewById(selectedId_major);
                user_major = radioButton_major.getText().toString();

                if(user_major.equals("심컴")){
                    user_major = "ADVANCED";
                } else{
                    user_major = "GLOBALSW";
                }

                createUser(email, password);

            }

            else{
                //라디오버튼 체크 안 된 경우
                Toast.makeText(SignupActivity.this, "선택을 완료해주세요.", Toast.LENGTH_LONG).show();
            }
        } else {
            // 이메일과 비밀번호가 공백인 경우
            Toast.makeText(SignupActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
        }

    }

    Intent intent = getIntent();
    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // 회원가입 성공

                        // real time database에 유저 정보 저장...

                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        // user information save
                        DatabaseReference myRef = database.getReference("users").child(user.getUid());

                        Hashtable<String, Object> values = new Hashtable<String, Object>();
                        values.put("email", user.getEmail());
                        values.put("Uid", user.getUid());
                        values.put("count", 0);

                        myRef.setValue(values);

                        //이메일 인증 구현해야함

                        Toast.makeText(SignupActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                        firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        push(name, email, password, phone, user_type, user_major, user_gender);

                    } else {
                        // 회원가입 실패
                        Toast.makeText(SignupActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null && queue != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
            queue.cancelAll(TAG);
        }
    }

    void push(String name, String email, String password, String phone, String user_type, String user_major, String user_gender){
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignupActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("tel_number", phone);
                params.put("userType", user_type);
                params.put("major", user_major);
                params.put("gender", user_gender);
                return params;
            }
        };
        stringRequest.setTag(TAG);

        queue.add(stringRequest);
    }

}
