package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.services.xray_service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentXrayScanViewModel extends AndroidViewModel {
    xray_service service = new xray_service();
    public FragmentXrayScanViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<Xray_data>> get_Xray_data(){
        return service.getX_ray_data();
    }

    public LiveData<Xray_data> get_XrayReport_data() {
        return service.getX_ray_Report();
    }
}
