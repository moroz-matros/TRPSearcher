package com.example.trpsearcher.ui.board;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.AuthActivity;
import com.example.trpsearcher.MenuActivity;
import com.example.trpsearcher.R;
import com.example.trpsearcher.ui.games.GamesGetRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class BoardFragment extends Fragment {

    private BoardViewModel boardViewModel;
    private Integer user_id;
    Button createButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        boardViewModel =
                ViewModelProviders.of(this).get(BoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_board, container, false);
        createButton = root.findViewById(R.id.bd_btn_create);
        createButton.setOnClickListener(onCreateClickListener);
        Intent intent = getActivity().getIntent();
        user_id = intent.getIntExtra("id", 0);
        getForms();

        return root;
    }


    private View.OnClickListener onCreateClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent startCreateActivity = new Intent(getActivity().getApplicationContext(), CreateFormActivity.class);
            startCreateActivity.putExtra("id", user_id);
            startActivity(startCreateActivity);
        }
    };

    private void getForms(){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            Toast.makeText(getActivity().getApplicationContext(), "Получены", Toast.LENGTH_LONG).show();
                            //int age = jsonResponse.getInt("age");
                            //intent.putExtra("age", age);
                            //intent.putExtra("username", username);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
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

            GetFormsRequest getFormsRequest = new GetFormsRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(getFormsRequest);
    }
}