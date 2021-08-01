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
import com.example.doc_app_android.data_model.CkpHstryData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class chkHstryService {
    private MutableLiveData<ArrayList<CkpHstryData>> data_model;
    private MutableLiveData<CkpHstryData> data;
    private CkpHstryData ckpHstryData;
    private Application app;
    private SharedPreferences prefs;
    private boolean isDoc;

    public LiveData<ArrayList<CkpHstryData>> getCkpHstryData(Application app) {

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
            prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
            isDoc = prefs.getBoolean("isDoc", false);

            if(isDoc){
                url = Globals.checkUpHistory + prefs.getString("patient_id", "-1");
                Log.d("Testing", "Url for Patient history from doctor patient list: " + url);
            } else {
                url = Globals.checkUpHistory + prefs.getString("id", "-1");
            }
            ArrayList<CkpHstryData> chkUpdata = new ArrayList<>();
        Log.e("TAG", "loadData: id "+prefs.getString("id", "-1") );
            final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("TAG", "onResponse: "+ response );
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            String reportId,date, data;
                            JSONObject obj = response.getJSONObject(i);
                            reportId = obj.getString("id");
                            date = obj.getString("date");
                            data = obj.getString("data");
                            chkUpdata.add(new CkpHstryData(reportId,date,data));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("TAG", "loadData: final"+ chkUpdata);
                    data_model.setValue(chkUpdata);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.displayDialog(error.getLocalizedMessage(), app.getApplicationContext());
                }
            });
            requestQueue.add(jsonArrayRequest);

    }

    public LiveData<CkpHstryData> get_Xray_desc() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadDescData();
        }
        return data;
    }

    private void loadDescData() {
        String date ,data,reportID;
        reportID = "60";
        data= "data is Unavailable";
        date = "30-02-2012";
        ckpHstryData = new CkpHstryData(reportID , date,data);
        this.data.setValue(ckpHstryData);
    }


}
