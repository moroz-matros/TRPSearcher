package com.example.trpsearcher.activities;

import android.app.AlertDialog;
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
import com.example.trpsearcher.requests.AddFormRequest;
import com.example.trpsearcher.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateFormActivity extends AppCompatActivity {

    private EditText title;
    private EditText text;
    private Button addButton;
    private Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);
        title = findViewById(R.id.cr_title);
        text = findViewById(R.id.cr_text);
        addButton = findViewById(R.id.cr_btn_add);
        addButton.setOnClickListener(onAddClickListener);
        Intent intent = getIntent();
        user_id = intent.getIntExtra("id", 0);


    }

    private View.OnClickListener onAddClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            add();
            CreateFormActivity.this.finish();
        }
    };

    private void add(){
        final String titleText = title.getText().toString();
        final String textText = text.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(CreateFormActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(CreateFormActivity.this, "Объявление успешно добавлено", Toast.LENGTH_LONG).show();
                        //int age = jsonResponse.getInt("age");
                        //intent.putExtra("age", age);
                        //intent.putExtra("username", username);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateFormActivity.this);
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

        AddFormRequest addRequest = new AddFormRequest(titleText, textText, user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreateFormActivity.this);
        queue.add(addRequest);
    }
}
