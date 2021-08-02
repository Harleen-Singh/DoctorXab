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
import com.example.doc_app_android.data_model.Pat_prescription;
import com.example.doc_app_android.data_model.PrescData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class prescService {
    private MutableLiveData<ArrayList<PrescData>> data_model;
    private Application app;

    public LiveData<ArrayList<PrescData>> getPrescData(Application app) {
        this.app = app;
        if (data_model == null) {
            data_model = new MutableLiveData<>();
            loadData();
        }
        return data_model;
    }

    private void loadData() {
        dialogs dialog = new dialogs();
        SharedPreferences prefs = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        ArrayList<PrescData> prescTemp = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Globals.prescription + prefs.getString("id", "-1"), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("TAG", "onResponse: " + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String prescID , date;
                        ArrayList<Pat_prescription> presc = new ArrayList<>();
                        JSONObject obj = response.getJSONObject(i);
                        prescID = obj.getString("id");
                        date = obj.getString("date");
                        JSONObject detailObj = obj.getJSONObject("details");
                        for (int j = 1; j <= detailObj.length(); j++) {
                            Log.e("TAG", "onResponse: "+ detailObj.getString(String.valueOf(j)) );
                            presc.add(new Pat_prescription(detailObj.getString(String.valueOf(j))));
                        }
                        Log.d("TAG", "onResponse prescription: " + presc.get(0).getPrescription());
                        prescTemp.add(new PrescData(date,prescID,presc));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                data_model.setValue(prescTemp);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("prescService", "onErrorResponse: "+error.getLocalizedMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
