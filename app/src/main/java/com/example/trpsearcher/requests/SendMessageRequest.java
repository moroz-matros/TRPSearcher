package com.example.trpsearcher.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

    public class SendMessageRequest extends StringRequest {
        private Map<String, String> params;

        public SendMessageRequest(Integer user_id, Integer send_to_id, String title, String message, String URL, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            params = new HashMap<>();
            params.put("user_id", user_id.toString());
            params.put("user2_id", send_to_id.toString());
            params.put("title", title);
            params.put("message", message);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
