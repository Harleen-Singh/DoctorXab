package com.example.doc_app_android.services;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doc_app_android.Globals;
import com.example.doc_app_android.data_model.DocData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorListService {
    Context app;
    private MutableLiveData<ArrayList<DocData>> data;
    private MutableLiveData<ArrayList<DocData>> id_name_Pair;

    public MutableLiveData<ArrayList<DocData>> getDocList(Context context) {
        this.app =context;
        if (data == null) {
            id_name_Pair = new MutableLiveData<>();
            data = new MutableLiveData<>();
            loadData();
        }
        return data;
    }

    public MutableLiveData<ArrayList<DocData>> getId_name_Pair(Context context) {
        this.app =context;
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
                        String docID , username, name , age, state,image, email,phone , department;
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
                        Log.e("TAG", "onResponse: "+phone );
                        tempData.add(new DocData(docID,username,name,age,state,image,email,phone,department));
                        tempid_name_Pair.add(new DocData(docID,name));
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


}
