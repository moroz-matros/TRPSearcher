package com.example.trpsearcher.requests;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private Map<String, String> params;

    public RegisterRequest(String login, String password, String email, String birthdate, String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);
        params.put("email", email);
        params.put("birthdate", birthdate);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
