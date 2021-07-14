package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.Xray_data;

import java.util.ArrayList;

public class xray_service {
    private MutableLiveData<ArrayList<Xray_data>> data_model;

    public LiveData<ArrayList<Xray_data>> getX_ray_data() {
        if (data_model == null) {
            data_model = new MutableLiveData<>();
            laodData();
        }
        return data_model;
    }

    private void laodData() {
        String date = "Date is Unknown";
        String xray_ID = "ID is UNknoen" ;
        String Xcategory = "category Unknown";
        String time = "TIME Unknown";

        ArrayList<Xray_data> temp = new ArrayList<>();
        temp.add(new Xray_data(xray_ID,Xcategory,date,time));
        data_model.setValue(temp);
    }

}
