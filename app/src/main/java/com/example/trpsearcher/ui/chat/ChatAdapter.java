package com.example.trpsearcher.ui.chat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trpsearcher.MenuActivity;
import com.example.trpsearcher.R;
import com.example.trpsearcher.ui.MessageActivity;

import org.json.JSONArray;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private ArrayList<ChatOutData> dataArrayList;
        private Activity activity;
        private Integer user_id;

        public ChatAdapter(Activity activity, ArrayList<ChatOutData> dataArrayList, Integer user_id){
            this.activity = activity;
            this.dataArrayList = dataArrayList;
            this.user_id = user_id;
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
            holder.chat.setText(data.getUser());
            if (data.getHas_new()) {
                holder.cardView.setBackgroundColor(Color.parseColor("#FF7FC1CA"));
            }


            View.OnClickListener onChatClickListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent startChatActivity = new Intent(activity, ChatActivity.class);
                    startChatActivity.putExtra("user_id", user_id);
                    startChatActivity.putExtra("user2_id", data.getUser2_id());
                    startChatActivity.putExtra("jsonArray", data.getJsonArray().toString());
                    startChatActivity.putExtra("login2", data.getUser());
                    startChatActivity.putExtra("login", data.getUser_login());
                    startChatActivity.putExtra("user_hasNew", data.getHas_new());
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

