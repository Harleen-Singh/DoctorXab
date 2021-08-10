package com.example.doc_app_android.services;

import android.app.Application;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Adapter.ReportData;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.LoadingDialogBinding;
import com.example.doc_app_android.volley.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ReportService {

    private int id, patientId;
    private String date, data, patient;
    private ReportData reportData1;
    private MutableLiveData<ReportData> reportDataMutableLiveData;
    private SharedPreferences prefs;
    private String url;
    private Dialog dialog1;
    private LoadingDialogBinding loadingDialogBinding;
    private Application application;
    private Context context;


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

    public void addPatientReport(Application mApplication, Context mContext, com.example.doc_app_android.data_model.ReportData reportData){

        context = mContext;
        application = mApplication;

        loadingDialogBinding = LoadingDialogBinding.inflate(LayoutInflater.from(context));
        loadingDialogBinding.getRoot().setBackgroundResource(android.R.color.transparent);
        dialog1 = new Dialog(context);
        dialog1.setCancelable(false);
        dialog1.setContentView(loadingDialogBinding.getRoot());
        loadingDialogBinding.updateProgress.setVisibility(View.VISIBLE);
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        sp = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
//        id = Integer.parseInt(sp.getString("doctor_id", ""));

        Log.d("TestingProfileEdit", "URL: " + Globals.editGenDetails + id);
       // addXrayImage("hjs", reportData, mContext);
//        addReport("hsj", context);

    }

//    public void addXrayImage(String ROOT_URL, com.example.doc_app_android.data_model.ReportData editedProfileData, Context context) {
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.PUT, ROOT_URL,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        Log.d("Testing", "Upload Image response" + response);
//                        Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("GotError", "" + error.getMessage());
//                       // dialog1.hide();
//                    }
//                }) {
//
//
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                //editedProfileData.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
//                params.put("image", new DataPart("Profile" + imagename + ".jpg", bytesOfImage));
//                return params;
//            }
//        };
//
//        //adding the request to volley
//        Volley.newRequestQueue(context).add(volleyMultipartRequest);
//    }

//    public void addReport(String Url, Context context, com.example.doc_app_android.data_model.ReportData data) {
//
////        NavHeaderBinding binding = NavHeaderBinding.inflate(LayoutInflater.from(context));
//        JSONObject details = null;
//
//        try {
//            details = new JSONObject();
//            details.put("pic_id", data.getXray_id());
//            details.put("patient", data.getPatientId());
//            details.put("category", data.getCategory());
//            details.put("time", data.getTime());
//            details.put("date", data.getDate());
//            JSONObject report = new JSONObject();
//            report.put("date", data.getDate());
//            report.put("data", data.getReport());
//            details.put("report", report);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        final RequestQueue detailsRequestQueue = Volley.newRequestQueue(context);
//
//        JsonObjectRequest sendEditDetailsRequest = new JsonObjectRequest(Request.Method.POST, Globals.uploadReport, details, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("Testing", "Output From Profile Edit Service: " + response.toString());
//
//                try {
////                    id = response.getInt("id");
////                    name = response.getString("name");
////                    userName = response.getString("username");
////                    phoneNumber = response.getString("phone_number");
////                    address = response.getString("address");
////                    email = response.getString("email");
////                    image = "https://maivrikdoc.herokuapp.com/api" + response.getString("image");
////                    age = response.getInt("age");
////                    Log.d("TestingProfileEdit", "id: " + id);
////                    Log.d("TestingProfileEdit", "name: " + name);
////                    Log.d("TestingProfileEdit", "username: " + userName);
////                    Log.d("TestingProfileEdit", "addsress: " + phoneNumber);
////                    Log.d("TestingProfileEdit", "email: " + email);
////                    Log.d("TestingProfileEdit", "image: " + image);
////                    Log.d("TestingProfileEdit", "id: " + age);
//
//
////                    binding.navProfileName.setText(name);
////                    Picasso.get().load(image).placeholder(R.drawable.doctor_profile_image).into(binding.navProfileImage);
//
//                    //int doctor_Id, int age, String userName, String email, String name, String phoneNumber, String address, String image
////                    editedProfileData = new ProfileData(id, age, userName, email, name, phoneNumber, address, image);
////                    mutableProfileData.setValue(editedProfileData);
//
////                    loadingDialogBinding.updateProgress.setVisibility(View.GONE);
////                    dialog1.hide();
////                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
////
//
//                    //dialogs.dismissDialog(progressDialog);
//
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                dialog1.hide();
//
//                if (error instanceof NoConnectionError) {
//                    //dialogs.displayDialog("Not Connected to Internet", context);
//
//                }
//                if (error instanceof ServerError) {
////                    Toast.makeText(context, "ServiceError: " + error, Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
//                }
//
//                //dialogs.dismissDialog(progressDialog);
//                //dialogs.dismissDialog(progressDialog);
//
//            }
//        });
////        {
////            @Nullable
////            @org.jetbrains.annotations.Nullable
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////
////                Map<String, String> map = new HashMap<>();
////                map.put("image", editedProfileData.getImage());
////                return map;
////            }
////        };
//
//
//        detailsRequestQueue.add(sendEditDetailsRequest);
//
//        sendEditDetailsRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//
//
//    }

    // Do not delete this code, it's fro converting URI to Bitmap
    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
    //    }
}


