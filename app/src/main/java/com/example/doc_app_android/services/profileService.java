package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.view_model.ProfileViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class profileService {


    private int id;
    private String userName, email;
    boolean is_Doc, is_Patient;
    private int department;
    private Context context;
    private final com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();
    private ProfileData profileData;
    private MutableLiveData<ProfileData> mutableProfileData =  new MutableLiveData<>();
    private int problem, doctor;




    public LiveData<ProfileData> getProfileDetails(Application application, Context mContext){
        context = mContext;

        final SharedPreferences sp = application.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        boolean isDoc = sp.getBoolean("isDoc", false);
        String dorP_id = sp.getString("id", "");

        if(isDoc){
            //Log.d("Testing", "Url: " +Globals.profileDoctor + dorP_id )
            getData(Globals.profileDoctor + dorP_id, true);
        } else{
            dorP_id = "60";
            getData(Globals.profilePatient + dorP_id, false);
        }
        return mutableProfileData;
    }



    public void getData(String url, boolean isDoctor) {


        final RequestQueue putProfileInfoRequest = Volley.newRequestQueue(context);
        JsonObjectRequest makeProfileInfoRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Log.d("Testing", String.valueOf(response));
                    if(isDoctor) {
                        JSONObject user = response.getJSONObject("user");
                        id = user.getInt("id");
                        userName = user.getString("username");
                        email = user.getString("email");
                        is_Doc = user.getBoolean("is_doctor");
                        is_Patient = user.getBoolean("is_patient");

                        department = response.getInt("department");

                        profileData = new ProfileData(id, userName, email, is_Doc, is_Patient, department);
                    } else{

                        JSONObject user = response.getJSONObject("user");
                        id = user.getInt("id");
                        userName = user.getString("username");
                        email = user.getString("email");
                        is_Doc = user.getBoolean("is_doctor");
                        is_Patient = user.getBoolean("is_patient");

                        doctor = response.getInt("doctor");
                        problem = response.getInt("problem");
                        profileData = new ProfileData(id, userName, email, is_Doc, is_Patient, doctor, problem);

                    }
                    mutableProfileData.postValue(profileData);

                } catch (JSONException e) {
                    Log.d("Testing", "onResponse: Error Occured" + "\n" + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NoConnectionError) {
                    dialogs.displayDialog("Not Connected to Internet",context);
                }
                else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }
            }
        });

        putProfileInfoRequest.add(makeProfileInfoRequest);

        makeProfileInfoRequest.setRetryPolicy(new RetryPolicy() {
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
