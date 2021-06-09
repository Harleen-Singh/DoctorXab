package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.doc_app_android.Data_model.Register_data;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.Home;
import com.example.doc_app_android.R;

import org.json.JSONException;
import org.json.JSONObject;

public class signUpService {
    private Register_data data;
    private Activity mContext;
    private ProgressDialog progressDialog;
    private boolean isDoc;
    public String url;
    private com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();
    public signUpService(Register_data data, Activity activity , boolean isDoc) {
        this.data = data;
        this.mContext = activity;
        this.isDoc = isDoc;
        if(!isDoc)
            url = Globals.docRegister;
        else
            url = Globals.patientRegister;
        progressDialog = new ProgressDialog(mContext, R.style.AlertDialog);
        getData();
    }

    public void getData(){
        Log.d("TAG", "getData: we are in getData");
        dialogs.alertDialogLogin(progressDialog,"Signing Up....");
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("username",data.getUsername() );
            postparams.put("password",data.getCpass() );
            postparams.put("email",data.getEmail() );
            postparams.put("contact",data.getContact() );
            if(!isDoc)
                postparams.put("department",data.getSpecialistof() );
          else
                postparams.put("doctor",data.getSpecialistof() );
//            postparams.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getData: obj " + postparams);
        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("TAG", "onResponse: we are processing");
                    JSONObject rootObject = new JSONObject(String.valueOf(response));

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

}
