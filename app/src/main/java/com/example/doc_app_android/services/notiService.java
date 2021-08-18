package com.example.doc_app_android.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.NotiData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class notiService {
    private MutableLiveData<ArrayList<NotiData>> data_model;
    private MutableLiveData<NotiData> data;
    private NotiData notiData;
    private Application app;
    private SharedPreferences prefs;
    private String nextURL,counter;


    public LiveData<ArrayList<NotiData>> getNotiData(Application app) {
        prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        this.app = app;
                data_model = new MutableLiveData<>();
                loadData();
        return data_model;
    }

    public void loadData() {
        ArrayList<NotiData> notiDataArrayList = new ArrayList<>();
        String url = Globals.notifications;
        prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = new JSONArray();

                    for (int i = 0; i < resultArray.length(); i++) {
                            String id, data, sender, receiver;
                            JSONObject singleObject = resultArray.getJSONObject(i);
                            id = singleObject.getString("id");
                            data = singleObject.getString("data");
                            sender = singleObject.getString("sender");
                            receiver = singleObject.getString("reciever");
                            notiDataArrayList.add(new NotiData(id, data, sender, receiver));
                    }
                    data_model.setValue(notiDataArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("notiData", "onErrorResponse: " + error.getLocalizedMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    
    public void acceptReq(NotiData notificationData , Context context){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject param = new JSONObject();
        int lenght = notificationData.getData().length();
        try {
            param.put("date",notificationData.getData().substring(lenght-10));
            param.put("patient",notificationData.getSender());
            Toast.makeText(context, notificationData.getData().substring(lenght-10), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        param.put("date",);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.newNotifications, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public void rejectRequest(NotiData notificationData , Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject param = new JSONObject();
        int lenght = notificationData.getData().length();
        try {
            param.put("date",notificationData.getData().substring(lenght-10));
            param.put("patient",notificationData.getSender());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        param.put("date",);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.newNotifications, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
