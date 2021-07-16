package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.PrescData;
import com.example.doc_app_android.data_model.Xray_data;


import java.util.ArrayList;

public class prescService {
    private MutableLiveData<ArrayList<PrescData>> data_model;

    public LiveData<ArrayList<PrescData>> getPrescData() {
        if (data_model == null) {
            data_model = new MutableLiveData<>();
            loadData();
        }
        return data_model;
    }

    private void loadData() {
        String date = "Date is Unknown";
        String prescText = "Drinks lots of fluids";

        ArrayList<PrescData> temp = new ArrayList<>();
        temp.add(new PrescData(prescText,date));
        data_model.setValue(temp);
    }

}
