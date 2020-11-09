package com.example.trpsearcher;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditProfileRequest extends StringRequest {
    private static final String URL = "http://192.168.0.13/edit_profile.php";
    private Map<String, String> params;

    public EditProfileRequest(String name, String about, String email, Integer id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("about", about);
        params.put("email", email);
        params.put("profile_id", id.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
