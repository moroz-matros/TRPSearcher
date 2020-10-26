package com.example.trpsearcher.ui.board;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class GetFormsRequest extends StringRequest {
    private static final String URL = "http://192.168.0.13/get_forms.php";
    private Map<String, String> params;

    public GetFormsRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
