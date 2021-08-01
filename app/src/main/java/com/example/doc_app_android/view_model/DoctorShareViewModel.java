package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.services.DoctorListService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoctorShareViewModel extends AndroidViewModel {

    DoctorListService doctorListService = new DoctorListService();
    Application app;
    public DoctorShareViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<ArrayList<DocData>> getDoctorList(){
        return doctorListService.getDocList(app);
    }
}
