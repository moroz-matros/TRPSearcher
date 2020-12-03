package com.example.trpsearcher.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetFormsRequest extends StringRequest {
    private Map<String, String> params;

    public GetFormsRequest(Integer user_id, String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
