package com.example.trpsearcher;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProfileRequest extends StringRequest {
    private static final String URL = "http://192.168.0.13/profile.php";
    private Map<String, String> params;

    public ProfileRequest(Integer id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", id.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
