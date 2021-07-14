package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.data_model.Xray_data;


import java.util.ArrayList;

public class chkHstryService {
    private MutableLiveData<ArrayList<CkpHstryData>> data_model;

    public LiveData<ArrayList<CkpHstryData>> getCkpHstryData() {
        if (data_model == null) {
            data_model = new MutableLiveData<>();
            loadData();
        }
        return data_model;
    }

    private void loadData() {
        String date = "Unknown";
        String textArea = "A Quick Brown Fox Jumps Over the Lazy Dog";
        ArrayList<CkpHstryData> temp = new ArrayList<>();
        temp.add(new CkpHstryData(date,textArea));
        data_model.setValue(temp);
    }

}
