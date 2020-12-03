package com.example.trpsearcher.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendPostRequest extends StringRequest {
    private Map<String, String> params;

    public SendPostRequest(Integer id, Integer user_id, Integer user2_id, String text, String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("id", id.toString());
        params.put("user_id", user_id.toString());
        params.put("user2_id", user2_id.toString());
        params.put("text", text);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
