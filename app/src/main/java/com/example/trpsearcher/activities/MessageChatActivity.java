package com.example.trpsearcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.requests.ChangeStatusRequest;
import com.example.trpsearcher.adapters.MessageAdapter;
import com.example.trpsearcher.datas.MessageData;
import com.example.trpsearcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageChatActivity extends AppCompatActivity {

    private Integer user_id, user2_id;
    private Boolean has_new;
    private Button sendButton;
    private JSONArray messages;
    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<MessageData> dataArrayList = new ArrayList<>();
    private MessageAdapter adapter;
    private HashMap<Integer, Integer> colors = new HashMap<>();
    private int current = 0;
    private int maxSize = 0;
    private int color = 0;
    private String login, login2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);

        Intent intent = getIntent();
        user2_id = intent.getIntExtra("user2_id", 0);
        user_id = intent.getIntExtra("user_id", 0);
        String jsonArray = intent.getStringExtra("jsonArray");
        login2 = intent.getStringExtra("login2");
        login = intent.getStringExtra("login");
        has_new = intent.getBooleanExtra("user_hasNew", false);

        sendButton = findViewById(R.id.chms_send);

        sendButton.setOnClickListener(onSendClickListener);
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = getString(R.string.ip) + getString(R.string.change_status_php);
        ChangeStatusRequest changeStatusRequest = new ChangeStatusRequest(user_id, user2_id, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(changeStatusRequest);
    }

    private void getData(){
        progressBar.setVisibility(View.GONE);
        int next = current + 5;
        for (; (current<next) && (current < maxSize); current++){
            try{
                //Init main data
                    MessageData data = new MessageData();
                    Integer sender = messages.getJSONObject(current).getInt("sender");
                    data.setUser_id(sender);
                    if (sender.equals(user_id)){
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

    private View.OnClickListener onSendClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent startMessageActivity = new Intent(MessageChatActivity.this, MessageActivity.class);
            startMessageActivity.putExtra("user2_id", user2_id);
            startMessageActivity.putExtra("user_id", user_id);
            startActivity(startMessageActivity);
            //finish();
        }
    };
}
