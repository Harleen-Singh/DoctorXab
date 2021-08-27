package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.Home;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.data_model.Register_data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signUpService {
    private Register_data data;
    private Activity mContext;
    private ProgressDialog progressDialog;
    public DocData docData;
    private boolean isDoc;
    public MutableLiveData<Boolean> flag = new MutableLiveData<>();
    public String url, url2, url3;
    String id, usernameResp, email;
    private boolean isDoctor;
    dialogs dialog ;
    public signUpService(Register_data data, Activity activity, boolean isDoc, boolean flagCheck) {
        dialog = new dialogs();
        this.data = data;
        this.mContext = activity;
        this.isDoc = isDoc;
        if (!isDoc)
            url = Globals.docRegister;
        else
            url = Globals.patientRegister;
        progressDialog = new ProgressDialog(mContext, R.style.AlertDialog);

        url2 = Globals.addUserData;
        url3 = Globals.addSpecialistList;
        if (flagCheck)
            getSignUpData();
        else
            postSpecialist();
    }

    private void postSpecialist() {
        dialog.alertDialogLogin(progressDialog, "Loading.....");
        SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("user", pref.getString("id", "-1"));
            postparams.put("doctor", Integer.parseInt(data.getSpecialistof()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url3, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    getContinueData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismissDialog(progressDialog);
                if (error instanceof NoConnectionError) {
                    dialog.displayDialog("Not Connected to Internet", mContext);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", mContext);
                } else {
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                    dialog.displayDialog("Username Already Taken", mContext);
                    flag.setValue(false);
                }
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        MyRequestQueue.add(jsonObjectRequest);


    }

    private void getContinueData() {
        Log.d("TAG", "getData: we are in getContinueData");
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("name", data.getName());
            postparams.put("phone_number", data.getContact());
            postparams.put("gender", data.getGender());
            postparams.put("age", data.getAge());
            if (!isDoc)
                postparams.put("department", Integer.parseInt("1"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        Log.e("TAG", "getContinueData: " + "password in prefs " + pref.getString("pass", "") + " usename " + pref.getString("username", ""));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url2 + pref.getString("id", ""), postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismissDialog(progressDialog);
                try {
                    JSONObject root = new JSONObject(String.valueOf(response));
                    Intent intent = new Intent(mContext, Home.class);
                    mContext.startActivity(intent);
                    mContext.finishAffinity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismissDialog(progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismissDialog(progressDialog);
                if (error instanceof NoConnectionError) {
                    dialog.displayDialog("Not Connected to Internet", mContext);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", mContext);
                } else {
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                }
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = mContext.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        MyRequestQueue.add(jsonObjectRequest);
    }

    public void getSignUpData() {
        Log.d("TAG", "getData: we are in getSignUpData");
        dialog.alertDialogLogin(progressDialog, "Signing Up....");
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("username", data.getUsername());
            postparams.put("password", data.getCpass());
            postparams.put("email", data.getEmail());
            if (!isDoc)
                postparams.put("department", Integer.parseInt("1"));
            else
                postparams.put("doctor", data.getSpecialistof());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getData: obj " + postparams);
        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                flag.setValue(true);
                Log.d("TAG", "onResponse: we are processing");
                Log.e("TAG", "onResponse: " + response);
                dialog.dismissDialog(progressDialog);
                try {
                    JSONObject root = new JSONObject(String.valueOf(response));
                    id = root.getString("id");
                    email = root.getString("email");
                    usernameResp = root.getString("username");
                    isDoctor = root.getBoolean("is_doctor");
                    saveToPreferences();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.displayDialog("Not Connected to Internet", mContext);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", mContext);
                } else {
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                    dialog.displayDialog("Username Already Taken", mContext);
                    flag.setValue(false);
                }
                dialog.dismissDialog(progressDialog);
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        MyRequestQueue.add(jsonObjectRequest);

    }

    private void saveToPreferences() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("tokenFile", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isDoc", isDoctor);
        editor.putString("doctor_id", id);
        editor.putString("id", id);
        editor.putString("username", usernameResp);
        editor.putString("Email", email);
        editor.putString("pass", data.getCpass());
        editor.apply();
    }

}
