package com.example.doc_app_android.services;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.utils.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class notiService {
    private MutableLiveData<ArrayList<NotiData>> data_model;
    private MutableLiveData<String> unread_notifications;
    private MutableLiveData<NotiData> data;
    private NotiData notiData;
    private Application app;
    private SharedPreferences prefs;
    private String nextURL, counter;
    private ProgressDialog progressDialog;
    private dialogs dialog = new dialogs();


    public LiveData<String> getCount(Application app) {
        prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        this.app = app;
        unread_notifications = new MutableLiveData<>();
        data_model = new MutableLiveData<>();
        loadData();
        return unread_notifications;
    }

    public LiveData<ArrayList<NotiData>> getNotiData(Application app) {
        prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        this.app = app;
        unread_notifications = new MutableLiveData<>();
        data_model = new MutableLiveData<>();
        loadData();
        return data_model;
    }

    public void loadData() {
        ArrayList<NotiData> notiDataArrayList = new ArrayList<>();
        String url = Globals.notifications;

        prefs = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        final RequestQueue requestQueue = Volley.newRequestQueue(app.getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("NOTIFICATIONS", "DATA FROM Notifications:\n" + response);
                try {
                    int count = 0;
                    for (int i = 0; i < response.length(); i++) {
                        String id, data, sender, receiver, status, icon, time, date, time1;
                        JSONObject singleObject = response.getJSONObject(i);
                        id = singleObject.getString("id");
                        data = singleObject.getString("data");
                        Log.d("NOTIFICATIONS", "DATA FROM Notifications: Length of data: " + data.length());
                        Log.d("NOTIFICATIONS", "DATA FROM Notifications: Date: " + data.substring(data.length() - 10));
                        Log.d("NOTIFICATIONS", "DATA FROM Notifications: Time: " + data.substring(data.length() - 19, data.length() - 14));
                        sender = singleObject.getString("sender");
                        receiver = singleObject.getString("reciever");
                        status = singleObject.getString("status");
                        time = singleObject.getString("time");
                        date = time.substring(0, 19);
                        time1 = time.substring(11, 19);

                        if (status.equals("0")) {
                            count = count + 1;
                        }
                        icon = singleObject.getString("icon");
                        notiDataArrayList.add(new NotiData(id, data, sender, receiver, icon, status, time, date));
                    }
                    data_model.setValue(notiDataArrayList);
                    unread_notifications.setValue(String.valueOf(count));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("notiData", "onErrorResponse: " + error.getLocalizedMessage());
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
        requestQueue.add(jsonArrayRequest);
    }


    public void acceptReq(NotiData notificationData, Context context) {
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        dialog.alertDialogLogin(progressDialog, "Accepting...");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject param = new JSONObject();
        int length = notificationData.getData().length();
        try {
            String date = notificationData.getData().substring(length - 10);
            String time = notificationData.getData().substring(length - 19, length - 14) + ":00";

//            DateFormat formatedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//            Date tempDate = formatedDate.parse(date);
//            DateFormat reqFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
//
//            String reqDate =  revqFormat.format(tempDate);

            Log.e("TAG", "acceptReq: new Date " + date);
            param.put("date", date);
            param.put("time", time);
            param.put("patient", notificationData.getSender());
            Log.d("NOTIFICATIONS", "DATA FROM Accept Request: Patient ID: " + notificationData.getSender());
            Log.d("NOTIFICATIONS", "DATA FROM Accept Request: Date: " + date);
            Log.d("NOTIFICATIONS", "DATA FROM Accept Request: Time: " + time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        param.put("date",);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.newNotifications, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Accepted Successfully", Toast.LENGTH_SHORT).show();
                changeStatus(notificationData.getId(), context);
                dialog.dismissDialog(progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.displayDialog(error + " !", context, true);
                dialog.dismissDialog(progressDialog);

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
        requestQueue.add(jsonObjectRequest);
    }

    public void rejectRequest(NotiData notificationData, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject param = new JSONObject();
        try {
            param.put("patient", notificationData.getSender());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.denyAppointment, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.displayDialog("Rejected Successfully", context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.displayDialog(error.getLocalizedMessage() + " !", context);
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
        requestQueue.add(jsonObjectRequest);
    }


    public void changeStatus(String id, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject param = new JSONObject();

        try {
            param.put("status", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        param.put("date",);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Globals.notificationStatus + id, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadData(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.displayDialog(error + " !", context);
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
        requestQueue.add(jsonObjectRequest);
    }

    public void loadData(Context context) {
        ArrayList<NotiData> notiDataArrayList = new ArrayList<>();
        String url = Globals.notifications;

        prefs = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        final RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("NOTIFICATIONS", "DATA FROM Notifications:\n" + response);
                try {
                    int count = 0;
                    for (int i = 0; i < response.length(); i++) {
                        String id, data, sender, receiver, status, icon, time, date, time1;
                        JSONObject singleObject = response.getJSONObject(i);
                        id = singleObject.getString("id");
                        data = singleObject.getString("data");
                        Log.d("NOTIFICATIONS", "DATA FROM Notifications: Length of data: " + data.length());
                        Log.d("NOTIFICATIONS", "DATA FROM Notifications: Date: " + data.substring(data.length() - 10));
                        Log.d("NOTIFICATIONS", "DATA FROM Notifications: Time: " + data.substring(data.length() - 19, data.length() - 14));
                        sender = singleObject.getString("sender");
                        receiver = singleObject.getString("reciever");
                        status = singleObject.getString("status");
                        time = singleObject.getString("time");
                        date = time.substring(0, 19);
                        time1 = time.substring(11, 19);

                        if (status.equals("0")) {
                            count = count + 1;
                        }
                        icon = singleObject.getString("icon");
                        notiDataArrayList.add(new NotiData(id, data, sender, receiver, icon, status, time, date));
                    }
                    data_model = new MutableLiveData<>();
                    data_model.postValue(notiDataArrayList);
                    unread_notifications = new MutableLiveData<>();
                    unread_notifications.postValue(String.valueOf(count));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("notiData", "onErrorResponse: " + error.getLocalizedMessage());
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
        requestQueue.add(jsonArrayRequest);
    }
}
