package com.example.doc_app_android.data_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.services.DoctorListService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoctorListViewModel extends AndroidViewModel {
    DoctorListService doctorListService = new DoctorListService();
    Application app;
    public DoctorListViewModel(Application application) {
        super(application);
        app = application;
    }
    public LiveData<ArrayList<DocData>> getID_name_pair(){
        return doctorListService.getId_name_Pair(app);
    }
}
