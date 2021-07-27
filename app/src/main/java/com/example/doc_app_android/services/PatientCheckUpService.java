package com.example.doc_app_android.services;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.FilterData;

import java.util.ArrayList;

public class PatientCheckUpService {

    private MutableLiveData<ArrayList<FilterData>> data;
    private ArrayList<FilterData> list;


    public LiveData<ArrayList<FilterData>> getData(Application app){
        if(data==null){
            data = new MutableLiveData<>();
            loadData(app);
        }

        return data;
    }

    public void loadData(Application app){
        list = new ArrayList<>();
        String[] patientFilters = app.getResources().getStringArray(R.array.patientInfoFilters);
        for (String patientFilter : patientFilters) {
            list.add(new FilterData(patientFilter));
        }
        data.setValue(list);
    }
}
