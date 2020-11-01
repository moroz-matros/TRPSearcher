package com.example.trpsearcher.ui.board;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BoardFragment extends Fragment {

    private Integer user_id;
    private JSONObject jsonResponse;
    private JSONArray jsonArray;
    Button createButton;

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int current = 0;
    int maxSize = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_board, container, false);
        getForms();
        createButton = root.findViewById(R.id.bd_btn_create);
        createButton.setOnClickListener(onCreateClickListener);
        Intent intent = getActivity().getIntent();
        user_id = intent.getIntExtra("id", 0);

        nestedScrollView = root.findViewById(R.id.scroll_view);
        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progress_bar);

        //Init adapter

        adapter = new MainAdapter(getActivity(), dataArrayList, user_id);

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        //Set adapter
        recyclerView.setAdapter(adapter);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //condition
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    //progressBar
                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                    }
                }
            });

        return root;
    }

    private void getData() {
        progressBar.setVisibility(View.GONE);
        int next = current+5;
        for (; (current<next) && (current < maxSize); current++){
            try{
                //Init main data
                MainData data = new MainData();
                data.setTitle(jsonArray.getJSONObject(current).getString("title"));
                data.setText(jsonArray.getJSONObject(current).getString("text"));
                data.setId_owner(jsonArray.getJSONObject(current).getInt("id_owner"));
                //Add data
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //init adapter
            adapter = new MainAdapter(getActivity(), dataArrayList, user_id);
            //set adapter
            recyclerView.setAdapter(adapter);
        }
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
                        jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        jsonArray = jsonResponse.getJSONArray("response");
                        maxSize = jsonArray.length();

                        if (success) {
                            getData();
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