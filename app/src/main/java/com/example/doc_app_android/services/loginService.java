package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.data_model.Login_data;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.Home;
import com.example.doc_app_android.R;

import org.json.JSONException;
import org.json.JSONObject;

public class loginService {
    private boolean isDoc, isPatient;
    private String userID, email, username;
    private Activity mContext;
    private String userName, pass;
    private ProgressDialog progressDialog;
    private dialogs dialogs = new dialogs();

    public loginService(Login_data data, Activity context) {
        this.userName = data.getloginUsername();
        this.pass = data.getPassword();
        this.mContext = context;
        progressDialog = new ProgressDialog(mContext,R.style.AlertDialog);
        getData();
    }

    public void getData() {
        Log.d("TAG", "getData: we are in getData");
        dialogs.alertDialogLogin(progressDialog,"Signing In....");
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
                        dialogs.dismissDialog(progressDialog);
                        mContext.startActivity(i);
                        mContext.finish();
                        Toast.makeText(mContext, "Hello Doc " + userName, Toast.LENGTH_SHORT).show();
                    } else {
                        i = new Intent(mContext, Home.class);
                        dialogs.dismissDialog(progressDialog);
                        mContext.startActivity(i);
                        mContext.finish();
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
                if(error instanceof NoConnectionError) {
                    dialogs.displayDialog("Not Connected to Internet",mContext);
                }
                else if(error instanceof ClientError){
                    dialogs.displayDialog("Invalid Credentials!",mContext);
                }
                else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }
                dialogs.dismissDialog(progressDialog);
                    Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        MyRequestQueue.add(jsonObjectRequest);

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
    }

    private void saveToPreferences() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences("tokenFile", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isDoc", isDoc);
        editor.putString("id", userID);
        editor.putString("username", userName);
        editor.putString("Email", email);
        editor.putString("pass" , pass);
        editor.apply();
    }



}
