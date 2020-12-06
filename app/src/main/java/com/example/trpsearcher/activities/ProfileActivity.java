package com.example.trpsearcher.activities;

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
import com.example.trpsearcher.ErrorDetector;
import com.example.trpsearcher.requests.EditProfileRequest;
import com.example.trpsearcher.requests.ProfileRequest;
import com.example.trpsearcher.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private EditText login, email, birthdate, name, about;
    private Button editButton;
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

        Intent intent = getIntent();
        user_login = intent.getStringExtra("user_login");
        user_id = intent.getIntExtra("user_id", 0);

        login.setText(user_login);
        editButton.setOnClickListener(onEditClickListener);

        getData();

    }
    private void getData(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        email.setText(jsonResponse.getString("email"));
                        birthdate.setText(jsonResponse.getString("birthdate"));
                        name.setText(jsonResponse.getString("name"));
                        about.setText(jsonResponse.getString("about"));
                        rate.setText(getString(R.string.rate_profile) + ' ' + jsonResponse.getString("rate"));
                        profile_id = jsonResponse.getInt("id");

                    }
                    else {
                        Toast.makeText(ProfileActivity.this, jsonResponse.getString("response"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = getString(R.string.ip) + getString(R.string.profile_php);
        ProfileRequest profileRequest = new ProfileRequest(user_id, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        queue.add(profileRequest);
    }

    private View.OnClickListener onEditClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if (check()) edit();
        }
    };

    private boolean check() {
        ErrorDetector ed = new ErrorDetector();
        return (ed.lengthCheckMax(name, 20)
                && ed.lengthCheckMax(email, 30)
                && ed.lengthCheckMax(about, 255)
                && ed.isNotEmpty(email));
    }


    private void edit(){
        final String name_text = name.getText().toString();
        final String about_text = about.getText().toString();
        final String email_text = email.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ProfileActivity.this, jsonObject.getString("response"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e){
                    e.printStackTrace();

                }

            }
        };

        String URL = getString(R.string.ip) + getString(R.string.edit_profile_php);
        EditProfileRequest editProfileRequest = new EditProfileRequest(name_text, about_text, email_text, profile_id, URL, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        requestQueue.add(editProfileRequest);
        //finish();
    }

}