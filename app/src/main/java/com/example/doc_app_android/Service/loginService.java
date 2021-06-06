package com.example.doc_app_android.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.Home;

import org.json.JSONException;
import org.json.JSONObject;

public class loginService {
    private boolean isDoc , isPatient ;
    private String userID, email , username;

    private Context mContext;
    private String userName , pass;
    public loginService(String username , String Pass , Context context) {
        this.userName = username;
        this.pass = Pass;
        this.mContext = context;
        getData();
    }

    public void getData(){

        JSONObject obj  = new JSONObject();
        try {
            obj.put("username" , userName);
            obj.put("password" , pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue MyRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST ,  Globals.loginURL ,obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject rootObject = new JSONObject(String.valueOf(response));
                    userID = rootObject.getString("id");
                    userName = rootObject.getString("username");
                    email = rootObject.getString("email");
                    isPatient = rootObject.getBoolean("is_patient");
                    isDoc = rootObject.getBoolean("is_doctor");

                    saveToPreferences();  //saved to sharedPreferences

                    Intent i;
                    if(isDoc){
                        i = new Intent(mContext , Home.class);
                        mContext.startActivity(i);
                        Toast.makeText(mContext, "Hello Doc " + userName, Toast.LENGTH_SHORT).show();
                    }else{
                        i = new Intent(mContext , Home.class);
                        mContext.startActivity(i);
                        Toast.makeText(mContext, "Hello patient " + userName, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    private void saveToPreferences(){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("tokenFile", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isDoc", isDoc);
        editor.putBoolean("isPatient", isPatient);
        editor.putString("id", userID);
        editor.putString("username", userName);
        editor.putString("Email", email);
        editor.apply();
    }


}
