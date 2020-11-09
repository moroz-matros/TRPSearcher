package com.example.trpsearcher.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.R;
import com.example.trpsearcher.activities.ChatActivity;
import com.example.trpsearcher.activities.MessageActivity;
import com.example.trpsearcher.activities.UserProfileActivity;
import com.example.trpsearcher.datas.BoardData;
import com.example.trpsearcher.requests.ChatsRequest;
import com.example.trpsearcher.requests.CloseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private ArrayList <BoardData> dataArrayList;
    private Activity activity;
    private Integer  user_id;

    public BoardAdapter(Activity activity, ArrayList<BoardData> dataArrayList, Integer user_id){
        this.activity = activity;
        this.dataArrayList = dataArrayList;
        this.user_id = user_id;
    }

    private void close(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(activity, response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    Toast.makeText(activity, jsonResponse.getString("response"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String URL = activity.getString(R.string.ip) + activity.getString(R.string.close_php);
        CloseRequest closeRequest = new CloseRequest(user_id, activity.getString(R.string.form), URL, responseListener);
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(closeRequest);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Init main data
        BoardData data = dataArrayList.get(position);

        //Set name on text View
        holder.titleText.setText(data.getTitle());
        holder.textText.setText(data.getText());
        final Integer user2_id = data.getUser2_id();

        if (!user2_id.equals(user_id)) {

            View.OnClickListener onMessageClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startMessageActivity = new Intent(activity, MessageActivity.class);
                    startMessageActivity.putExtra("user_id", user_id);
                    startMessageActivity.putExtra("user2_id", user2_id);
                    activity.startActivity(startMessageActivity);
                }
            };

            View.OnClickListener onProfileClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent startUserProfileActivity = new Intent(activity, UserProfileActivity.class);
                    startUserProfileActivity.putExtra("user2_id", user2_id);
                    startUserProfileActivity.putExtra("user_id", user_id);
                    activity.startActivity(startUserProfileActivity);
                }
            };

            holder.btnProfile.setOnClickListener(onProfileClickListener);
            holder.btnMessage.setOnClickListener(onMessageClickListener);
        } else {
            holder.btnProfile.setVisibility(View.GONE);
            holder.btnMessage.setVisibility(View.GONE);
            holder.btnClose.setVisibility(View.VISIBLE);

            View.OnClickListener onCloseClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    close();
                }
            };
            holder.btnClose.setOnClickListener(onCloseClickListener);

        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView textText;
        Button btnProfile;
        Button btnMessage;
        Button btnClose;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.bd_title);
            textText = itemView.findViewById(R.id.bd_text);
            btnProfile = itemView.findViewById(R.id.bd_prof_owner);
            btnMessage = itemView.findViewById(R.id.bd_message);
            btnClose = itemView.findViewById(R.id.bd_close_btn);
        }
    }



}
