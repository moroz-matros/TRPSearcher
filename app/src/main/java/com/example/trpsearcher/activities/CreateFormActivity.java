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
import com.example.trpsearcher.ErrorDetector;
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
            if (check()) {
                add();
                CreateFormActivity.this.finish();
            }
        }
    };

    private boolean check() {
        ErrorDetector ed = new ErrorDetector();
        return (ed.lengthCheckMax(title, 128) && ed.isNotEmpty(title)
                && ed.lengthCheckMaxText(text) && ed.isNotEmpty(text));
    }

    private void add(){
        final String titleText = title.getText().toString();
        final String textText = text.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Toast.makeText(CreateFormActivity.this, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String URL = getString(R.string.ip) + getString(R.string.add_form_php);
        AddFormRequest addRequest = new AddFormRequest(titleText, textText, user_id, URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CreateFormActivity.this);
        queue.add(addRequest);
    }
}
