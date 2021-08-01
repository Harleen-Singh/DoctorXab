package com.example.doc_app_android.services;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
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
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.LoadingDialogBinding;
import com.example.doc_app_android.volley.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileEditService {

    private int id, age;
    private String userName, name, phoneNumber, address, email, image;
    private ProfileData editedProfileData;
    private MutableLiveData<ProfileData> mutableProfileData;
    private Context context;
    private Dialog dialog1;
    private LoadingDialogBinding loadingDialogBinding;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private Application application;
//    private ProgressDialog progressDialog;
//    private com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();


    public ProfileEditService() {

    }


    public void init(ProfileData profileData, Context context) {
        this.context = context;
        this.editedProfileData = profileData;




        //progressDialog = new ProgressDialog(context, R.style.AlertDialog);

    }

    public LiveData<ProfileData> getEditedProfileDetails(Application mApplication, Context mContext, ProfileData editedProfileData) {
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
        sp = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        id = Integer.parseInt(sp.getString("doctor_id", ""));
        if (mutableProfileData == null) {
            mutableProfileData = new MutableLiveData<>();
            Log.d("TestingProfileEdit", "URL: " + Globals.editGenDetails + id);
            uploadImage(Globals.editGenDetails + id, editedProfileData);
            editData(Globals.editGenDetails + id, editedProfileData);
        }
        //dialog1.hide();
        return mutableProfileData;
    }


    public void editData(String Url, ProfileData data) {
        JSONObject details = null;

        try {
            details = new JSONObject();
            details.put("name", data.getName());
            details.put("email", data.getEmail());
            details.put("phone_number", data.getPhoneNumber());
            //details.put("image", data.getImage());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue detailsRequestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest sendEditDetailsRequest = new JsonObjectRequest(Request.Method.PUT, Url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Testing", "Output From Profile Edit Service: " + response.toString());

                try {
                    id = response.getInt("id");
                    name = response.getString("name");
                    userName = response.getString("username");
                    phoneNumber = response.getString("phone_number");
                    address = response.getString("address");
                    email = response.getString("email");
                    image = response.getString("image");
                    age = response.getInt("age");
                    Log.d("TestingProfileEdit", "id: " + id);
                    Log.d("TestingProfileEdit", "name: " + name);
                    Log.d("TestingProfileEdit", "username: " + userName);
                    Log.d("TestingProfileEdit", "addsress: " + phoneNumber);
                    Log.d("TestingProfileEdit", "email: " + email);
                    Log.d("TestingProfileEdit", "image: " + image);
                    Log.d("TestingProfileEdit", "id: " + age);


                    editedProfileData = new ProfileData(id, userName, email, phoneNumber, address, image, age);
                    mutableProfileData = new MutableLiveData<>();
                    mutableProfileData.setValue(editedProfileData);

                    loadingDialogBinding.updateProgress.setVisibility(View.GONE);
                    dialog1.hide();
                    Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    editor = sp.edit();
                    editor.putBoolean("hasProfileUpdate", true);
                    editor.apply();

                    //dialogs.dismissDialog(progressDialog);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    //dialogs.displayDialog("Not Connected to Internet", context);

                }
                if (error instanceof ServerError) {
                    Toast.makeText(context, "ServiceError: " + error, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }

                //dialogs.dismissDialog(progressDialog);
                //dialogs.dismissDialog(progressDialog);

            }
        });
//        {
//            @Nullable
//            @org.jetbrains.annotations.Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> map = new HashMap<>();
//                map.put("image", editedProfileData.getImage());
//                return map;
//            }
//        };


        detailsRequestQueue.add(sendEditDetailsRequest);

        sendEditDetailsRequest.setRetryPolicy(new RetryPolicy() {
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

    public void setProfileEditedObject(ProfileData profileData, Context context) {
        editedProfileData = profileData;


//        editor = sp.edit();
//        editor.putBoolean("hasProfileEdited", true);
//        editor.apply();
    }

    public void uploadImage(String ROOT_URL, ProfileData editedProfileData){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.PUT, ROOT_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                       Toast.makeText(context, "Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError", "" + error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                editedProfileData.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
                params.put("image", new DataPart("Profile" + imagename + ".jpg", bytesOfImage));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

}

