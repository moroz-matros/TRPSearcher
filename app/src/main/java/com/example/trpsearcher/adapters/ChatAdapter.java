package com.example.trpsearcher.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trpsearcher.R;
import com.example.trpsearcher.activities.MessageChatActivity;
import com.example.trpsearcher.datas.ChatOutData;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private ArrayList<ChatOutData> dataArrayList;
        private Activity activity;

        public ChatAdapter(Activity activity, ArrayList<ChatOutData> dataArrayList){
            this.activity = activity;
            this.dataArrayList = dataArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row_chat, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //Init main data
            final ChatOutData data = dataArrayList.get(position);

            //Set name on text View
            holder.chat.setText(data.getUser2_login());

            //Color for new messages
            if (data.getHas_new()) {
                holder.cardView.setBackgroundColor(Color.parseColor("#FF7FC1CA"));
            }

            View.OnClickListener onChatClickListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent startChatActivity = new Intent(activity, MessageChatActivity.class);
                    startChatActivity.putExtra("user_id", data.getUser_id());
                    startChatActivity.putExtra("login", data.getUser_login());
                    startChatActivity.putExtra("user2_id", data.getUser2_id());
                    startChatActivity.putExtra("login2", data.getUser2_login());
                    startChatActivity.putExtra("user_hasNew", data.getHas_new());
                    startChatActivity.putExtra("jsonArray", data.getJsonArray().toString());
                    activity.startActivity(startChatActivity);
                }
            };

            holder.chat.setOnClickListener(onChatClickListener);

        }

        @Override
        public int getItemCount() {
            return dataArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView chat;
            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                chat = itemView.findViewById(R.id.ch_chat);
                cardView = itemView.findViewById(R.id.ch_container);

            }
        }



}

