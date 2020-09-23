package com.example.trpsearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AuthActivity extends AppCompatActivity {

    private Button signIn;
    private TextView registerText;
    private EditText login;
    private EditText password;

    private View.OnClickListener onRegisterClickListener  = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent startRegistrationActivity = new Intent(AuthActivity.this, RegistrationActivity.class);
            startActivity(startRegistrationActivity);
        }
    };

    public void onClickRegister(View view){
        Intent startRegistrationActivity = new Intent(AuthActivity.this, RegistrationActivity.class);
        startActivity(startRegistrationActivity);
    }

    public void onClickLogin(View view){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signIn = findViewById(R.id.login_login);
        registerText = findViewById(R.id.login_registration);
        login = findViewById(R.id.login_edit);
        password = findViewById(R.id.login_password);
        registerText.setOnClickListener(onRegisterClickListener);





    }
}