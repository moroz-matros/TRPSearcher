package com.example.trpsearcher.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CloseRequest extends StringRequest {
    private Map<String, String> params;

    public CloseRequest(Integer id, String type, String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("id", id.toString());
        params.put("type", type);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

