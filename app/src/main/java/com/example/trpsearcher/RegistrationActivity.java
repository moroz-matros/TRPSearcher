package com.example.trpsearcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText date;
    private EditText login;
    private EditText password;
    private EditText c_password;
    private EditText email;
    private Button btn_reg;
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
        btn_reg = findViewById(R.id.rg_btn);
        btn_reg.setOnClickListener(onRegClickListener);

    }

    public void setDate(View view) {
        new DatePickerDialog(RegistrationActivity.this, d,
                birthday.get(Calendar.YEAR),
                birthday.get(Calendar.MONTH),
                birthday.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
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
            AddUser();

        }
    };

    private void AddUser(){
        loading.setVisibility(View.VISIBLE);
        btn_reg.setVisibility(View.GONE);
        final String login = this.login.getText().toString();
        final String password = this.password.getText().toString();
        final String c_psw = c_password.getText().toString();
        final String email = this.email.getText().toString();
        final String birthdate = this.date.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btn_reg.setVisibility(View.VISIBLE);
                        RegistrationActivity.this.finish();

                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                        builder.setMessage("Register Failed")
                                .create()
                                .show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(RegistrationActivity.this, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    btn_reg.setVisibility(View.VISIBLE);
                }
            }
        };

        RegisterRequest stringRequest = new RegisterRequest(login, password, email, birthdate, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}