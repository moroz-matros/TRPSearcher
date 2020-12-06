package com.example.trpsearcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.ErrorDetector;
import com.example.trpsearcher.R;
import com.example.trpsearcher.requests.ChatsRequest;
import com.example.trpsearcher.requests.CreateGameRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateGameActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private EditText user;
    private Button createButton;
    private Integer user_id;
    private ArrayList<String> usersLogins = new ArrayList<>();
    private Map<String, Integer> users = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_game);
        title = findViewById(R.id.gmcr_title);
        description = findViewById(R.id.gmcr_decr);
        user = findViewById(R.id.gmcr_user);
        createButton = findViewById(R.id.gmcr_create);

        createButton.setOnClickListener(onCreateClickListener);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);

        getUsers();

    }

    private View.OnClickListener onCreateClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (usersLogins.contains(user.getText().toString())) {
                if (check()) {
                    create();
                    CreateGameActivity.this.finish();
                }
            } else Toast.makeText(CreateGameActivity.this, getString(R.string.check_right_fields), Toast.LENGTH_SHORT).show();
        }
    };

    private boolean check() {
        ErrorDetector ed = new ErrorDetector();
        return (ed.lengthCheckMax(title, 128) && ed.isNotEmpty(title)
                && ed.lengthCheckMaxText(description) && ed.isNotEmpty(description)
                && ed.isNotEmpty(user));
    }

    private void create(){
        final String titleText = title.getText().toString();
        final String descriptionText =  description.getText().toString();
        Integer user2_id = users.get(user.getText().toString());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Toast.makeText(CreateGameActivity.this, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = getString(R.string.ip) + getString(R.string.create_game_php);

        CreateGameRequest createGameRequest = new CreateGameRequest(user_id, user2_id, titleText, descriptionText, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreateGameActivity.this);
        queue.add(createGameRequest);
    }

    private void getUsers(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String login;
                        Integer id;
                        JSONArray jsonArray = jsonResponse.getJSONArray("response");
                        for (int i = 0; i < jsonArray.length(); i++){
                             login = jsonArray.getJSONObject(i).getString("login");
                             id = jsonArray.getJSONObject(i).getInt("id");
                             usersLogins.add(login);
                             users.put(login, id);
                        }
                    } else {
                        Toast.makeText(CreateGameActivity.this, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = getString(R.string.ip) + getString(R.string.get_chats_php);

        ChatsRequest chatsRequest = new ChatsRequest(user_id, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreateGameActivity.this);
        queue.add(chatsRequest);
    }
}
