package com.example.doc_app_android.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
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
import com.example.doc_app_android.R;
import com.example.doc_app_android.utils.Globals;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WritePrescriptionService {

    public static final String TAG = "WritePrescription";
    private com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();
    private ProgressDialog progressDialog;

    public WritePrescriptionService() {
    }

    public void send_Medicine_List(ArrayList<String> medicineList, int patient_id, Context context, FragmentManager fragmentManager) {

        JSONObject prescription = null;
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        dialogs.alertDialogLogin(progressDialog, "Sending....");

        try {
            prescription = new JSONObject();
            prescription.put("patient", String.valueOf(patient_id));
            JSONObject details = new JSONObject();
            for (int i = 0; i < medicineList.size(); i++) {
                details.put(String.valueOf(i + 1), medicineList.get(i));
            }
            prescription.put("details", details);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue sendPrescriptionRequestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest sendPrescription = new JsonObjectRequest(Request.Method.POST, Globals.sendPrescription, prescription, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                Toast.makeText(context, "Sent Successfully", Toast.LENGTH_SHORT).show();
                fragmentManager.popBackStack();

                dialogs.dismissDialog(progressDialog);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ClientError) {
                    Toast.makeText(context, "Client Error Occurred", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
                dialogs.dismissDialog(progressDialog);
                Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                Log.d(TAG, "onErrorResponse: " + error.networkResponse);
                Log.d(TAG, "onErrorResponse: " + error);

                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

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

        sendPrescriptionRequestQueue.add(sendPrescription);
        sendPrescription.setRetryPolicy(new RetryPolicy() {
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
