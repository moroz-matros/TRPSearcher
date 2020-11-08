package com.example.trpsearcher;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        final Integer owner_id = data.getOwner_id();

        View.OnClickListener onMessageClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent startMessageActivity = new Intent(activity, MessageActivity.class);
                startMessageActivity.putExtra("user_id", user_id);
                startMessageActivity.putExtra("send_to_id", owner_id);
                activity.startActivity(startMessageActivity);
            }
        };

        View.OnClickListener onProfileClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent startUserProfileActivity = new Intent(activity, UserProfileActivity.class);
                startUserProfileActivity.putExtra("owner_id", owner_id);
                startUserProfileActivity.putExtra("user_id", user_id);
                activity.startActivity(startUserProfileActivity);
            }
        };

        holder.btnProfile.setOnClickListener(onProfileClickListener);
        holder.btnMessage.setOnClickListener(onMessageClickListener);
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView textText;
        TextView idText;
        Button btnProfile;
        Button btnMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.bd_title);
            textText = itemView.findViewById(R.id.bd_text);
            btnProfile = itemView.findViewById(R.id.bd_prof_owner);
            btnMessage = itemView.findViewById(R.id.bd_message);
        }
    }



}
