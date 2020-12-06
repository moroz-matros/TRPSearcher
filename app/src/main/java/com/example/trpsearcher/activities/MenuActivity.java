package com.example.trpsearcher.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trpsearcher.R;

public class MenuActivity extends AppCompatActivity {

    private String user_login;
    private Integer user_id;
    private Button profile, games, chats, board;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        profile = findViewById(R.id.menu_profile);
        games = findViewById(R.id.menu_games);
        chats = findViewById(R.id.menu_chats);
        board = findViewById(R.id.menu_board);
        text = findViewById(R.id.menu_text);

        text.setText(R.string.welcome_text);

        profile.setOnClickListener(onButtonClickListener);
        games.setOnClickListener(onButtonClickListener);
        chats.setOnClickListener(onButtonClickListener);
        board.setOnClickListener(onButtonClickListener);

        Intent intent = getIntent();
        user_login = intent.getStringExtra("user_login");
        user_id = intent.getIntExtra("user_id", 0);

    }

    private View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.menu_profile:
                    Intent startProfileActivity = new Intent(MenuActivity.this, ProfileActivity.class);
                    startProfileActivity.putExtra("user_login", user_login);
                    startProfileActivity.putExtra("user_id", user_id);
                    startActivity(startProfileActivity);
                    break;
                case R.id.menu_chats:
                    Intent startChatActivity = new Intent(MenuActivity.this, ChatActivity.class);
                    startChatActivity.putExtra("user_login", user_login);
                    startChatActivity.putExtra("user_id", user_id);
                    startActivity(startChatActivity);
                    break;
                case R.id.menu_board:
                    Intent startBoardActivity = new Intent(MenuActivity.this, BoardActivity.class);
                    startBoardActivity.putExtra("user_login", user_login);
                    startBoardActivity.putExtra("user_id", user_id);
                    startActivity(startBoardActivity);
                    break;
                case R.id.menu_games:
                    Intent startGamesActivity = new Intent(MenuActivity.this, GamesActivity.class);
                    startGamesActivity.putExtra("user_login", user_login);
                    startGamesActivity.putExtra("user_id", user_id);
                    startActivity(startGamesActivity);
                    break;
            }
        }
    };

}
