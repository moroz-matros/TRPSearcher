package com.example.trpsearcher.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.trpsearcher.AuthActivity;
import com.example.trpsearcher.LoginRequest;
import com.example.trpsearcher.MenuActivity;
import com.example.trpsearcher.R;
import com.example.trpsearcher.RegisterRequest;
import com.example.trpsearcher.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private EditText login;
    private EditText email;
    private EditText birthdate;
    private EditText name;
    private EditText about;
    private EditText favorites;
    String profileId;
    Integer user_id;
    private Button editbtn;

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        login = root.findViewById(R.id.pr_user_login);
        email = root.findViewById(R.id.pr_user_email);
        birthdate = root.findViewById(R.id.pr_birthday);
        name = root.findViewById(R.id.pr_user_name);
        about = root.findViewById(R.id.pr_about_text);
        favorites = root.findViewById(R.id.pr_fav_tags);
        Intent intent = getActivity().getIntent();
        String loginString = intent.getStringExtra("login");
        user_id = intent.getIntExtra("id", 0);
        login.setText(loginString);
        editbtn = root.findViewById(R.id.pr_edit_btn);
        getData(loginString);
        editbtn.setOnClickListener(onEditClickListener);


        //profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});
        return root;
    }

    private void getData(String username){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        email.setText(jsonResponse.getString("email"));
                        birthdate.setText(jsonResponse.getString("birthdate"));
                        name.setText(jsonResponse.getString("name"));
                        about.setText(jsonResponse.getString("about"));
                        favorites.setText(jsonResponse.getString("favorites"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ProfileRequest profileRequest = new ProfileRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(profileRequest);
    }

    private View.OnClickListener onEditClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            edit();

        }
    };

    private void edit(){
        final String namet = name.getText().toString();
        final String aboutt = about.getText().toString();
        final String emailt = email.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success){
                        Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    }
                    else {

                    }
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();

                }
            }
        };

        EditProfileRequest editProfileRequest = new EditProfileRequest(namet, aboutt, emailt, user_id, responseListener);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(editProfileRequest);


    }

}