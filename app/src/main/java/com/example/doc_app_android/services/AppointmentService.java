package com.example.doc_app_android.services;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.AppointmentData;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.utils.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AppointmentService {

    public static final String TAG = "AppointmentService";
    private MutableLiveData<ArrayList<AppointmentData>> data_model;
    private MutableLiveData<ArrayList<AppointmentData>> individualAppointmentDataModel;
    private Application app;
    private ArrayList<AppointmentData> appointmentData = new ArrayList<>();
    private ArrayList<AppointmentData> individualAppointment = new ArrayList<>();
    private SharedPreferences prefs;
    private boolean isDoc;
    private String required_id = "";
    private TreeMap<Integer, String> namesOfDocPat = new TreeMap<>();
    private String url;
    private ProgressBar progressBarFromAppointmentV2;
    private ProgressBar progressBarFromIndividualAppointment;
    private ProgressDialog progressDialog;
    private dialogs dialogs = new dialogs();


    public void cancelAppointmentFromDoctorOrPatient(String id, Application app, Context context) {
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        cancelAppointment(id, app, context);
    }


    public LiveData<ArrayList<AppointmentData>> getApmntData(Application app, ProgressBar progressBar, Context context) {
        this.app = app;
        this.progressBarFromAppointmentV2 = progressBar;
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);

        final SharedPreferences sp = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isDoc = sp.getBoolean("isDoc", false);

        if (data_model == null) {
//            progressBarFromAppointmentV2.setVisibility(View.VISIBLE);
            dialogs.alertDialogLogin(progressDialog, "Loading...");


            data_model = new MutableLiveData<>();
            if (!isDoc) {
                getDoctorList(false);
            } else {
                getPatientList(app.getApplicationContext());
            }

        }
        return data_model;
    }

    public LiveData<ArrayList<AppointmentData>> getIndividualAppointmentData(Application app, ProgressBar progressBar, Context context) {
        progressBarFromIndividualAppointment = progressBar;
        this.app = app;
        prefs = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        required_id = prefs.getString("patient_id", "");
        url = Globals.individualAppointment + required_id;
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        dialogs.alertDialogLogin(progressDialog, "Loading...");

//        progressBarFromIndividualAppointment.setVisibility(View.VISIBLE);
        individualAppointmentDataModel = new MutableLiveData<>();
        getDoctorList(true);

        return individualAppointmentDataModel;
    }

    private void loadData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Globals.appointment, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                try {
                    //Date today1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(String.valueOf(today));

                    for (int i = 0; i < response.length(); i++) {
                        String id, date, patient_id, doctor_id;
                        JSONObject obj = response.getJSONObject(i);
                        id = obj.getString("id");
                        date = obj.getString("date");
                        patient_id = obj.getString("patient");
                        doctor_id = obj.getString("doctor");

//                        Date comingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);
                        // namesOfDocPat.get(Integer.valueOf(doctor_id)))

                        if (!isDoc) {


                            appointmentData.add(new AppointmentData(id, date, patient_id, doctor_id, "Dr." + namesOfDocPat.get(Integer.valueOf(doctor_id))));
                            Log.e("Individual", "onResponse: " + "Appointment_Id:" + id +
                                    "\t" + "Date:" + date +
                                    "\t" + "doctor_id" + doctor_id +
                                    "\t" + "name: " + namesOfDocPat.get(Integer.valueOf(doctor_id)));
                        } else {
                            if (namesOfDocPat.get(Integer.valueOf(patient_id)) != null) {
                                appointmentData.add(new AppointmentData(id, date, patient_id, doctor_id, namesOfDocPat.get(Integer.valueOf(patient_id))));
                                Log.e("Individual", "onResponse: " + "Appointment_Id:" + id +
                                        "\t" + "Date:" + date +
                                        "\t" + "Patient_ID:" + patient_id +
                                        "\t" + "name: " + namesOfDocPat.get(Integer.valueOf(patient_id)));
                            }
                        }


//                        assert today1 != null;
//                        if (today1.after(comingDate)) {
//
//                        } else {
//                            appointmentData.add(new AppointmentData(id, date, patient_id, doctor_id, false));
//
//                        }


                    }
                    data_model.setValue(appointmentData);
//                    progressBarFromAppointmentV2.setVisibility(View.GONE);
                    dialogs.dismissDialog(progressDialog);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Log.e("Individual", "onResponse: " + appointmentData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(app, "Error Response : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                progressBarFromAppointmentV2.setVisibility(View.GONE);
                dialogs.dismissDialog(progressDialog);


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        requestQueue.add(request);
    }


    void loadParticularIndividualAppointmentData(String url) {


        Log.d("Individual", "Url: " + url);

        final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                try {
                    //Date today1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(String.valueOf(today));
                    individualAppointment.clear();

                    for (int i = 0; i < response.length(); i++) {
                        String id, date, patient_id, doctor_id;
                        JSONObject obj = response.getJSONObject(i);
                        id = obj.getString("id");
                        date = obj.getString("date");
                        patient_id = obj.getString("patient");
                        doctor_id = obj.getString("doctor");

//                        Date comingDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);


                        individualAppointment.add(new AppointmentData(id, date, patient_id, doctor_id, "Dr." + namesOfDocPat.get(Integer.valueOf(doctor_id))));
                        Log.e("Individual", "onResponse: " + "Appointment_Id:" + id +
                                "\t" + "Date:" + date +
                                "\t" + "doctor_id" + doctor_id +
                                "\t" + "name: " + namesOfDocPat.get(Integer.valueOf(doctor_id)));
//                        assert today1 != null;
//                        if (today1.after(comingDate)) {
//
//                        } else {
//                            appointmentData.add(new AppointmentData(id, date, patient_id, doctor_id, false));
//
//                        }


                    }
                    individualAppointmentDataModel.setValue(individualAppointment);
//                    progressBarFromIndividualAppointment.setVisibility(View.GONE);
                    dialogs.dismissDialog(progressDialog);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(app, "Error Response : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                progressBarFromIndividualAppointment.setVisibility(View.GONE);
                dialogs.dismissDialog(progressDialog);

            }
        });
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                SharedPreferences pref = app.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
//                Map<String, String> params = new HashMap<>();
//                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
//                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//                params.put("Authorization", auth);
//                return params;
//            }
//        };
        requestQueue.add(request);

    }


    private void getDoctorList(boolean fromIndividualPatient) {

        RequestQueue queue = Volley.newRequestQueue(app);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Globals.docList, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                TreeMap<Integer, String> listOfNames = new TreeMap<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String docID, username, name, age, state, image, email, phone, department;
                        JSONObject obj = response.getJSONObject(i);
                        department = obj.getString("department");
                        JSONObject userObj = obj.getJSONObject("user");
                        docID = userObj.getString("id");
                        name = userObj.getString("name");
                        listOfNames.put(Integer.valueOf(docID), name);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                namesOfDocPat = new TreeMap<>(listOfNames);
                if (!fromIndividualPatient) {
                    loadData();
                } else {
                    loadParticularIndividualAppointmentData(url);
                }
                Log.d("Individual", "Response from patient list:\n" + namesOfDocPat);
                Log.d("Individual", "Response from patient list:\n" + listOfNames);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogs.dismissDialog(progressDialog);

                if (!fromIndividualPatient) {
                    progressBarFromAppointmentV2.setVisibility(View.GONE);
                } else {
                    progressBarFromIndividualAppointment.setVisibility(View.GONE);
                }

                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void getPatientList(Context context) {
        ArrayList<ProfileData> data = new ArrayList<>();
        final RequestQueue patientDetailsQueue = Volley.newRequestQueue(context);
        JsonObjectRequest getPatientListObject = new JsonObjectRequest(Request.Method.GET, Globals.patientList, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DoctorPatientList", "OnResponse: " + response);
                TreeMap<Integer, String> listOfNames = new TreeMap<>();
                try {

                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        JSONObject user = object.getJSONObject("user");
                        int patient_Id = user.getInt("id");
                        String name = user.getString("name");
                        listOfNames.put(patient_Id, name);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                namesOfDocPat = new TreeMap<>(listOfNames);
                loadData();
                Log.d("Individual", "Response from doctor list:\n" + namesOfDocPat);
                Log.d("Individual", "Response from doctor list:\n" + listOfNames);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBarFromAppointmentV2.setVisibility(View.GONE);
                dialogs.dismissDialog(progressDialog);


                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());

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


    private void cancelAppointment(String id, Application app, Context context) {

        Log.d("AppointmentService", "Appointment with id" + id + "getting deleted");

        dialogs.alertDialogLogin(progressDialog, "Cancelling...");


        JSONObject details = null;

        try {
            details = new JSONObject();
            details.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RequestQueue cancelRequest = Volley.newRequestQueue(app.getApplicationContext());

        JsonObjectRequest cancel = new JsonObjectRequest(Request.Method.DELETE, Globals.cancelAppointment, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: Cancellation response " + response);
                Toast.makeText(app.getApplicationContext(), "Appointment Cancelled Successfully", Toast.LENGTH_SHORT).show();
                dialogs.dismissDialog(progressDialog);
//                dialogs.displayDialog("Successfully Cancelled",context, true);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: Cancellation error " + error);
                Log.d(TAG, "onErrorResponse: Cancellation status code " + error.networkResponse.statusCode);
                Log.d(TAG, "onErrorResponse: Cancellation error data " + Arrays.toString(error.networkResponse.data));
                Toast.makeText(app.getApplicationContext(), "Appointment Cancellation error with status code " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                dialogs.dismissDialog(progressDialog);
//                dialogs.displayDialog("Error Occurred",context, true);
                Toast.makeText(context, "Cancellation Failed", Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s", pref.getString("username", ""), pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                Log.d("Credentials", "Username: " + pref.getString("username", "") + "\n" + "password: " + pref.getString("pass", ""));
                return params;
            }
        };

        cancelRequest.add(cancel);

        cancel.setRetryPolicy(new RetryPolicy() {
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