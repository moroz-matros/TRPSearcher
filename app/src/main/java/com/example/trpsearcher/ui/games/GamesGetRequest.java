package com.example.trpsearcher.ui.games;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GamesGetRequest extends StringRequest {

    private static final String URL = "http://192.168.0.13/get_games.php";
    private Map<String, String> params;

    public GamesGetRequest(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
