package com.example.doc_app_android.services;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.AppointmentData;
import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.utils.Globals;
import com.example.doc_app_android.databinding.LoadingDialogBinding;
import com.example.doc_app_android.volley.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReportService {

    private int id, patientId;
    private String date, data, patient;
    private ReportData reportData1;
    private ReportData reportDataPatient;
    private MutableLiveData<ReportData> reportDataMutableLiveData;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String url;
    private Dialog dialog1;
    private LoadingDialogBinding loadingDialogBinding;
    private Application application;
    private Context context;
    private int report_id;
    private dialogs dialogs = new dialogs();
    private ProgressDialog progressDialog;


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
        editData(application, url, reportData, Request.Method.PUT);
    }

    public void addAppointment(AppointmentData appointmentData, Context context){
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        allotAppointment(appointmentData, context);
    }


    public void editData(Context context, String url, ReportData reportData,  int method) {

        JSONObject writteData = null;
        try {
            writteData = new JSONObject();
            writteData.put("date", reportData.getDate());
            writteData.put("data", reportData.getData());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RequestQueue updateReportRequestQueue = Volley.newRequestQueue(context);
        final JsonObjectRequest updateReportRequest = new JsonObjectRequest(method, url, writteData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Testing", "Updated Report: "+ response);
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                try {
                    String date = response.getString("date");
                    String data = response.getString("data");
                    int id = response.getInt("id");
                    report_id = id;
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

    public void addPatientReport(Application mApplication, Context mContext, com.example.doc_app_android.data_model.ReportData reportData, ReportData reportData1)  {

        context = mContext;
        application = mApplication;

        loadingDialogBinding = LoadingDialogBinding.inflate(LayoutInflater.from(mContext));
        loadingDialogBinding.getRoot().setBackgroundResource(android.R.color.transparent);
        dialog1 = new Dialog(context);
        dialog1.setCancelable(false);
        dialog1.setContentView(loadingDialogBinding.getRoot());
        loadingDialogBinding.updateProgress.setVisibility(View.VISIBLE);
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addNewReport(context,reportData1,reportData);

    }
    private String encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
    }




    public void uploadImage(com.example.doc_app_android.data_model.ReportData reportData) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Globals.addNewXray,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Toast.makeText(context, "UploadedSuccessfully", Toast.LENGTH_SHORT).show();
                        Log.d("Testing", "Upload Image response" + response);
                        Log.d("Testing", "Upload Image response" + Arrays.toString(response.data));

                        Log.d("Testing", "Uploading Image Successful");
                        prefs = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                        editor = prefs.edit();
                        editor.putString("X-RAY-ID-Report","");
                        editor.putString("X-RAY-DATE-REPORT", "");
                        editor.putString("X-RAY-TIME-REPORT", "");
                        editor.putString("X-RAY-CATEGORY-REPORT", "");
                        editor.putString("X-RAY-BODYAREA-REPORT", "");
                        editor.putString("X-RAY-REPORT-REPORT", "");
                        editor.apply();
                        dialog1.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError", "" + error.getMessage());
                        dialog1.dismiss();
                        Log.d("Testing", "Uploading Image makes error");
                    }
                }) {


            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("pic_id", reportData.getXray_id());
                params.put("time", reportData.getTime());
                params.put("date", reportData.getDate());
                params.put("report", String.valueOf(report_id));
                params.put("patient",  String.valueOf(reportData.getPatientId()));
                params.put("category", reportData.getCategory());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws IOException {
                Map<String, DataPart> params = new HashMap<>();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), reportData.getUri());
                long imagename = System.currentTimeMillis();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
                params.put("image", new DataPart("xray" + imagename + ".jpg", bytesOfImage));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }



    public void addNewReport(Context context, ReportData reportData1, com.example.doc_app_android.data_model.ReportData reportData) {

        JSONObject writteData = null;
        try {
            writteData = new JSONObject();
            writteData.put("patient", String.valueOf(reportData.getPatientId()));
            writteData.put("date", reportData1.getDate());
            writteData.put("data", reportData1.getData());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RequestQueue updateReportRequestQueue = Volley.newRequestQueue(context);
        final JsonObjectRequest updateReportRequest = new JsonObjectRequest(Request.Method.POST, Globals.addNewReport, writteData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Testing", "Updated Report: "+ response);
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                try {
                    String date = response.getString("date");
                    String data = response.getString("data");
                    int id = response.getInt("id");
                    report_id = id;
                    uploadImage(reportData);
                    //editData(reportData);
                    int patientId = response.getInt("patient");
                    reportDataPatient = new ReportData(id,patientId,date,data);

                    reportDataMutableLiveData.setValue(reportDataPatient);
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




    public void allotAppointment(AppointmentData appointmentData , Context context){

        dialogs.alertDialogLogin(progressDialog, "Adding Appointment...");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject param = new JSONObject();

        try {
            param.put("date",appointmentData.getDate());
            param.put("patient",appointmentData.getPatientId());
            param.put("time", appointmentData.getTime());

            Log.d("Report Service", "Appointment from doctor side: \n" +
                    "Date: " + appointmentData.getDate() + "\n" +
                    "Patient ID: " + appointmentData.getPatientId() + "\n" +
                    "Time: " + appointmentData.getTime());

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        param.put("date",);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Globals.newNotifications, param, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                dialogs.displayDialog("Allotted Successfully",context);
                dialogs.dismissDialog(progressDialog);

                Toast.makeText(context, "Allotted Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogs.dismissDialog(progressDialog);
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
//                dialogs.displayDialog(error + " !",context);
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







    // Do not delete this code, it's fro converting URI to Bitmap
    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
    //    }
}


