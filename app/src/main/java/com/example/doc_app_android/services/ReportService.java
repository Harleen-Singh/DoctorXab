package com.example.doc_app_android.services;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Adapter.ReportData;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportService {

    private int id, patientId;
    private String date, data, patient;
    private ReportData reportData1;
    private MutableLiveData<ReportData> reportDataMutableLiveData;
    private SharedPreferences prefs;
    private String url;


    public LiveData<ReportData> getReportData(Application application) {

        prefs = application.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);

        url = Globals.report + prefs.getString("patient_id", "-1");
        Log.d("Testing", "Url for Patient history from doctor patient list: " + url);

        if (reportDataMutableLiveData == null) {
            reportDataMutableLiveData = new MutableLiveData<>();

            getReport(application, url);
        }
        return reportDataMutableLiveData;
    }

    public void updateReportData(Application application, ReportData reportData){
        prefs = application.getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);

        url = Globals.report + prefs.getString("patient_id", "-1");
        Log.d("Testing", "Url for Patient history from doctor patient list: " + url);
        editData(application, url, reportData);
    }


    public void editData(Context context, String url, ReportData reportData) {

        JSONObject writteData = null;
        try {
            writteData = new JSONObject();
            writteData.put("date", reportData.getDate());
            writteData.put("data", reportData.getData());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RequestQueue updateReportRequestQueue = Volley.newRequestQueue(context);
        final JsonObjectRequest updateReportRequest = new JsonObjectRequest(Request.Method.PUT, url, writteData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Testing", "Updated Report: "+ response);
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                try {
                    String date = response.getString("date");
                    String data = response.getString("data");
                    int id = response.getInt("id");
                    int patientId = response.getInt("patient");
                    reportData1 = new ReportData(id,patientId,date,data);

                    reportDataMutableLiveData.setValue(reportData1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }
            }
        });
        updateReportRequestQueue.add(updateReportRequest);

        updateReportRequest.setRetryPolicy(new RetryPolicy() {
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

    public void getReport(Context context, String url) {

        final RequestQueue getReportRequestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest getReportRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    id = response.getInt("id");
                    date = response.getString("date");
                    data = response.getString("data");
                    patientId = response.getInt("patient");
                    reportData1 = new ReportData(id, patientId, date, data);
                    reportDataMutableLiveData.setValue(reportData1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                    dialogs.displayDialog("Not Connected to Internet", context);
                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }
            }
        });

        getReportRequestQueue.add(getReportRequest);

        getReportRequest.setRetryPolicy(new RetryPolicy() {
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
