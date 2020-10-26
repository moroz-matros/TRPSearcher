package com.example.trpsearcher.ui.board;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.AuthActivity;
import com.example.trpsearcher.MenuActivity;
import com.example.trpsearcher.R;
import com.example.trpsearcher.ui.games.GamesGetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BoardFragment extends Fragment {

    private BoardViewModel boardViewModel;
    private Integer user_id;
    Button createButton;

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int page = 1, limit = 10;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        boardViewModel =
                ViewModelProviders.of(this).get(BoardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_board, container, false);
        /*createButton = root.findViewById(R.id.bd_btn_create);
        createButton.setOnClickListener(onCreateClickListener);
        Intent intent = getActivity().getIntent();
        user_id = intent.getIntExtra("id", 0);
        getForms();
*/
        nestedScrollView = root.findViewById(R.id.scroll_view);
        recyclerView = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progress_bar);

        //Init adapter

        adapter = new MainAdapter(getActivity(), dataArrayList);

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        //Set adapter
        recyclerView.setAdapter(adapter);

        //Create get data method

        getData(page, limit);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //condition
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    //reach last item position
                    //increase page size
                    page++;
                    //progressBar
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });


        return root;
    }

    private void getData(int page, int limit) {
        //Init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //Create main interface
        MainInterface mainInterface = retrofit.create(MainInterface.class);
        //Call
        Call<String> call = mainInterface.STRING_CALL(page, limit);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                //Condition
                if (response.isSuccessful() && response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseResult(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void parseResult(JSONArray jsonArray) {
        for (int i=0; i<jsonArray.length(); i++){
            try{
                JSONObject object = jsonArray.getJSONObject(i);
                //Init main data
                MainData data = new MainData();
                data.setImage(object.getString("download_url"));
                data.setName(object.getString("author"));
                //Add data
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //init adapter
            adapter = new MainAdapter(getActivity(), dataArrayList);
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

            GetFormsRequest getFormsRequest = new GetFormsRequest(responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(getFormsRequest);
    }
}