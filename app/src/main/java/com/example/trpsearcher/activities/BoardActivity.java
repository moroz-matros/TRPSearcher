package com.example.trpsearcher;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    private Integer user_id;
    private JSONObject jsonResponse;
    private JSONArray jsonArray;
    private Button createButton, backButton;


    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<BoardData> dataArrayList = new ArrayList<>();
    private BoardAdapter adapter;
    private int current = 0;
    private int maxSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        createButton = findViewById(R.id.bd_create_btn);
        backButton = findViewById(R.id.bd_back_btn);
        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        createButton.setOnClickListener(onCreateClickListener);
        backButton.setOnClickListener(onBackClickListener);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);

        getForms();

        //Init adapter

        adapter = new BoardAdapter(this, dataArrayList, user_id);

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                BoardData data = new BoardData();
                data.setTitle(jsonArray.getJSONObject(current).getString("title"));
                data.setText(jsonArray.getJSONObject(current).getString("text"));
                data.setOwner_id(jsonArray.getJSONObject(current).getInt("id_owner"));
                //Add data
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //init adapter
            adapter = new BoardAdapter(this, dataArrayList, user_id);
            //set adapter
            recyclerView.setAdapter(adapter);
        }
    }

    private View.OnClickListener onCreateClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent startCreateActivity = new Intent(BoardActivity.this, CreateFormActivity.class);
            startCreateActivity.putExtra("id", user_id);
            startActivity(startCreateActivity);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            BoardActivity.this.finish();
        }
    };

    private void getForms(){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(BoardActivity.this, response, Toast.LENGTH_LONG).show();
                        jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        jsonArray = jsonResponse.getJSONArray("response");
                        maxSize = jsonArray.length();

                        if (success) {
                            getData();
                        } else {
                            Toast.makeText(BoardActivity.this, getString(R.string.failed_ger_forms), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            };

            GetFormsRequest getFormsRequest = new GetFormsRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(BoardActivity.this);
            queue.add(getFormsRequest);
    }
}