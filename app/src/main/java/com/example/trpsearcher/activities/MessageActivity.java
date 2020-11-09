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
import com.example.trpsearcher.R;
import com.example.trpsearcher.requests.SendMessageRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageActivity extends AppCompatActivity {

    private EditText title;
    private EditText text;
    private Button addButton;
    private Integer user_id, user2_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_form);
        title = findViewById(R.id.ms_title);
        text = findViewById(R.id.ms_text);
        addButton = findViewById(R.id.ms_btn_add);
        addButton.setOnClickListener(onAddClickListener);
        Intent intent = getIntent();
        user2_id = intent.getIntExtra("user2_id", 0);
        user_id = intent.getIntExtra("user_id", 0);
    }

    private View.OnClickListener onAddClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
           sendMessage();
           MessageActivity.this.finish();
        }
    };

    private void sendMessage(){
        final String titleText = title.getText().toString();
        final String textText = text.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(MessageActivity.this, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(MessageActivity.this, "Сообщение успешно отправлено", Toast.LENGTH_LONG).show();
                        //int age = jsonResponse.getInt("age");
                        //intent.putExtra("age", age);
                        //intent.putExtra("username", username);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
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

        SendMessageRequest sendMessageRequest = new SendMessageRequest(user_id, user2_id, titleText, textText, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MessageActivity.this);
        queue.add(sendMessageRequest);
    }

}
