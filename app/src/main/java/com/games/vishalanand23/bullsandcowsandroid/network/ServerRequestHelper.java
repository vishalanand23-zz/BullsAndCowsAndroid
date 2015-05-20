package com.games.vishalanand23.bullsandcowsandroid.network;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.games.vishalanand23.bullsandcowsandroid.data.PlayResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequestHelper {
    private final Context context;
    String URL = "http://ubuntu@ec2-52-24-182-231.us-west-2.compute.amazonaws.com:8080/bullsAndCows/rest/insert/result";

    public ServerRequestHelper(Context context) {
        this.context = context;
    }

    public void postRequest(PlayResult result) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        String jsonString = gson.toJson(result);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsObjRequest =
                new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println("response: " + response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("error");
                            }
                        }
                );
        queue.add(jsObjRequest);
    }
}
