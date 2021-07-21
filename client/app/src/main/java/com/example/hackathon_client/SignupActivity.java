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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

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

    private void signUp(){

        EditText editTextEmail = findViewById(R.id.signup_email);
        String email = editTextEmail.getText().toString();

        EditText editTextPassword = findViewById(R.id.signup_password);
        String password = editTextPassword.getText().toString();

        EditText editTextName = findViewById(R.id.signup_name);
        String name = editTextName.getText().toString();

        EditText editTextPhoneNum = findViewById(R.id.signup_phone);
        String phone = editTextPhoneNum.getText().toString();

        RadioGroup major = findViewById(R.id.major);
        String user_major = getRadio(major);
        if(user_major == "신입생"){
            user_major = "FRESHMAN";
        } else if(user_major == "재학생"){
            user_major = "SENIOR";
        } else{
            user_major = "GRADUATE";
        }

        RadioGroup gender = findViewById(R.id.gender);
        String user_gender = getRadio(gender);
        if(user_gender == "남자"){
            user_gender = "MALE";
        } else{
            user_gender = "FEMALE";
        }

        RadioGroup state = findViewById(R.id.state);
        String user_type = getRadio(state);
        if(user_type == "심컴"){
            user_type = "ADVANCED";
        } else{
            user_type = "GLOBALSW";
        }

        if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("") && !editTextName.getText().toString().equals("")) {
            // 이메일과 비밀번호가 공백이 아닌 경우
            if(major.getCheckedRadioButtonId() != -1 && gender.getCheckedRadioButtonId() != -1 && major.getCheckedRadioButtonId() != -1 && state.getCheckedRadioButtonId() != -1 ){
                //라디오버튼 체크 된 경우
                createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());

                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("email", email);
                    jsonObject.put("password", password);
                    jsonObject.put("name", name);
                    jsonObject.put("tel_number", phone);
                    jsonObject.put("UserType", user_type);
                    jsonObject.put("Major", user_major);
                    jsonObject.put("Gender", user_gender);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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

    //라디오버튼 값 받아오기
    private String getRadio(RadioGroup guessRadioGroup){
        int selectedId = guessRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) guessRadioGroup.findViewById(selectedId);
        String s = radioButton.getText().toString();
        return s;
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

                        //이메일 인증 구현해야함

                        Toast.makeText(SignupActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                        firebaseAuth.addAuthStateListener(firebaseAuthListener);



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
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }


}
