package com.example.doc_app_android.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Data_model.Login_data;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.Home;

import org.json.JSONException;
import org.json.JSONObject;

public class loginService {
    private boolean isDoc, isPatient;
    private String userID, email, username;

    private Context mContext;
    private String userName, pass;

    public loginService(Login_data data, Activity context) {
        this.userName = data.getloginUsername();
        this.pass = data.getPassword();
        this.mContext = context;
        getData();
    }

    public void getData() {
        Log.d("TAG", "getData: we are in getData");

        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("username", userName);
            postparams.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getData: obj " + postparams);
        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.loginURL, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("TAG", "onResponse: we are processing");
                    JSONObject rootObject = new JSONObject(String.valueOf(response));
                    userID = rootObject.getString("id");
                    userName = rootObject.getString("username");
                    email = rootObject.getString("email");
                    isPatient = rootObject.getBoolean("is_patient");
                    isDoc = rootObject.getBoolean("is_doctor");

                    saveToPreferences();  //saved to sharedPreferences

                    Intent i;
                    if (isDoc) {
                        i = new Intent(mContext, Home.class);
                        mContext.startActivity(i);
                        Toast.makeText(mContext, "Hello Doc " + userName, Toast.LENGTH_SHORT).show();
                    } else {
                        i = new Intent(mContext, Home.class);
                        mContext.startActivity(i);
                        Toast.makeText(mContext, "Hello patient " + userName, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("TAG", "onResponse: Sorry failed");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        MyRequestQueue.add(jsonObjectRequest);

    }

    private void saveToPreferences() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("tokenFile", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isDoc", isDoc);
        editor.putBoolean("isPatient", isPatient);
        editor.putString("id", userID);
        editor.putString("username", userName);
        editor.putString("Email", email);
        editor.apply();
    }


}
