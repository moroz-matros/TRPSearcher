package com.example.trpsearcher.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ChangeStatusRequest extends StringRequest {
   private Map<String, String> params;

    public ChangeStatusRequest(Integer user_id, Integer user2_id, String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("id", user_id.toString());
        params.put("id2", user2_id.toString());
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
