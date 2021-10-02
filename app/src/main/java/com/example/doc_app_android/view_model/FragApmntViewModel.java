package com.example.doc_app_android.view_model;

import android.app.Application;
import android.content.Context;
import android.widget.ProgressBar;

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
    public LiveData<ArrayList<AppointmentData>> getApmntData(ProgressBar progressBar, Context context){
        return service.getApmntData(app, progressBar, context);
    }

    public LiveData<ArrayList<AppointmentData>> getIndividualAppointmentData(ProgressBar progressBar, Context context){
        return service.getIndividualAppointmentData(app, progressBar, context);
    }

    public void cancelAppointmentFromDoctorOrPatient(String id, Context context){
        service.cancelAppointmentFromDoctorOrPatient(id, app, context);
    }

}
