package com.example.trpsearcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.R;
import com.example.trpsearcher.requests.ProfileRequest;
import com.example.trpsearcher.requests.RateRequest;

import org.json.JSONException;
import org.json.JSONObject;

    public class UserProfileActivity extends AppCompatActivity {

        private TextView login, name, email, birthday, about, rate;
        private Button plusBtn, minusBtn;
        private Integer user_id, cur_id;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_profile);
            login = findViewById(R.id.us_user_login);
            name = findViewById(R.id.us_user_name);
            email = findViewById(R.id.us_user_email);
            birthday = findViewById(R.id.us_birthday);
            about = findViewById(R.id.us_about_text);
            plusBtn = findViewById(R.id.us_plus);
            minusBtn = findViewById(R.id.us_minus);
            rate = findViewById(R.id.us_rating);
            plusBtn.setOnClickListener(onPlusClickListener);
            minusBtn.setOnClickListener(onMinusClickListener);
            Intent intent = getIntent();
            user_id = intent.getIntExtra("user_id", 0);
            cur_id = intent.getIntExtra("user2_id", 0);
            fillProfile();

        }

        private void fillProfile(){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(UserProfileActivity.this, response, Toast.LENGTH_LONG).show();
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                            email.setText(jsonResponse.getString("email"));
                            birthday.setText(jsonResponse.getString("birthdate"));
                            name.setText(jsonResponse.getString("name"));
                            about.setText(jsonResponse.getString("about"));
                            rate.setText(jsonResponse.getString("rate"));

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            String URL = getString(R.string.ip) + getString(R.string.profile_php);
            ProfileRequest profileRequest = new ProfileRequest(cur_id, URL, responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserProfileActivity.this);
            queue.add(profileRequest);
        }

        private View.OnClickListener onPlusClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                changeRate(1);
            }
        };

        private View.OnClickListener onMinusClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                changeRate(-1);
            }
        };

        private void changeRate(int i) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(UserProfileActivity.this, response, Toast.LENGTH_LONG).show();
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {


                        }
                        else {
                            Toast.makeText(UserProfileActivity.this, "Вы уже оставляли оценку на этого пользователя", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RateRequest profileRequest = new RateRequest(user_id, cur_id, i, responseListener);
            RequestQueue queue = Volley.newRequestQueue(UserProfileActivity.this);
            queue.add(profileRequest);
        }


    }
