package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.Pat_prescription;
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
        String date = "15-02-2021";
        int count=1;
        Pat_prescription prescText = new Pat_prescription("Drinks lots of fluids" + count);
        ArrayList<Pat_prescription> list = new ArrayList<>();
        list.add(prescText);
        count++;
        prescText = new Pat_prescription("Drinks lots of fluids" + count);
        count++;
        list.add(prescText);
        prescText = new Pat_prescription("Drinks lots of fluids" + count);
        count++;
        list.add(prescText);
        prescText = new Pat_prescription("Drinks lots of fluids" + count);
        count++;
        list.add(prescText);
        prescText = new Pat_prescription("Drinks lots of fluids" + count);
        count++;
        list.add(prescText);
        prescText = new Pat_prescription("Drinks lots of fluids" + count);
        list.add(prescText);
        count++;
        list.add(prescText);
        count++;
        prescText = new Pat_prescription("Drinks lots of fluids" + count);
        list.add(prescText);
        ArrayList<PrescData> temp = new ArrayList<>();
        temp.add(new PrescData(date,list));
        temp.add(new PrescData(date,list));
        data_model.setValue(temp);
    }

}
