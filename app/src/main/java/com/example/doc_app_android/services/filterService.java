package com.example.doc_app_android.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.FilterData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class filterService {
    private MutableLiveData<ArrayList<FilterData>> data;

    public LiveData<ArrayList<FilterData>> getdata(Application app) {
        if (data == null) {
            data = new MutableLiveData<>();
            loaddata(app);
        }
        return data;
    }

    private void loaddata(Application app) { // calll to api containg the list should be called
        SharedPreferences pref = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);

        ArrayList<FilterData> list = new ArrayList<>();
        if (pref.getBoolean("isDoc", false)) {
            final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Globals.docFilter, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String id, filter, department;
                            JSONObject obj = response.getJSONObject(i);
                            id = obj.getString("id");
                            filter = obj.getString("problem");
                            department = obj.getString("Department");
                            Log.d("TAG", "onResponse: " + filter);
                            list.add(new FilterData(filter, id, department));
                        }
                        data.setValue(list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(app, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders () throws AuthFailureError {
                SharedPreferences pref = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                params.put("Username", pref.getString("username", ""));
                params.put("Password", pref.getString("pass", ""));
                return params;
            }
        } ;
        requestQueue.add(request);
//            list.add(new FilterData("wrist"));
//            list.add(new FilterData("ELbow"));
//            list.add(new FilterData("Hip"));
//            list.add(new FilterData("Spine"));
//            list.add(new FilterData("ORTHOLOGIST"));
//            list.add(new FilterData("Knee"));
    } else

    {
        String[] patientFilters = app.getResources().getStringArray(R.array.patientFilters);
        for (String patientFilter : patientFilters) {
            list.add(new FilterData(patientFilter));
        }
        data.setValue(list);
    }

}

}
