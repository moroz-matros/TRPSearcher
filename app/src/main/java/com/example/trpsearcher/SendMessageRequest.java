package com.example.trpsearcher;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

    public class SendMessageRequest extends StringRequest {
        private static final String URL = "http://192.168.0.13/send_message.php";
        private Map<String, String> params;

        public SendMessageRequest(Integer user_id, Integer send_to_id, String title, String message, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            params = new HashMap<>();
            params.put("user_id", user_id.toString());
            params.put("send_to_id", send_to_id.toString());
            params.put("title", title);
            params.put("message", message);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
