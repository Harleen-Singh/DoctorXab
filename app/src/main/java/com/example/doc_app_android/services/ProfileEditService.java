package com.example.doc_app_android.services;

import android.app.ProgressDialog;
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
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileEditService {

    private int id, age;
    private String userName, name, phoneNumber, address, email, image;
    private ProfileData editedProfileData;
    private MutableLiveData<ProfileData> mutableProfileData = new MutableLiveData<>();
    private Context context;
    private ProgressDialog progressDialog;
    private com.example.doc_app_android.Dialogs.dialogs dialogs = new dialogs();


    public ProfileEditService() {

    }


    public void init(ProfileData profileData, Context context) {
        this.context = context;
        final SharedPreferences sp = context.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
//        boolean isDoc = sp.getBoolean("isDoc", false);
        String id = sp.getString("id", "");
        progressDialog = new ProgressDialog(context, R.style.AlertDialog);
        editData(Globals.editGenDetails + id, profileData);
    }

    public LiveData<ProfileData> getEditedProfileDetails() {
        return mutableProfileData;
    }


    public void editData(String Url, ProfileData data) {
        JSONObject details = null;

        try {
            details = new JSONObject();
            details.put("username", data.getUserName());
            details.put("email", data.getEmail());
            details.put("phone_number", data.getPhoneNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue detailsRequestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest sendEditDetailsRequest = new JsonObjectRequest(Request.Method.PUT, Url, details, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Testing", "Output: " + response.toString());

                try {
                    id = response.getInt("id");
                    name = response.getString("name");
                    userName = response.getString("username");
                    phoneNumber = response.getString("phone_number");
                    address = response.getString("address");
                    email = response.getString("email");
                    image = response.getString("image");
                    age = response.getInt("age");

                    editedProfileData = new ProfileData(id, userName, email, phoneNumber, address, image, age);
                    mutableProfileData.postValue(editedProfileData);
                    dialogs.dismissDialog(progressDialog);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NoConnectionError) {
                    dialogs.displayDialog("Not Connected to Internet", context);

                }
                if (error instanceof ServerError) {
                    Toast.makeText(context, "ServiceError: " + error, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("", "error.networkRespose.toString()" + error.networkResponse.toString());
                }

                dialogs.dismissDialog(progressDialog);
                //dialogs.dismissDialog(progressDialog);

            }
        });

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

    public ProfileData getProfileEditedObject() {
        return editedProfileData;
    }
}
