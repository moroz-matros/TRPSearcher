package com.example.trpsearcher.ui.games;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.R;

import org.json.JSONException;
import org.json.JSONObject;

public class GamesFragment extends Fragment {


    private GamesViewModel gamesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gamesViewModel =
                ViewModelProviders.of(this).get(GamesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_games, container, false);
        getGames();
        return root;
    }

    private void getGames(){
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

        GamesGetRequest gamesGetRequest = new GamesGetRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(gamesGetRequest);
    }
}