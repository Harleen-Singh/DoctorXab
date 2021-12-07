package com.example.doc_app_android.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.R;
import com.example.doc_app_android.actvities.Home;
import com.example.doc_app_android.data_model.ProblemsList;
import com.example.doc_app_android.data_model.RegistrationData;
import com.example.doc_app_android.utils.Globals;
import com.example.doc_app_android.utils.PreferencesName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationService {

    private dialogs dialog = new dialogs();
    private RegistrationData registrationData;
    private RegistrationData receivedRegistrationData;
    private ProgressDialog progressDialog;
    private String url;
    private Context context;
    private ConstraintLayout regContainer;
    private FrameLayout regContainer1;
    private boolean isDoctor;
    private boolean isDoctorRegistration = false;
    private String id, receivedUserName, name, age, state, gender, image, receivedEmail, address, phoneNumber, password;
    private boolean receivedIsDoctor, receivedIsPatient;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private MutableLiveData<ArrayList<ProblemsList>> problemListData;
    private MutableLiveData<ProblemsList> problemReturned;
    private ArrayList<ProblemsList> problemsListArray;
    private String returnedId;


    public LiveData<ArrayList<ProblemsList>> get_problem_list(Context context) {

        if (problemListData == null) {
            problemListData = new MutableLiveData<>();
            problem_list(context);
        }
        return problemListData;
    }

//    public LiveData<ProblemsList> returnedProblem(String problem, Context context) {
//
//        if (problemReturned == null) {
//            problemReturned = new MutableLiveData<>();
//            addProblem(problem, context);
//        }
//        return problemReturned;
//    }


    public void registerUser(Context context, RegistrationData registrationData, ConstraintLayout regContainer, FrameLayout regContainer1, boolean isDoctor, boolean fromDataLoaderFragment) {
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        this.context = context;
        this.registrationData = registrationData;
        this.regContainer = regContainer;
        this.regContainer1 = regContainer1;
        this.isDoctor = isDoctor;

        if (isDoctor) {
            url = Globals.docRegister;
        } else {
            url = Globals.patientRegister;
        }
        registerUser(fromDataLoaderFragment);

    }


    void registerUser(boolean fromDataLoaderFragment) {
        Log.d("TAG", "getData: we are in getSignUpData");
        dialog.alertDialogLogin(progressDialog, "Signing Up....");
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("username", registrationData.getUserName());
            postparams.put("password", registrationData.getPassword());
            postparams.put("email", registrationData.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "getData: obj " + postparams);
        Log.d("TESTING", "Registering with url: " + url);
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest registrationRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse: we are processing");
                Log.e("TAG", "onResponse: " + response);

                dialog.dismissDialog(progressDialog);
                try {

                    id = response.getString("id");
                    receivedUserName = response.getString("username");
                    name = response.getString("name");
                    age = response.getString("age");
                    state = response.getString("state");
                    gender = response.getString("gender");
                    image = response.getString("image");
                    receivedEmail = response.getString("email");
                    address = response.getString("address");
                    phoneNumber = response.getString("phone_number");
                    receivedIsDoctor = response.getBoolean("is_doctor");
                    receivedIsPatient = response.getBoolean("is_patient");
                    if (!fromDataLoaderFragment) {
                        saveToPreferences(context);
                    }

                    receivedRegistrationData = new RegistrationData(id, receivedUserName, name, age, state, gender, image, receivedEmail, address, phoneNumber, receivedIsDoctor, receivedIsPatient);

                    preferences = context.getSharedPreferences(PreferencesName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString(PreferencesName.RECEIVED_REGISTRATION_ID, id);
                    editor.apply();


                    regContainer.setVisibility(View.GONE);
                    regContainer1.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.displayDialog("Not Connected to Internet", context);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", context);
                } else {
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                    dialog.displayDialog("Username Already Taken", context);
                }
                dialog.dismissDialog(progressDialog);
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        requestQueue.add(registrationRequest);
    }

    public void addPatientToDataBase(Context context, RegistrationData registrationData, String userId, String doctorId, Activity activity, boolean fromDataLoaderFragment, int problemId) {
        progressDialog = new ProgressDialog(context.getApplicationContext(), R.style.AlertDialog);
        Log.d("TESTING", "AddingPatient with url: " + Globals.addSpecialistList);


        dialog.alertDialogLogin(progressDialog, "Loading.....");
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("user", Integer.parseInt(userId));
            postparams.put("doctor", Integer.parseInt(doctorId));
            postparams.put("problem", problemId);
            Log.d("TESTING", "AddingPatient with userid: " + userId + " doctorId: " + doctorId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.addSpecialistList, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    sendRegisteredUserInformation(context, registrationData, userId, activity, isDoctorRegistration, fromDataLoaderFragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismissDialog(progressDialog);
                if (error instanceof NoConnectionError) {
                    dialog.displayDialog("Not Connected to Internet", context);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", context);
                } else {
                    Log.d("", "error.networkRespose.toString()-->>>" + error.networkResponse.toString());
                    dialog.displayDialog("Username Already Taken", context);
                }
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void sendRegisteredUserInformation(Context context, RegistrationData registrationData, String userId, Activity activity, boolean isDoctorRegistration, boolean fromDataLoaderFragment) {
        Log.d("TAG", "getData: we are in getContinueData");
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        if (isDoctorRegistration) {
            dialog.alertDialogLogin(progressDialog, "Loading.....");
        }

        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("name", registrationData.getSendingName());
            postparams.put("phone_number", registrationData.getSendingContact());
            postparams.put("gender", registrationData.getSendingGender());
            postparams.put("age", registrationData.getSendingAge());
            postparams.put("gender", registrationData.getSendingGender());
            postparams.put("address", registrationData.getSendingAddress());
//            if (!isDoc)
//                postparams.put("department", Integer.parseInt("1"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue userDataRequestQueue = Volley.newRequestQueue(context);
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PreferencesName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        Log.e("TAG", "getContinueData: " + "password in prefs " + pref.getString("pass", "") + " username " + pref.getString("username", ""));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Globals.addUserData + userId, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismissDialog(progressDialog);

                Log.d("TESTING", " SendingRegisteredUserInformation with url: " + Globals.addUserData + userId);

                try {
                    JSONObject root = new JSONObject(String.valueOf(response));
                    id = response.getString("id");
                    receivedUserName = response.getString("username");
                    name = response.getString("name");
                    age = response.getString("age");
                    state = response.getString("state");
                    gender = response.getString("gender");
                    image = response.getString("image");
                    receivedEmail = response.getString("email");
                    address = response.getString("address");
                    phoneNumber = response.getString("phone_number");

                    Intent intent = new Intent(context, Home.class);
                    context.startActivity(intent);
                    activity.finishAffinity();


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
                    dialog.displayDialog("Not Connected to Internet", context);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", context);
                } else {
                    Log.d("", "error.networkResponse.toString()-->>>" + error.networkResponse.toString());
                }
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
//                Map<String, String> params = new HashMap<>();
//                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
//                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//                params.put("Authorization", auth);
//                return params;
//            }
        };
        userDataRequestQueue.add(jsonObjectRequest);
    }


    private void saveToPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PreferencesName.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("isDoc", isDoctor);
        editor.putString("doctor_id", id);
        editor.putString("id", id);
        editor.putString("username", receivedUserName);
        editor.putString("Email", receivedEmail);
        editor.putString("pass", registrationData.getPassword());
        editor.apply();
    }

    public void addProblem(String problem, Context context, RegistrationData registrationData, String userId, String doctorId, Activity activity, boolean fromDataLoaderFragment, int problem_id) {
        JSONObject postparams = null;
        try {
            postparams = new JSONObject();
            postparams.put("problem", problem);
            postparams.put("Department", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue addProblemRequestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.addProblem, postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String problem = response.getString("problem");
                    String department = response.getString("Department");
                    returnedId = response.getString("id");
                    Log.d("PROBLEM", "Returned ID: " + returnedId);

                    addPatientToDataBase(context, registrationData, userId, doctorId, activity, fromDataLoaderFragment, Integer.parseInt(returnedId));

//                    ProblemsList returnedProb = new ProblemsList(problem, id, department);
//                    problemReturned.setValue(returnedProb);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("AddProblem", "onResponse: " + response);
                Log.d("AddProblem", "Successfully Added");

//

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    dialog.displayDialog("Not Connected to Internet", context);
                } else if (error instanceof ClientError) {
                    dialog.displayDialog("Invalid Credentials!", context);
                } else {
                    Log.d("", "error.networkResponse.toString()-->>>" + error.networkResponse.toString());
                }
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        addProblemRequestQueue.add(jsonObjectRequest);

    }


    public void problem_list(Context context) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        problemsListArray = new ArrayList<>();

        JsonArrayRequest problemsList = new JsonArrayRequest(Request.Method.GET, Globals.docFilter, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String id, problem, department;
                        JSONObject obj = response.getJSONObject(i);
                        id = obj.getString("id");
                        problem = obj.getString("problem");
                        department = obj.getString("Department");
                        problemsListArray.add(new ProblemsList(problem, id, department));
                    }
                    Log.d("PROBLEM_LIST", "onResponse: " + response);
                    problemListData.setValue(problemsListArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(problemsList);
    }
}
