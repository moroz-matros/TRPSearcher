package com.example.trpsearcher.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trpsearcher.R;
import com.example.trpsearcher.activities.GameActivity;
import com.example.trpsearcher.datas.GamesData;

import java.util.ArrayList;


public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private ArrayList<GamesData> dataArrayList;
    private Activity activity;

    public GamesAdapter(Activity activity, ArrayList<GamesData> dataArrayList){
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

        final GamesData data = dataArrayList.get(position);

        //Set name on text View
        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());

        View.OnClickListener onGameClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent startGameActivity = new Intent(activity, GameActivity.class);
                startGameActivity.putExtra("id", data.getId());
                startGameActivity.putExtra("user_id", data.getUser_id());
                startGameActivity.putExtra("user2_id", data.getUser2_id());
                startGameActivity.putExtra("jsonArray", data.getJsonArray().toString());
                startGameActivity.putExtra("user2_login", data.getUser2_login());
                startGameActivity.putExtra("user_login", data.getUser_login());
                startGameActivity.putExtra("closed", data.getClosed());
                activity.startActivity(startGameActivity);
            }
        };

        holder.cardView.setOnClickListener(onGameClickListener);

    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gm_title);
            description = itemView.findViewById(R.id.gm_descr);
            cardView = itemView.findViewById(R.id.gm_container);

        }
    }
}

