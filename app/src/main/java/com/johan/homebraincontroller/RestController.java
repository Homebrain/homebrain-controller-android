package com.johan.homebraincontroller;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class RestController {

    public static final String TAG = "VolleyPatterns";

    private RequestQueue mRequestQueue = null;

    private static RestController sInstance = null;

    private static Context mCtx;

    // Defeats instantiation
    protected RestController(){
        mCtx = ApplicationController.getInstance();
    }

    public static synchronized RestController getInstance() {
        if (sInstance == null){
            sInstance = new RestController();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ApplicationController.getInstance());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void createJsonPostRequest(JSONObject data){
        String IP = SettingsController.getInstance().getIpPort();
        String URL = "http://"+IP+"/api/v0/event";
        Log.w("myApp", "Creating JsonPostRequest");

        JsonObjectRequest req = new JsonObjectRequest(URL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            Log.w("myApp", "Got volley recv error");
                            Log.w("myApp", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("myApp", "Got volley send error");
                Log.w("myApp", error.toString());
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        // add the request object to the queue to be executed
        addToRequestQueue(req);
    }

    public void createJsonGetRequest(String location){
        String IP = SettingsController.getInstance().getIpPort();
        String URL = "http://" + IP + location;
        Log.d(TAG, "Sending request to " + URL);

        // pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e){
                            Log.e("error", e.toString());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final String msg = "Node request failed, invalid IP or port?";
                Log.e(TAG, msg);
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

}
