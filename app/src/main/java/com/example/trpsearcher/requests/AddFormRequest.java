package com.example.trpsearcher.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddFormRequest extends StringRequest {
    private Map<String, String> params;

    public AddFormRequest(String title, String text, Integer id, String URL, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("title", title);
        params.put("text", text);
        params.put("id", id.toString());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}




