package com.example.trpsearcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.adapters.ChatAdapter;
import com.example.trpsearcher.datas.ChatOutData;
import com.example.trpsearcher.requests.ChatsRequest;
import com.example.trpsearcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private Integer user_id;
    private JSONArray jsonArray;
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<ChatOutData> dataArrayList = new ArrayList<>();
    ChatAdapter adapter;
    int current = 0;
    int maxSize = 0;
    String user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        user_login = intent.getStringExtra("user_login");

        nestedScrollView = findViewById(R.id.ch_scroll_view);
        recyclerView = findViewById(R.id.ch_recycler_view);
        progressBar = findViewById(R.id.ch_progress_bar);

        getChats();

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        //Set adapter
        recyclerView.setAdapter(adapter);
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

    private void getData() {
        progressBar.setVisibility(View.GONE);
        int next = current+5;
        for (; (current<next) && (current < maxSize); current++){
            try{
                //Init main data
                ChatOutData data = new ChatOutData();
                int flag = jsonArray.getJSONObject(current).getInt("id_have_new");
                data.setHas_new(flag == 1);

                JSONObject currentObj = jsonArray.getJSONObject(current);
                data.setUser2_login(currentObj.getString("login"));
                data.setUser_id(user_id);
                data.setUser2_id(currentObj.getInt("id"));
                data.setJsonArray(currentObj.getJSONArray("messages"));
                data.setUser_login(user_login);

                //Add data
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //init adapter
            adapter = new ChatAdapter(this, dataArrayList);
            //set adapter
            recyclerView.setAdapter(adapter);
        }
    }

    private void getChats() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        jsonArray = jsonResponse.getJSONArray("response");
                        maxSize = jsonArray.length();
                        getData();
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ChatActivity.this, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = getString(R.string.ip) + getString(R.string.get_chats_php);
        ChatsRequest chatsRequest = new ChatsRequest(user_id, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
        queue.add(chatsRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}