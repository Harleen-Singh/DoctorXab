package com.example.doc_app_android.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.utils.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorListService {
    Context app;
    private MutableLiveData<ArrayList<DocData>> data;
    private MutableLiveData<ArrayList<DocData>> id_name_Pair;
    private SharedPreferences preferences;
    private boolean isDoc = false;
    private ArrayList<DocData> listOfNames;

    public MutableLiveData<ArrayList<DocData>> getDocListForShare(Context context) {
        this.app = context;
        preferences = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isDoc = preferences.getBoolean("isDoc", false);
        if (data == null) {
            id_name_Pair = new MutableLiveData<>();
            data = new MutableLiveData<>();
            loadData();

        }
        return data;
    }

    public MutableLiveData<ArrayList<DocData>> getDocList(Context context) {
        this.app = context;
        preferences = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isDoc = preferences.getBoolean("isDoc", false);
        if (data == null) {
            id_name_Pair = new MutableLiveData<>();
            data = new MutableLiveData<>();
            if (isDoc) {
                getPatientList(context);
            } else {
                loadData();
            }
        }
        return data;
    }

    public MutableLiveData<ArrayList<DocData>> getDocList(Context context, boolean fromDataLoaderFragment) {
        this.app = context;
        preferences = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isDoc = preferences.getBoolean("isDoc", false);
        if (data == null) {
            id_name_Pair = new MutableLiveData<>();
            data = new MutableLiveData<>();
            if (isDoc && fromDataLoaderFragment) {
                loadData();
            } else if (isDoc) {
                getPatientList(context);
            } else {
                loadData();
            }

        }
        return data;
    }

    public MutableLiveData<ArrayList<DocData>> getId_name_Pair(Context context) {
        this.app = context;
        if (id_name_Pair == null) {
            data = new MutableLiveData<>();
            id_name_Pair = new MutableLiveData<>();
            loadData();
        }
        return id_name_Pair;
    }

    private void loadData() {
        RequestQueue queue = Volley.newRequestQueue(app);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Globals.docList, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<DocData> tempData = new ArrayList<>();
                ArrayList<DocData> tempid_name_Pair = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        String docID, username, name, age, state, image, email, phone, department;
                        JSONObject obj = response.getJSONObject(i);
                        department = obj.getString("department");
                        JSONObject userObj = obj.getJSONObject("user");
                        docID = userObj.getString("id");
                        name = userObj.getString("name");
                        username = userObj.getString("username");
                        age = userObj.getString("age");
                        state = userObj.getString("state");
                        image = userObj.getString("image");
                        email = userObj.getString("email");
                        phone = userObj.getString("phone_number");
                        Log.e("TAG", "onResponse: " + phone);
                        tempData.add(new DocData(docID, username, name, age, state, image, email, phone, department));
                        tempid_name_Pair.add(new DocData(docID, name));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                id_name_Pair.setValue(tempid_name_Pair);
                data.setValue(tempData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void getPatientList(Context context) {

        final RequestQueue patientDetailsQueue = Volley.newRequestQueue(context);
        JsonObjectRequest getPatientListObject = new JsonObjectRequest(Request.Method.GET, Globals.patientList, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("DoctorPatientList", "OnResponse: " + response);
                listOfNames = new ArrayList<>();
                DocData docData;
                try {

                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        JSONObject user = object.getJSONObject("user");
                        int patient_Id = user.getInt("id");
                        String name = user.getString("name");
                        String image = user.getString("image");
                        docData = new DocData(name, image, String.valueOf(patient_Id));
                        listOfNames.add(docData);
                    }
                    data.setValue(listOfNames);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Individual", "Response from doctor list:\n" + listOfNames);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


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


}
