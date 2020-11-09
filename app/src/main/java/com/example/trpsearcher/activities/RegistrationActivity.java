package com.example.trpsearcher.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.ErrorDetector;
import com.example.trpsearcher.R;
import com.example.trpsearcher.requests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText date;
    private EditText login;
    private EditText password;
    private EditText c_password;
    private EditText email;
    private Button regButton;
    private ProgressBar loading;

    private Calendar birthday = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        login = findViewById(R.id.rg_login);
        email = findViewById(R.id.rg_email);
        date = findViewById(R.id.rg_birthday);
        password = findViewById(R.id.rg_password);
        c_password = findViewById(R.id.rg_confirm_password);
        loading = findViewById(R.id.loading);
        regButton = findViewById(R.id.rg_btn);
        regButton.setOnClickListener(onRegClickListener);

    }

    public void setDate(View view) {
        new DatePickerDialog(RegistrationActivity.this, d,
                birthday.get(Calendar.YEAR),
                birthday.get(Calendar.MONTH),
                birthday.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {

        date.setText(DateUtils.formatDateTime(this,
                birthday.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }


    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            birthday.set(Calendar.YEAR, year);
            birthday.set(Calendar.MONTH, monthOfYear);
            birthday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    private View.OnClickListener onRegClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if (check() && checkPassword()) AddUser();
        }
    };

    private boolean checkPassword() {
        if (!password.getText().toString().equals(c_password.getText().toString())){
            Toast.makeText(RegistrationActivity.this, getString(R.string.psws_not_eq), Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private boolean check() {
        ErrorDetector ed = new ErrorDetector();
        return (ed.lengthCheckMax(login, 20) && ed.lengthCheckMin(login, 6)
        && ed.lengthCheckMin(password, 6) && ed.lengthCheckMax(password, 20)
        && ed.lengthCheckMax(email, 30) && ed.isNotEmpty(email)
        && ed.isNotEmpty(date));
    }

    private void AddUser(){
        loading.setVisibility(View.VISIBLE);
        regButton.setVisibility(View.GONE);
        final String login = this.login.getText().toString();
        final String password = this.password.getText().toString();
        final String email = this.email.getText().toString();
        final String birthdate = this.date.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(RegistrationActivity.this, getString(R.string.success_register), Toast.LENGTH_LONG).show();
                        RegistrationActivity.this.finish();

                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, jsonObject.getString("response"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
                loading.setVisibility(View.GONE);
                regButton.setVisibility(View.VISIBLE);
            }
        };
        String URL = getString(R.string.ip) + getString(R.string.register_php);
        RegisterRequest stringRequest = new RegisterRequest(login, password, email, birthdate, URL, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}