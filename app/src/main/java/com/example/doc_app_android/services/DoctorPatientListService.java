package com.example.doc_app_android.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.ProfileData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorPatientListService {

    private int id, doctor, count, problem;
    private String username, email, name, image, phoneNumber;
    private boolean isDoc, isPatient;
    private JSONArray results;
    private JSONObject object;
    private JSONObject user;
    private ArrayList<ProfileData> data =  new ArrayList<>();
    private ProfileData profileData;
    private MutableLiveData<ArrayList<ProfileData>> mutableLiveDatadata = new MutableLiveData<>();



    public LiveData<ArrayList<ProfileData>> getPatientListForDoctorHome(String problemId, Context context){
        if(mutableLiveDatadata == null){
            mutableLiveDatadata = new MutableLiveData<>();
        }
        getPatientList(Globals.doctorHomeScreenPatientList + problemId, context);
        return mutableLiveDatadata;
    }

    private void getPatientList(String url, Context context) {

        final RequestQueue patientDetailsQueue = Volley.newRequestQueue(context);
        JsonObjectRequest getPatientListObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DoctorPatientList", "OnResponse: " + response);
                try {
                    count = response.getInt("count");
                    results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        object = results.getJSONObject(i);
                        user = object.getJSONObject("user");
                        id = user.getInt("id");
                        name = user.getString("name");
                        image = user.getString("image");
                        phoneNumber = user.getString("phone_number");
                        username = user.getString("username");
                        email = user.getString("email");
                        isDoc = user.getBoolean("is_doctor");
                        isPatient = user.getBoolean("is_patient");
                        doctor = object.getInt("doctor");
                        problem = object.getInt("problem");
                        //int id, int count, int doctor, int problem, String userName, String email, String name, String phoneNumber, String image, boolean isDoc, boolean isPatient
                        profileData = new ProfileData(id, count, doctor, problem, username, email, name, phoneNumber, image, isDoc, isPatient);
                        data.add(profileData);
                    }

                    mutableLiveDatadata.setValue(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "ServiceError: " + error, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();

                String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
//                params.put("Username", pref.getString("username", ""));
                Log.d("Credentials", "Username: " + pref.getString("username", "") + "\n" + "password: " + pref.getString("pass", ""));
//                params.put("Password", pref.getString("pass", ""));
                return params;
            }
        };

        patientDetailsQueue.add(getPatientListObject);

        getPatientListObject.setRetryPolicy(new RetryPolicy() {
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
