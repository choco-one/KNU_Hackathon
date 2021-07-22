package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<GridItem> itemList = new ArrayList<>();

    int position;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseDatabase chatRoomdatabase = FirebaseDatabase.getInstance();

    String stEmail;
    ChatRoom chatRoom = new ChatRoom();
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
            finishAffinity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String usr_id_from_login = intent.getStringExtra("usr_id");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_list:
                        Intent a = new Intent(MainActivity.this, MentoListActivity.class);
                        a.putExtra("usr_id", usr_id_from_login);
                        startActivity(a);
                        break;
                    case R.id.navigation_mypage:
                        Intent b = new Intent(MainActivity.this, MypageActivity.class);
                        b.putExtra("usr_id", usr_id_from_login);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
        navigation.getMenu().getItem(0).setChecked(true);

        bindGrid();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chatRoomdatabase = FirebaseDatabase.getInstance();
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
                stEmail = getIntent().getStringExtra("usr_id");

                DatabaseReference ref = chatRoomdatabase.getReference("Room").child("chatRoom");
                DatabaseReference userRef = chatRoomdatabase.getReference("users").child(mAuth.getCurrentUser().getUid()).child("chatUid");

                if(a_name == "create") {
                    // popup
                    Intent intent = new Intent(MainActivity.this, UserTypePopupActivity.class);
                    startActivityForResult(intent, 1);
                    position = a_position;

                    // 여기에 채팅방 생성 코드 넣어보자
                    // random user id 가져와서 생성되게 만들

                    Hashtable<String, String> value = new Hashtable<>();
//                    DatabaseReference chref = chatRoomdatabase.getReference("chatRoom");
                    String roomUid = mAuth.getCurrentUser().getUid() + "uiIsSbywnnMJ6xFqeB6FhSULAdw2";
                    value.put("chatRoomUid", roomUid);

//                    ref.addChildEventListener(childEventListener);
//                    ref.push().setValue("");
                    ref.setValue(value);
                    userRef.setValue(value);
                }
                else if(a_name == "mentee") {
                    // 멘티랑 1대1 채팅방 실행
                    Intent in = new Intent(MainActivity.this, ChatActivity.class);

                    in.putExtra("usr_id", stEmail);
                    startActivity(in);
                }
            }
        });
        gridView.setAdapter(mGridAdapter);
    }
}