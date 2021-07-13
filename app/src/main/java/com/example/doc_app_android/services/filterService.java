package com.example.doc_app_android.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.FilterData;

import java.util.ArrayList;

public class filterService {
    private MutableLiveData<ArrayList<FilterData>> data;
    public LiveData<ArrayList<FilterData>> getdata(){
        if(data==null){
            data = new MutableLiveData<>();
            loaddata();
        }
        return data;
    }

    private void loaddata() { // calll to api containg the list should be called
        ArrayList<FilterData> list = new ArrayList<>();
        list.add(new FilterData("wrist"));
        list.add(new FilterData("ELbow"));
        list.add(new FilterData("Hip"));
        list.add(new FilterData("Spine"));
        list.add(new FilterData("Leg"));
        list.add(new FilterData("Knee"));

        data.setValue(list);
    }

}
