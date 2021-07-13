package com.example.doc_app_android.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.FilterData;

import java.util.ArrayList;
import java.util.Arrays;

public class filterService {
    private MutableLiveData<ArrayList<FilterData>> data;
    public LiveData<ArrayList<FilterData>> getdata(Application app){
        if(data==null){
            data = new MutableLiveData<>();
            loaddata(app);
        }
        return data;
    }

    private void loaddata(Application app) { // calll to api containg the list should be called
        SharedPreferences pref = app.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);


        ArrayList<FilterData> list = new ArrayList<>();
        if(pref.getBoolean("isDoc",false)) {
            list.add(new FilterData("wrist"));
            list.add(new FilterData("ELbow"));
            list.add(new FilterData("Hip"));
            list.add(new FilterData("Spine"));
            list.add(new FilterData("ORTHOLOGIST"));
            list.add(new FilterData("Knee"));
        }else {
            String[] patientFilters = app.getResources().getStringArray(R.array.patientFilters);
            for (String patientFilter : patientFilters) {
                list.add(new FilterData(patientFilter));
            }
        }
        data.setValue(list);
    }

}
