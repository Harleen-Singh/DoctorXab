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
import com.example.doc_app_android.utils.Globals;
import com.example.doc_app_android.data_model.ProfileData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorPatientListService {

    private int patient_Id, doctor, count, problem, age;
    private String username, email, name, image, phoneNumber, state;
    private boolean isDoc, isPatient;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

//    private  results;
//    private  object;
//    private  user;

    private MutableLiveData<ArrayList<ProfileData>> mutableLiveElbowData;
    private MutableLiveData<ArrayList<ProfileData>> mutableLiveWristData;
    private MutableLiveData<ArrayList<ProfileData>> mutableLiveKneeData;
    private MutableLiveData<ArrayList<ProfileData>> mutableLiveHipData;
    private MutableLiveData<ArrayList<ProfileData>> mutableLiveData;


    public LiveData<ArrayList<ProfileData>> getPatientListForDoctorHome(String problemId, Context context) {
        preferences = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE );
//        editor = preferences.edit();
//        editor.putString("body_component_id", problemId);
//        editor.apply();

        if (problemId.equals("1")) {
            if (mutableLiveElbowData == null) {
                mutableLiveElbowData = new MutableLiveData<>();
                mutableLiveData = mutableLiveElbowData;
                getPatientList(Globals.doctorHomeScreenPatientList + problemId, context, problemId);
            } else {
                mutableLiveData = mutableLiveElbowData;
            }
        } else if (problemId.equals("2")) {
            if (mutableLiveWristData == null) {
                mutableLiveWristData = new MutableLiveData<>();
                mutableLiveData = mutableLiveWristData;
                getPatientList(Globals.doctorHomeScreenPatientList + problemId, context, problemId);
            } else {
                mutableLiveData = mutableLiveWristData;
            }
        } else if (problemId.equals("3")) {
            if (mutableLiveKneeData == null) {
                mutableLiveKneeData = new MutableLiveData<>();
                mutableLiveData = mutableLiveKneeData;
                getPatientList(Globals.doctorHomeScreenPatientList + problemId, context, problemId);
            } else {
                mutableLiveData = mutableLiveKneeData;
            }
        } else if (problemId.equals("4")) {
            if (mutableLiveHipData == null) {
                mutableLiveHipData = new MutableLiveData<>();
                mutableLiveData = mutableLiveHipData;
                getPatientList(Globals.doctorHomeScreenPatientList + problemId, context, problemId);
            } else {
                mutableLiveData = mutableLiveHipData;
            }
        }

        return mutableLiveData;
    }

    private void getPatientList(String url, Context context, String problem_Id) {
        ArrayList<ProfileData> data = new ArrayList<>();
        final RequestQueue patientDetailsQueue = Volley.newRequestQueue(context);
        JsonObjectRequest getPatientListObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DoctorPatientList", "OnResponse: " + response);
                try {

                    count = response.getInt("count");
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        JSONObject user = object.getJSONObject("user");
                         patient_Id = user.getInt("id");
                        name = user.getString("name");
                        image = user.getString("image");
                        phoneNumber = user.getString("phone_number");
                        username = user.getString("username");
                        email = user.getString("email");
                        age = user.getInt("age");
                        state = user.getString("state");
                        isDoc = user.getBoolean("is_doctor");
                        isPatient = user.getBoolean("is_patient");
                        doctor = object.getInt("doctor");
                        problem = object.getInt("problem");
                        //int id, int count, int doctor, int problem, String userName, String email, String name, String phoneNumber, String image, boolean isDoc, boolean isPatient
                        ProfileData profileData = new ProfileData(patient_Id, count, doctor, problem, username, email, name, phoneNumber, image, isDoc, isPatient, age, state);
                        data.add(profileData);

//                        preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
//                        editor = preferences.edit();
//                        editor.putString("id", pat)

                    }

                    if (problem_Id.equals("1")) {
                        mutableLiveElbowData.setValue(data);
                    } else if (problem_Id.equals("2")) {
                        mutableLiveWristData.setValue(data);
                    } else if (problem_Id.equals("3")) {
                        mutableLiveKneeData.setValue(data);
                    } else if (problem_Id.equals("4")) {
                        mutableLiveHipData.setValue(data);
                    }


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
                    Log.d("", "ServiceError: " + error);

                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();

                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
//
                Log.d("Credentials", "Username: " + pref.getString("username", "") + "\n" + "password: " + pref.getString("pass", ""));
//
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
