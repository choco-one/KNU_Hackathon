package com.example.hackathon_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<GridItem> itemList = new ArrayList<>();
    private Button btn_mypage;
    int position;

    String stEmail;
    private static final String TAG = "MainActivity";

    public interface ImageItemClickListener {
        void onImageItemClick(String a_name, int a_position) ;
    }

    private GridArrayAdapter mGridAdapter;
    private long backpressedTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_mypage = findViewById(R.id.btn_mypage);

        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MypageActivity.class);

                startActivity(intent);
            }
        });

        bindGrid();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("result");
                if(result.equals("Student")) {
                    itemList.remove(position);
                    itemList.add(new GridItem(R.drawable.loading, "mentee"));
                    itemList.add(new GridItem(R.drawable.plus_icon, "create"));
                    mGridAdapter.notifyDataSetChanged();
                }
                else if(result.equals("Graduate")) {
                    itemList.remove(position);
                    itemList.add(new GridItem(R.drawable.mortarboard, "mentee"));
                    itemList.add(new GridItem(R.drawable.plus_icon, "create"));
                    mGridAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    private void bindGrid() {
        itemList.add(new GridItem(R.drawable.plus_icon, "create"));

        GridView gridView = (GridView) findViewById(R.id.gridview);
        mGridAdapter = new GridArrayAdapter(this, itemList);

        // Grid item 중 image view click event 처리
        mGridAdapter.setImageItemClickListener(new ImageItemClickListener() {
            @Override
            public void onImageItemClick(String a_name, int a_position) {
                stEmail = getIntent().getStringExtra("email");
                if(a_name == "create") {
                    // popup
                    Intent intent = new Intent(MainActivity.this, UserTypePopupActivity.class);
                    startActivityForResult(intent, 1);
                    position = a_position;
                }
                else if(a_name == "mentee") {
                    // 멘티랑 1대1 채팅방 실행
                    Intent in = new Intent(MainActivity.this, ChatActivity.class);

                    in.putExtra("email", stEmail);
                    startActivity(in);
                }
            }
        });
        gridView.setAdapter(mGridAdapter);
    }
}