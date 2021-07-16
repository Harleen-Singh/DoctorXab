package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.services.DoctorPatientListService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DataLoaderViewModel extends AndroidViewModel {

    private DoctorPatientListService service = new DoctorPatientListService();


    public DataLoaderViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public LiveData<ArrayList<ProfileData>> getPatientListForDoctorHome(String problemId){
        return service.getPatientListForDoctorHome(problemId, getApplication().getApplicationContext());
    }


}
