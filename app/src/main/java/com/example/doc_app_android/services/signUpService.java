package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.data_model.Register_data;
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
    public MutableLiveData<Boolean> flag = new MutableLiveData<>() ;
    public String url,url2;
    String id , usernameResp , email , name , age , gender , contact , state ;
    private boolean isDoctor;
    private com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();
    public signUpService(Register_data data, Activity activity , boolean isDoc , boolean flagCheck) {
        this.data = data;
        this.mContext = activity;
        this.isDoc = isDoc;
        if(!isDoc)
            url = Globals.docRegister;
        else
            url = Globals.patientRegister;
        progressDialog = new ProgressDialog(mContext, R.style.AlertDialog);

        url2 = Globals.addUserData;

        if(flagCheck)
            getSignUpData();
        else
            getContinueData();


    }

    private void getContinueData() {
        Log.d("TAG", "getData: we are in getContinueData");
        dialogs.alertDialogLogin(progressDialog,"Loading.....");
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("name",data.getName() );
            postparams.put("phone_number",data.getContact() );
            postparams.put("gender",data.getGender());
            postparams.put("age",data.getAge() );
            if(!isDoc)
                postparams.put("department",Integer.parseInt("1"));
//                postparams.put("department",Integer.parseInt(data.getSpecialistof()) );
            else
//            postparams.put("password", pass);
            Log.e("TAG", "getData: " + data.getName()  );
            Log.e("TAG", "getData: " + data.getAge()  );
            Log.e("TAG", "getData: " + data.getGender()  );
            Log.e("TAG", "getData: " + data.getContact()  );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getContinueData: obj " + postparams);
        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url2, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse: we are processing");
                Log.e("TAG", "onResponse: "+response );
                dialogs.dismissDialog(progressDialog);
                try{
                    JSONObject root = new JSONObject(String.valueOf(response));
                    name = root.getString("name");
                    age = root.getString("age");
                    gender = root.getString("gender");
                    contact = root.getString("phone_number");
                    state =  root.getString("state");
//                    saveToPreferences();

                    Intent intent = new Intent(mContext, Home.class);
                    mContext.startActivity(intent);
                    mContext.finish();
                }catch (Exception e){
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
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                }
                dialogs.dismissDialog(progressDialog);
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        MyRequestQueue.add(jsonObjectRequest);
    }

    public void getSignUpData(){
        Log.d("TAG", "getData: we are in getSignUpData");
        dialogs.alertDialogLogin(progressDialog,"Signing Up....");
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("username",data.getUsername() );
            postparams.put("password",data.getCpass() );
            postparams.put("email",data.getEmail() );

            if(!isDoc)
                postparams.put("department",Integer.parseInt("1"));
//                postparams.put("department",Integer.parseInt(data.getSpecialistof()) );
          else
                postparams.put("doctor",data.getSpecialistof() );
//            postparams.put("password", pass);

            Log.e("TAG", "getData: " + data.getUsername()  );

            Log.e("TAG", "getData: " + data.getCpass()  );
            Log.e("TAG", "getData: " + data.getEmail()  );



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
                Log.e("TAG", "onResponse: "+response );
                dialogs.dismissDialog(progressDialog);
                try{
                    JSONObject root = new JSONObject(String.valueOf(response));
                    id = root.getString("id");
                    email = root.getString("email");
                    usernameResp = root.getString("username");
                    isDoctor = root.getBoolean("is_doctor");
                    saveToPreferences();

//                    Intent intent = new Intent(mContext, Home.class);
//                    mContext.startActivity(intent);
//                    mContext.finish();
                }catch (Exception e){
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
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                    dialogs.displayDialog("Username Already Taken",mContext);
                    flag.setValue(false);
                }
                dialogs.dismissDialog(progressDialog);
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
        editor.putString("pass" , data.getCpass());

//        editor.putString("name", name);
//        editor.putString("age", age);
//        editor.putString("gender", gender);
//        editor.putString("phone_number", contact);
//        editor.putString("state" , state);

        editor.apply();
    }

}
