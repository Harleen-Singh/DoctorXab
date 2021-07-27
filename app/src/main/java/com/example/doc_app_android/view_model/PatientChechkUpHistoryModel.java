package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.FilterData;
import com.example.doc_app_android.services.PatientCheckUpService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PatientChechkUpHistoryModel extends AndroidViewModel {

    private PatientCheckUpService patientCheckUpService = new PatientCheckUpService();
    private Application app;

    public PatientChechkUpHistoryModel(@NonNull @NotNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<ArrayList<FilterData>> getData(){
        return patientCheckUpService.getData(app);
    }
}
