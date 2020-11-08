package com.example.trpsearcher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private EditText login, email, birthdate, name, about;
    private Button editButton, backButton;
    private TextView rate;

    private String user_login;
    private Integer user_id;
    private Integer profile_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        login = findViewById(R.id.pr_user_login);
        email = findViewById(R.id.pr_user_email);
        birthdate = findViewById(R.id.pr_birthday);
        name = findViewById(R.id.pr_user_name);
        about = findViewById(R.id.pr_about_text);
        rate = findViewById(R.id.pr_rate);
        editButton = findViewById(R.id.pr_edit_btn);
        backButton = findViewById(R.id.pr_back_btn);

        Intent intent = getIntent();
        user_login = intent.getStringExtra("user_login");
        user_id = intent.getIntExtra("user_id", 0);

        login.setText(user_login);
        backButton.setOnClickListener(onBackClickListener);
        editButton.setOnClickListener(onEditClickListener);

        getData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void getData(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        email.setText(jsonResponse.getString("email"));
                        birthdate.setText(jsonResponse.getString("birthdate"));
                        name.setText(jsonResponse.getString("name"));
                        about.setText(jsonResponse.getString("about"));
                        rate.setText(getString(R.string.ratedd) + ' ' + jsonResponse.getString("rate"));
                        profile_id = jsonResponse.getInt("id");

                    }
                    else {
                        Toast.makeText(ProfileActivity.this, getString(R.string.failed_get_data), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        queue.add(profileRequest);
    }

    private View.OnClickListener onEditClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            edit();
        }
    };

    private View.OnClickListener onBackClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ProfileActivity.this.finish();
        }
    };

    private void edit(){
        final String name_text = name.getText().toString();
        final String about_text = about.getText().toString();
        final String email_text = email.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(ProfileActivity.this, getString(R.string.success_edit_profile), Toast.LENGTH_LONG).show();

                    }
                    else {
                        Toast.makeText(ProfileActivity.this, getString(R.string.failed_edit_profile), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();

                }
            }
        };

        EditProfileRequest editProfileRequest = new EditProfileRequest(name_text, about_text, email_text, profile_id, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(editProfileRequest);
    }

}