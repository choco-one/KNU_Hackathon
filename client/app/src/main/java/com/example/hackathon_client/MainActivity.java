package com.example.hackathon_client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {
    List<GridItem> itemList = new ArrayList<>();
    private Button btn_mypage;
    int position;

    int number;
    ArrayList<String> roomTypeList;
    private String Uid;
    private String destinaionUid;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseDatabase chatRoomdatabase = FirebaseDatabase.getInstance();

    int count;

    String stEmail;
    String roomUid;
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
            finish();
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
//        chatRoomdatabase = FirebaseDatabase.getInstance();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                DatabaseReference roomRef = chatRoomdatabase.getReference("Room").child("chatRoom").child(roomUid);
                HashMap<String, Object> up = new HashMap<>();
                String result = data.getStringExtra("result");
                if(result.equals("Student")) {
                    itemList.remove(position);
                    itemList.add(new GridItem(R.drawable.loading, "mentee"));
                    itemList.add(new GridItem(R.drawable.plus_icon, "create"));
                    mGridAdapter.notifyDataSetChanged();
                    up.put("roomType", "Student");
                    roomRef.setValue(up);
                }
                else if(result.equals("Graduate")) {
                    itemList.remove(position);
                    itemList.add(new GridItem(R.drawable.mortarboard, "mentee"));
                    itemList.add(new GridItem(R.drawable.plus_icon, "create"));
                    mGridAdapter.notifyDataSetChanged();
                    up.put("roomType", "Graduate");
                    roomRef.setValue(up);
                }
            }
        }
    }
    private void bindGrid() {

        DatabaseReference room = chatRoomdatabase.getReference("users").child(mAuth.getCurrentUser().getUid()).child("count");
        room.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

//        DatabaseReference roomtype = chatRoomdatabase.getReference("Room").child("chatRoom").child(roomUid).child("roomType");
//        if(count > 0){
//            roomtype.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String type = snapshot.getValue().toString();
//                    roomTypeList.add(type);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            System.out.println("room type :                 " + roomTypeList);
//        }


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
                    count += 1;
                    Hashtable<String, Object> value = new Hashtable<>();
//                    DatabaseReference chref = chatRoomdatabase.getReference("chatRoom");
                    roomUid = mAuth.getCurrentUser().getUid() + count + "uiIsSbywnnMJ6xFqeB6FhSULAdw2";
                    value.put("chatRoomUid", roomUid);

//                    ref.addChildEventListener(childEventListener);
//                    ref.push().setValue("");
//                    ref.updateChildren(value);
                    userRef.push().setValue(value);

                    // change count
                    chatRoomdatabase.getReference("users").child(mAuth.getCurrentUser().getUid()).child("count").setValue(count);
                }
                else if(a_name == "mentee") {
                    // 멘티랑 1대1 채팅방 실행
                    Intent in = new Intent(MainActivity.this, ChatActivity.class);

                    in.putExtra("usr_id", stEmail);
                    in.putExtra("room_uid", roomUid);
                    startActivity(in);
                }
            }
        });
        gridView.setAdapter(mGridAdapter);
    }
}