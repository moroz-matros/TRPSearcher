package com.example.trpsearcher.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private Integer user_id;
    private JSONArray jsonArray;
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<ChatOutData> dataArrayList = new ArrayList<>();
    ChatAdapter adapter;
    int current = 0;
    int maxSize = 0;
    String login;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        Intent intent = getActivity().getIntent();
        user_id = intent.getIntExtra("id", 0);
        login = intent.getStringExtra("login");

        nestedScrollView = root.findViewById(R.id.ch_scroll_view);
        recyclerView = root.findViewById(R.id.ch_recycler_view);
        progressBar = root.findViewById(R.id.ch_progress_bar);

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        getChats();
        //Set adapter
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
                ChatOutData data = new ChatOutData();
                Integer flag = jsonArray.getJSONObject(current).getInt("id_have_new");
                if (flag == 1){
                    data.setHas_new(true);
                } else{
                    data.setHas_new(false);
                }

                JSONObject currentObj = jsonArray.getJSONObject(current);
                data.setUser(jsonArray.getJSONObject(current).getString("login"));
                data.setUser_id(user_id);
                data.setUser2_id(jsonArray.getJSONObject(current).getInt("id"));
                data.setJsonArray(currentObj.getJSONArray("messages"));
                data.setUser_login(login);

                //Add data
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //init adapter
            adapter = new ChatAdapter(getActivity(), dataArrayList, user_id);
            //set adapter
            recyclerView.setAdapter(adapter);
        }
    }



    private void getChats() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    jsonArray = jsonResponse.getJSONArray("response");
                    maxSize = jsonArray.length();

                    if (success) {
                        getData();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ChatsRequest chatsRequest = new ChatsRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(chatsRequest);
    }
}