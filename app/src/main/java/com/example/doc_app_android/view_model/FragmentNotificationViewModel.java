package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.services.notiService;

import java.util.ArrayList;

public class FragmentNotificationViewModel extends AndroidViewModel {
    notiService service = new notiService();
    Application app;
    public FragmentNotificationViewModel(@NonNull Application application) {
        super(application);
        app = application;
    }

    public LiveData<String> getCount(){
        return service.getCount(app);
    }

    public LiveData<ArrayList<NotiData>> getNotiData(){
        return service.getNotiData(app);
    }

    public void refresh() {
        service.loadData();
    }
}
