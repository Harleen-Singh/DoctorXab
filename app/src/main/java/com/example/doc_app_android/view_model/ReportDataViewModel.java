package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.Adapter.ReportData;
import com.example.doc_app_android.services.ReportService;

import org.jetbrains.annotations.NotNull;

public class ReportDataViewModel extends AndroidViewModel {

    private ReportService reportService = new ReportService();
    private Application application;
    public ReportDataViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<ReportData> getReportData(){

        return reportService.getReportData(application);
    }

    public void updateReportData(ReportData reportData){
        reportService.updateReportData(application, reportData);
    }
}
