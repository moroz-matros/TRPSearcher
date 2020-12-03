package com.example.trpsearcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.ErrorDetector;
import com.example.trpsearcher.adapters.GameAdapter;
import com.example.trpsearcher.datas.GameData;
import com.example.trpsearcher.R;
import com.example.trpsearcher.requests.CloseRequest;
import com.example.trpsearcher.requests.SendPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    private Integer id, user_id, user2_id;
    private Button sendButton, closeButton;
    private JSONArray game;
    private Boolean closed;

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<GameData> dataArrayList = new ArrayList<>();
    private GameAdapter adapter;
    private HashMap<Integer, Integer> colors = new HashMap<>();
    private int current = 0;
    private int maxSize = 0;
    private int color = 0;
    private String user_login, user2_login;
    private EditText post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        user2_id = intent.getIntExtra("user2_id", 0);
        user_id = intent.getIntExtra("user_id", 0);
        String jsonArray = intent.getStringExtra("jsonArray");
        user2_login = intent.getStringExtra("user2_login");
        user_login = intent.getStringExtra("user_login");
        closed = intent.getBooleanExtra("closed", false);

        sendButton = findViewById(R.id.gmsl_send);
        closeButton = findViewById(R.id.gmsl_close);
        post = findViewById(R.id.gmsl_text);
        sendButton.setOnClickListener(onSendClickListener);
        closeButton.setOnClickListener(onCloseClickListener);

        if (closed){
            closeButton.setVisibility(View.GONE);
            sendButton.setVisibility(View.GONE);
            post.setVisibility(View.GONE);
        }

        try {
            game = new JSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        maxSize = game.length();

        nestedScrollView = findViewById(R.id.gmsl_scroll_view);
        recyclerView = findViewById(R.id.gmsl_recycler_view);
        progressBar = findViewById(R.id.gmsl_progress_bar);

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


    private void getData(){
        progressBar.setVisibility(View.GONE);
        int next = current + 10;
        for (; (current<next) && (current < maxSize); current++){
            try{
                //Init main data
                GameData data = new GameData();
                Integer sender = game.getJSONObject(current).getInt("sender");
                data.setUser_id(sender);
                if (sender.equals(user_id)){
                    data.setLogin(user_login);
                } else data.setLogin(user2_login);
                data.setText(game.getJSONObject(current).getString("text"));
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
            adapter = new GameAdapter(this, dataArrayList);
            //set adapter
            recyclerView.setAdapter(adapter);
        }
    }


    private View.OnClickListener onCloseClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        Toast.makeText(GameActivity.this, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            String URL = getString(R.string.ip) + getString(R.string.close_php);
            CloseRequest closeRequest = new CloseRequest(id, 1, URL, responseListener);
            RequestQueue queue = Volley.newRequestQueue(GameActivity.this);
            queue.add(closeRequest);
            finish();
        }
    };

    private View.OnClickListener onSendClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if (check()){
                final String text = post.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Toast.makeText(GameActivity.this, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                String URL = getString(R.string.ip) + getString(R.string.send_post_php);
                SendPostRequest sendPostRequest = new SendPostRequest(id, user_id, user2_id, text, URL, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GameActivity.this);
                queue.add(sendPostRequest);
                finish();
            }
        }
    };

    private boolean check() {
        ErrorDetector ed = new ErrorDetector();
        return (ed.isNotEmpty(post) && ed.lengthCheckMaxText(post));
    }
}
