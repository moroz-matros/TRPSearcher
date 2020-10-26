package com.example.trpsearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView registerText;
    private EditText login;
    private EditText passwordText;

    private View.OnClickListener onRegisterClickListener  = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent startRegistrationActivity = new Intent(AuthActivity.this, RegistrationActivity.class);
            startActivity(startRegistrationActivity);
        }
    };


    private View.OnClickListener onLoginClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            logIn();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginButton = findViewById(R.id.login_login);
        registerText = findViewById(R.id.login_registration);
        login = findViewById(R.id.login_edit);
        passwordText = findViewById(R.id.login_password);
        registerText.setOnClickListener(onRegisterClickListener);
        loginButton.setOnClickListener(onLoginClickListener);

    }

    private void logIn(){
        final String username = login.getText().toString();
        final String password = passwordText.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(AuthActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String login_name = jsonResponse.getString("login");
                        Integer user_id = jsonResponse.getInt("id");

                        Intent startMenuActivity = new Intent(AuthActivity.this, MenuActivity.class);
                        startMenuActivity.putExtra("login", login_name);
                        startMenuActivity.putExtra("id", user_id);
                        startActivity(startMenuActivity);
                        //intent.putExtra("age", age);
                        //intent.putExtra("username", username);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AuthActivity.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AuthActivity.this);
        queue.add(loginRequest);
    }

}