package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.AppointmentData;
import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.services.AppointmentService;
import com.example.doc_app_android.services.chkHstryService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragApmntViewModel extends AndroidViewModel {
    AppointmentService service = new AppointmentService();
    Application app;
    public FragApmntViewModel(@NonNull @NotNull Application application) {
        super(application);
        app = application;
    }
    public LiveData<ArrayList<AppointmentData>> getApmntData(){
        return service.getApmntData(app);
    }

    public LiveData<ArrayList<AppointmentData>> getIndividualAppointmentData(){
        return service.getIndividualAppointmentData(app);
    }

}
