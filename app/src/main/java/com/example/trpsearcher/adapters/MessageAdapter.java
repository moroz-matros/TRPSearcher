package com.example.trpsearcher.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trpsearcher.R;
import com.example.trpsearcher.datas.MessageData;

import java.util.ArrayList;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<MessageData> dataArrayList;
    private Activity activity;

    public MessageAdapter(Activity activity, ArrayList<MessageData> dataArrayList){
        this.activity = activity;
        this.dataArrayList = dataArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_message, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MessageData data = dataArrayList.get(position);

        //Set name on text View
        holder.login.setText(data.getLogin());
        holder.title.setText(data.getTitle());
        holder.text.setText(data.getText());
        if (data.getColors().get(data.getUser_id()) == 0){
            holder.cardView.setBackgroundColor(Color.parseColor("#FFEFD5"));
        }

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, text, login;
        CardView cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.chms_title);
            text = itemView.findViewById(R.id.chms_text);
            login = itemView.findViewById(R.id.chms_login);
            cardView = itemView.findViewById(R.id.chms_container);

        }
    }



}

