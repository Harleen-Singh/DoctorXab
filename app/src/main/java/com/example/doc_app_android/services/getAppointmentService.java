package com.example.doc_app_android.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.DocData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getAppointmentService {
    public void getAppointment(DocData docData, CharSequence date, Context context, ProgressDialog dialog1){

        SharedPreferences pref = context.getSharedPreferences("tokenFile",Context.MODE_PRIVATE);
        JSONObject obj = new JSONObject();
        try {
            obj.put("doctor",docData.getDocID());
            obj.put("time","");
            obj.put("date",date);

            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            dialogs dialog = new dialogs();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.askAppointment, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dialog1.dismiss();
                    dialog.displayDialog("Request Sent Sucessfully", context);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog1.dismiss();
                    dialog.displayDialog("Error in Sending Request ", context);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
