package com.example.hackathon_client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {
    List<GridItem> itemList = new ArrayList<>();

    int position;

    int number;
    private ArrayList<String> roomTypeList = new ArrayList<>();
    private ArrayList<String> roomnumberList = new ArrayList<>();

    private String Uid;
    private String destinaionUid;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseDatabase chatRoomdatabase = FirebaseDatabase.getInstance();


    int count;

    String stEmail;
    String roomUid;
    ChatRoom chatRoom = new ChatRoom();

    public User user;
    private RequestQueue queue;
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

        queue = Volley.newRequestQueue(this);
        String url = "http://ec2-3-37-147-187.ap-northeast-2.compute.amazonaws.com/api/user/" + usr_id_from_login;

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
                        a.putExtra("usr_type", user.userType);
                        startActivity(a);
                        break;
                    case R.id.navigation_mypage:
                        Intent b = new Intent(MainActivity.this, MypageActivity.class);
                        b.putExtra("usr_id", usr_id_from_login);
                        //b.putExtra("usr_type", user.userType);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });
        navigation.getMenu().getItem(0).setChecked(true);

        bindGrid();

        //유저 받아오기
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                user = gson.fromJson(response, User.class);
                System.out.println(user.userType + user.email);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        chatRoomdatabase = FirebaseDatabase.getInstance();
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                itemList.remove(position);
                DatabaseReference roomRef = chatRoomdatabase.getReference("Room").child(roomUid).child("type");
                HashMap<String, Object> up = new HashMap<>();
                String result = data.getStringExtra("result");
                if(result.equals("Student")) {
                    itemList.add(new GridItem(R.drawable.loading, "mentee"));
                    mGridAdapter.notifyDataSetChanged();
                    up.put("roomType", "Student");
                    roomRef.setValue(up);
                }
                else if(result.equals("Graduate")) {
                    itemList.add(new GridItem(R.drawable.mortarboard, "mentee"));
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
                try {
                    callCount(count);

                }catch (Exception e){
                    System.out.println("fucking exception!!!!!!!" + e);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });





//        DatabaseReference roomtype = chatRoomdatabase.getReference("Room").child("chatRoom").child(roomUid).child("type").child("roomType");
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
//
//
//
//            System.out.println("room type :                 " + roomTypeList);
//        }
//
//        else{
//            //  카운트 0일때
//        }

        try {
            Thread.sleep(3000);
            itemList.add(new GridItem(R.drawable.plus_icon, "create"));
        }catch (Exception e){
            System.out.println(e);
        }


        GridView gridView = (GridView) findViewById(R.id.gridview);
        mGridAdapter = new GridArrayAdapter(this, itemList);


        // Grid item 중 image view click event 처리
        mGridAdapter.setImageItemClickListener(new ImageItemClickListener() {
            @Override
            public void onImageItemClick(String a_name, int a_position) {
                stEmail = getIntent().getStringExtra("usr_id");
                position = a_position+1;

                roomUid = mAuth.getCurrentUser().getUid() + position + "uiIsSbywnnMJ6xFqeB6FhSULAdw2";
                DatabaseReference ref = chatRoomdatabase.getReference("Room").child(roomUid);
                DatabaseReference userRef = chatRoomdatabase.getReference("users").child(mAuth.getCurrentUser().getUid()).child("position");

                if(a_name == "create") {
                    // popup
                    Intent intent = new Intent(MainActivity.this, UserTypePopupActivity.class);
                    intent.putExtra("usr_id", stEmail);
                    intent.putExtra("usr_type", user.userType);

                    System.out.println(user.userType + stEmail);

                    startActivityForResult(intent, 1);


                    // 여기에 채팅방 생성 코드 넣어보자
                    // random user id 가져와서 생성되게 만들
                    count += 1;
//                    DatabaseReference chref = chatRoomdatabase.getReference("chatRoom");
                    HashMap<String, String> value = new HashMap<>();
                    HashMap<String, Object> user_value = new HashMap<>();

                    value.put("roomUid", roomUid);
                    ref.setValue(value);

                    user_value.put(Integer.toString(position), roomUid);
                    userRef.updateChildren(user_value);

                    // change count
                    chatRoomdatabase.getReference("users").child(mAuth.getCurrentUser().getUid()).child("count").setValue(count);

                }
                else if(a_name == "mentee") {
                    // 멘티랑 1대1 채팅방 실행
                    Intent in = new Intent(MainActivity.this, ChatActivity.class);

                    in.putExtra("usr_id", stEmail);
                    in.putExtra("room_uid", roomUid);
                    in.putExtra("usr_type", user.userType);

                    startActivity(in);
                }
            }
        });
        gridView.setAdapter(mGridAdapter);
    }

    public void callCount(int count){
        DatabaseReference roomnumber = chatRoomdatabase.getReference("users").child(mAuth.getCurrentUser().getUid()).child("position");
        System.out.println("user id :                                         " + mAuth.getCurrentUser().getUid());

        if (count > 0) {

            roomnumber.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        roomnumberList.add(postSnapshot.getValue().toString());
                        System.out.println("Room Number List in in in in in in in" + roomnumberList);
                        callRoomnumber(roomnumberList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//            roomnumber.addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
//                    Position position1 = snapshot.getValue(Position.class);
//                    String num = position1.getRoomNum();
//                    roomTypeList.add(num);
//                    System.out.println("Room type List!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! : " + num);
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

//            System.out.println(roomTypeList.get(0));

        }
    }

    public void callRoomnumber (ArrayList<String> roomnumberList){
        for (int i = 0; i < roomnumberList.size(); i++) {

            System.out.println("Room Number List out out out out out out out out " + roomnumberList.get(i));

            DatabaseReference ref = chatRoomdatabase.getReference("Room").child(roomUid);
            HashMap<String, String> temp = new HashMap<>();
            temp.put("roomType", "Student");
            ref.child("type").setValue(temp);

            DatabaseReference roomtype = chatRoomdatabase.getReference("Room").child(roomnumberList.get(i)).child("type").child("roomType");
            roomtype.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    roomTypeList.add(snapshot.getValue().toString());
                    callRoomType(roomTypeList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void callRoomType (ArrayList<String> roomTypeList) {
        for (int i = 0; i < roomTypeList.size(); i++) {
            if (roomTypeList.get(i).equals("Student")) {
                itemList.add(new GridItem(R.drawable.loading, "mentee"));
                mGridAdapter.notifyDataSetChanged();
            } else {
                System.out.println("adhskfhjsdklfjdskasfsdfsdfdsfasfasdf");
                itemList.add(new GridItem(R.drawable.mortarboard, "mentee"));
                mGridAdapter.notifyDataSetChanged();
            }
        }
    }
}