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
import com.example.trpsearcher.datas.GameData;

import java.util.ArrayList;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private ArrayList<GameData> dataArrayList;
    private Activity activity;

    public GameAdapter(Activity activity, ArrayList<GameData> dataArrayList){
        this.activity = activity;
        this.dataArrayList = dataArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_game, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GameData data = dataArrayList.get(position);

        //Set name on text View
        holder.login.setText(data.getLogin());
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

        TextView text, login;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.gm_descr);
            login = itemView.findViewById(R.id.gm_title);
            cardView = itemView.findViewById(R.id.gm_container);

        }
    }
}

