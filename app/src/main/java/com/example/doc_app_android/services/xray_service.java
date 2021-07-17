package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.Xray_data;

import java.util.ArrayList;

public class xray_service {
    private MutableLiveData<ArrayList<Xray_data>> data_model;
    private MutableLiveData<Xray_data> data;
    private Xray_data xray_data;

    public LiveData<ArrayList<Xray_data>> getX_ray_data() {
        if (data_model == null) {
            data_model = new MutableLiveData<>();
            laodData();
        }
        return data_model;
    }

    public LiveData<Xray_data> getX_ray_Report(){
        if(data == null){
            data = new MutableLiveData<>();
            loadReportData();
        }
        return data;
    }

    private void loadReportData() {
        String date = "15-06-07";
        String xray_ID = "60" ;
        String Xcategory = "arms";
        String time = "11:11";

        xray_data = new Xray_data(xray_ID,Xcategory,date,time);
        data.setValue(xray_data);

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
