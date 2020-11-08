package com.example.trpsearcher;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GamesActivity extends AppCompatActivity {

    private Integer user_id;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
                View root = inflater.inflate(R.layout.activity_games, container, false);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("id", 0);
        getGames();
        return root;

        //sort by closed
    }

    private void getGames(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(GamesActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(GamesActivity.this, "Получены", Toast.LENGTH_LONG).show();
                        //int age = jsonResponse.getInt("age");
                        //intent.putExtra("age", age);
                        //intent.putExtra("username", username);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GamesActivity.this);
                        builder.setMessage("Не добавлено")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GamesGetRequest gamesGetRequest = new GamesGetRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(gamesGetRequest);
    }
}