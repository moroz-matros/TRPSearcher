package com.example.trpsearcher.activities;

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
import com.example.trpsearcher.adapters.GamesAdapter;
import com.example.trpsearcher.datas.GamesData;
import com.example.trpsearcher.requests.GamesGetRequest;
import com.example.trpsearcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity {

    private Integer user_id;
    private String user_login;
    private Button createButton;

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<GamesData> dataArrayList = new ArrayList<>();
    private GamesAdapter adapter;
    private JSONArray jsonArray;
    private int current = 0;
    private int maxSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        user_login = intent.getStringExtra("user_login");

        //sort by closed

        createButton = findViewById(R.id.gm_create_btn);
        createButton.setOnClickListener(onCreateClickListener);

        nestedScrollView = findViewById(R.id.gm_scroll_view);
        recyclerView = findViewById(R.id.gm_recycler_view);
        progressBar = findViewById(R.id.gm_progress_bar);

        getGames();

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(GamesActivity.this));

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
        int next = current + 5;
        for (; (current<next) && (current < maxSize); current++){
            try{
                //Init main data
                GamesData data = new GamesData();

                int flag = jsonArray.getJSONObject(current).getInt("closed");
                data.setClosed(flag == 1);

                JSONObject currentObj = jsonArray.getJSONObject(current);

                data.setId(currentObj.getInt("id"));

                data.setUser_id(user_id);
                data.setUser_login(user_login);
                data.setUser2_id(currentObj.getInt("user2_id"));
                data.setUser2_login(currentObj.getString("user2_login"));

                data.setTitle(currentObj.getString("title"));
                data.setDescription(currentObj.getString("description"));
                data.setJsonArray(currentObj.getJSONArray("game"));


                //Add data
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //init adapter
            adapter = new GamesAdapter(this, dataArrayList);
            //set adapter
            recyclerView.setAdapter(adapter);
        }
    }

    private void getGames(){
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

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(GamesActivity.this, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = getString(R.string.ip) + getString(R.string.get_games);

        GamesGetRequest gamesGetRequest = new GamesGetRequest(user_id, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(gamesGetRequest);
    }

    private View.OnClickListener onCreateClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent startCreateActivity = new Intent(GamesActivity.this, CreateGameActivity.class);
            startCreateActivity.putExtra("user_id", user_id);
            startActivity(startCreateActivity);
        }
    };

}