package com.example.doc_app_android.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.Xray_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class xray_service {
    private MutableLiveData<ArrayList<Xray_data>> data_model;
    private MutableLiveData<Xray_data> data;
    private Application app;
    private SharedPreferences prefs;
    private boolean isDoc;

    public LiveData<ArrayList<Xray_data>> getX_ray_data(Application app) {
        prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isDoc = prefs.getBoolean("isDoc", false);
        this.app = app;

        if(isDoc){
            data_model = new MutableLiveData<>();
            loadData();
        } else {
            if (data_model == null) {
                data_model = new MutableLiveData<>();
                loadData();
            }
        }
        return data_model;
    }

    private void loadData() {
        String url;
        dialogs dialog = new dialogs();
        prefs = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isDoc = prefs.getBoolean("isDoc", false);
        if(isDoc){
            url = Globals.xray + prefs.getString("patient_id", "-1");
            Log.d("Testing", "Url for Patient history from doctor patient list: " + url);
        } else {
            url = Globals.xray + prefs.getString("id", "-1");
        }
        ArrayList<Xray_data> XrayData = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("TAG", "onResponse: " + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String xray_ID, category, date, time, imageUrl, reportId, reportDate, reportData;
                        JSONObject obj = response.getJSONObject(i);
                        xray_ID = obj.getString("pic_id");
                        imageUrl = obj.getString("image");
                        time = obj.getString("time");
                            date = obj.getString("date");
//                        date = "30-30-30";
                        category = obj.getString("category");
                        JSONObject reportObj = obj.getJSONObject("report");
                        reportId = reportObj.getString("id");
                        reportDate = reportObj.getString("date");
                        Log.e("TAG time xray", "onResponse: reportdate "+ reportDate );
                        reportData = reportObj.getString("data");

                        XrayData.add(new Xray_data(xray_ID, category, date, time, imageUrl, reportId, reportDate, reportData));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TAG", "loadData: final xray" + XrayData);
                data_model.setValue(XrayData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xray_service", "onErrorResponse: "+error.getLocalizedMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
