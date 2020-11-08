package com.example.trpsearcher.ui.chat;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.AuthActivity;
import com.example.trpsearcher.MenuActivity;
import com.example.trpsearcher.R;
import com.example.trpsearcher.ui.MessageActivity;
import com.example.trpsearcher.ui.board.CreateFormActivity;
import com.example.trpsearcher.ui.board.MainAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    private Integer user_id, user2_id;
    private Boolean has_new;
    private Button back, send;
    JSONArray messages;
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MessageData> dataArrayList = new ArrayList<>();
    MessageAdapter adapter;
    HashMap<Integer, Integer> colors = new HashMap<>();
    int current = 0;
    int maxSize = 0;
    int color = 0;
    String login, login2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        user2_id = intent.getIntExtra("user2_id", 0);
        user_id = intent.getIntExtra("user_id", 0);
        String jsonArray = intent.getStringExtra("jsonArray");
        login2 = intent.getStringExtra("login2");
        login = intent.getStringExtra("login");
        has_new = intent.getBooleanExtra("user_hasNew", false);
        back = findViewById(R.id.chms_back);
        send = findViewById(R.id.chms_send);
        back.setOnClickListener(onBackClickListener);
        send.setOnClickListener(onSendClickListener);
        if (has_new) changeStatus();

        try {
            messages = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        maxSize = messages.length();

        nestedScrollView = findViewById(R.id.chms_scroll_view);
        recyclerView = findViewById(R.id.chms_recycler_view);
        progressBar = findViewById(R.id.chms_progress_bar);

        getData();
        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //condition
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    //progressBar
                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                }
            }
        });

    }

    private void changeStatus() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ChangeStatusRequest changeStatusRequest = new ChangeStatusRequest(user_id, user2_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(changeStatusRequest);
    }

    private void getData(){
        progressBar.setVisibility(View.GONE);
        int next = current+5;
        for (; (current<next) && (current < maxSize); current++){
            try{
                //Init main data
                    MessageData data = new MessageData();
                    Integer sender = messages.getJSONObject(current).getInt("sender");
                    data.setUser_id(sender);
                    if (sender == user_id){
                        data.setLogin(login);
                    } else data.setLogin(login2);
                    data.setTitle(messages.getJSONObject(current).getString("title"));
                    data.setText(messages.getJSONObject(current).getString("message"));
                    Integer value = colors.get(sender);
                    if (value == null) {
                        colors.put(sender, color);
                        color++;
                    }
                    data.setColors(colors);

                    //Add data
                    dataArrayList.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //init adapter
                adapter = new MessageAdapter(this, dataArrayList);
                //set adapter
                recyclerView.setAdapter(adapter);
            }
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent startMenuActivity = new Intent(ChatActivity.this, MenuActivity.class);
            startMenuActivity.putExtra("login", login);
            startMenuActivity.putExtra("id", user_id);
            startActivity(startMenuActivity);
            
        }
    };

    private View.OnClickListener onSendClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent startMessageActivity = new Intent(ChatActivity.this, MessageActivity.class);
            startMessageActivity.putExtra("send_to_id", user2_id);
            startMessageActivity.putExtra("user_id", user_id);
            startActivity(startMessageActivity);

        }
    };
}
