package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.view_model.ProfileViewModel;
import com.google.android.material.badge.BadgeDrawable;

import org.json.JSONException;
import org.json.JSONObject;

public class profileService {


    private int id;
    private String userName, email, name, image, phoneNumber;
    boolean is_Doc, is_Patient;
    private int department;
    private Context context;
    //private ProgressDialog progressDialog;
    private final com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();
    private ProfileData profileData;
    private MutableLiveData<ProfileData> mutableProfileData;
    private int problem, doctor;


    public LiveData<ProfileData> getProfileDetails(Application application, Context mContext) {

        context = mContext;


        final SharedPreferences sp = application.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        boolean isDoc = sp.getBoolean("isDoc", false);
        String dorP_id = sp.getString("doctor_id", "");
        String patient_id = sp.getString("id", "");
        boolean hasProfileUpdated = sp.getBoolean("hasProfileUpdate", false);

        if (mutableProfileData == null || hasProfileUpdated) {
            mutableProfileData = new MutableLiveData<>();
            if (isDoc) {
                //Log.d("Testing", "Url: " +Globals.profileDoctor + dorP_id )
                getData(Globals.profileDoctor + dorP_id, true);

            } else {
                dorP_id = "60";
                getData(Globals.profilePatient + patient_id, false);

            }
        }


        return mutableProfileData;
    }


    public void getData(String url, boolean isDoctor) {


        final RequestQueue putProfileInfoRequest = Volley.newRequestQueue(context);
        JsonObjectRequest makeProfileInfoRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Log.d("Testing", "Output From Profile Service: " + response);
                    if (isDoctor) {
                        JSONObject user = response.getJSONObject("user");
                        id = user.getInt("id");
                        userName = user.getString("username");
                        email = user.getString("email");
                        is_Doc = user.getBoolean("is_doctor");
                        is_Patient = user.getBoolean("is_patient");
                        name = user.getString("name");
                        image = user.getString("image");
                        phoneNumber = user.getString("phone_number");

                        department = response.getInt("department");

                        profileData = new ProfileData(id, userName, email, is_Doc, is_Patient, department, image, name, phoneNumber);
                    } else {

                        JSONObject user = response.getJSONObject("user");
                        id = user.getInt("id");
                        userName = user.getString("username");
                        email = user.getString("email");
                        is_Doc = user.getBoolean("is_doctor");
                        is_Patient = user.getBoolean("is_patient");

                        doctor = response.getInt("doctor");
                        problem = response.getInt("problem");
                        name = user.getString("name");
                        image = user.getString("image");
                        phoneNumber = user.getString("phone_number");
                        profileData = new ProfileData(id, userName, email, is_Doc, is_Patient, doctor, problem, image, name, phoneNumber);

                    }
                    mutableProfileData.setValue(profileData);
                    //dialogs.dismissDialog(progressDialog);

                } catch (JSONException e) {
                    Log.d("Testing", "onResponse: Error Occured" + "\n" + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialogs.displayDialog("Not Connected to Internet", context);
                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }

                //dialogs.dismissDialog(progressDialog);
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
