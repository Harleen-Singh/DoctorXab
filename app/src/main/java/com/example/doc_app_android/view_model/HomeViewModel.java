package com.example.doc_app_android.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.FilterData;
import com.example.doc_app_android.services.filterService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {
    filterService service = new filterService();
    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }
    public LiveData<ArrayList<FilterData>> getFilters(){
        Log.d("TAG", "getFilters: " + service.getdata().getValue().get(0).getFilter());
        return service.getdata();
    }

}
