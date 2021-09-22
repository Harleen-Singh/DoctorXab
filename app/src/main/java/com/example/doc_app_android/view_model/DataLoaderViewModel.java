package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.services.DoctorPatientListService;
import com.example.doc_app_android.utils.Globals;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DataLoaderViewModel extends AndroidViewModel {

    private DoctorPatientListService service = new DoctorPatientListService();
    private Application app;


    public DataLoaderViewModel(@NonNull @NotNull Application application) {
        super(application);
        app = application;
    }

    public LiveData<ArrayList<ProfileData>> getPatientListForDoctorHome(String problemId){
        return service.getPatientListForDoctorHome(problemId, app.getApplicationContext());
    }

    public void refresh(String patient_id){
        service.getPatientList(Globals.doctorHomeScreenPatientList + patient_id, app.getApplicationContext(), patient_id );
    }


}
