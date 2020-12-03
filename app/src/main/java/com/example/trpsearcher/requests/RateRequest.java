package com.example.trpsearcher.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RateRequest extends StringRequest {
    private Map<String, String> params;

    public RateRequest(Integer user_id, Integer cur_id, Integer rate, String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("user_id", user_id.toString());
        params.put("cur_id", cur_id.toString());
        params.put("rate", rate.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
