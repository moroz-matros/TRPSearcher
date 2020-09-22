package com.example.trpsearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    private Button signIn;
    private TextView register;
    private EditText login;
    private EditText password;

    public void onClickRegister(View view){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signIn = findViewById(R.id.login_login);
        register = findViewById(R.id.login_registration);
        login = findViewById(R.id.login_edit);
        password = findViewById(R.id.login_password);



    }
}