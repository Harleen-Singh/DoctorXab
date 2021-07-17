package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.CkpHstryData;


import java.util.ArrayList;

public class chkHstryService {
    private MutableLiveData<ArrayList<CkpHstryData>> data_model;
    private MutableLiveData<CkpHstryData> data;
    private CkpHstryData ckpHstryData;

    public LiveData<ArrayList<CkpHstryData>> getCkpHstryData() {
        if (data_model == null) {
            data_model = new MutableLiveData<>();
            loadData();
        }
        return data_model;
    }

    private void loadData() {
        String date = "15-07-04";
        String textArea = "A Quick Brown Fox Jumps Over the Lazy Dog";
        ArrayList<CkpHstryData> temp = new ArrayList<>();
        temp.add(new CkpHstryData(date,textArea));
        data_model.setValue(temp);
    }

    public LiveData<CkpHstryData> get_Xray_desc() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadDescData();
        }
        return data;
    }

    private void loadDescData() {
        String date = "11-02-03";
        String textArea = "An apple a day keeps the doctor away";
        ckpHstryData = new CkpHstryData(date,textArea);
        data.setValue(ckpHstryData);
    }


}
