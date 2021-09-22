package com.example.doc_app_android.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.data_model.AppointmentData;
import com.example.doc_app_android.utils.Globals;
import com.example.doc_app_android.data_model.DocData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class getAppointmentService {

    private androidx.appcompat.app.AlertDialog.Builder builder;
    private androidx.appcompat.app.AlertDialog alertDialog;

    public void getAppointment(DocData docData, String selectedDate, Context context, ProgressDialog dialog1, String selectedTime){

        SharedPreferences pref = context.getSharedPreferences("tokenFile",Context.MODE_PRIVATE);
        JSONObject obj = new JSONObject();
        try {
            obj.put("doctor",docData.getDocID());
            obj.put("time",selectedTime);
            obj.put("date",selectedDate);

            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            dialogs dialog = new dialogs();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.askAppointment, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dialog1.dismiss();
                    Toast.makeText(context, "Request Sent Successfully", Toast.LENGTH_SHORT).show();
//                    dialog.displayDialog("Request Sent Successfully", context, true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog1.dismiss();
//                    dialog.displayDialog("Error in Sending Request ", context, true);
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void shareReport(Context context, ProgressDialog dialog1, DocData docData) {
        SharedPreferences pref = context.getSharedPreferences("tokenFile",Context.MODE_PRIVATE);
        JSONObject obj = new JSONObject();
        try {
            obj.put("doctor",Integer.valueOf(docData.getDocID()));

            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            dialogs dialog = new dialogs();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.shareReport, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    dialog1.dismiss();
//                    dialog.displayDialog("Report Shared Successfully", context, true);
                    Toast.makeText(context, "Report Shared Successfully", Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", "onErrorResponse: "+ error );
                    dialog1.dismiss();
//                    dialog.displayDialog("Error in Sending Report ", context, true);
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void allotAppointment(DocData docData, String selectedDate, Context context, ProgressDialog dialog1, String selectedTime){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        dialogs dialogs = new dialogs();

        JSONObject param = new JSONObject();

        try {
            param.put("date",selectedDate);
            param.put("patient",docData.getPatientId());
            param.put("time", selectedTime);

            Log.d("Report Service", "Appointment from doctor side: \n" +
                    "Date: " + selectedDate + "\n" +
                    "Patient ID: " + docData.getPatientId() + "\n" +
                    "Time: " + selectedTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        param.put("date",);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.newNotifications, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog1.dismiss();
                Toast.makeText(context, "Allotted Successfully", Toast.LENGTH_SHORT).show();
//                dialogs.displayDialog("Allotted Successfully",context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog1.dismiss();
//                dialogs.displayDialog(error + " !",context);
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                String creds = String.format("%s:%s",pref.getString("username", ""),pref.getString("pass", ""));
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


//    public final void displayDialog1(String str,Context context) {
//        builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialog);
//        builder.setMessage(str);
//        alertDialog = builder.create();
//        alertDialog.show();
//        alertDialog.getWindow().getWindowStyle();
//
//
//    }
//
//    public void hideDialog(){
//        alertDialog.dismiss();
//    }
}
