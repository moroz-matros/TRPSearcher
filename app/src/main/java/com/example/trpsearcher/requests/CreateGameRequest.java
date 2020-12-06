package com.example.trpsearcher.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateGameRequest extends StringRequest {
    private Map<String, String> params;

    public CreateGameRequest(Integer id1, Integer id2, String title, String description, String URL, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", id1.toString());
        params.put("user2_id", id2.toString());
        params.put("title", title);
        params.put("description", description);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}




