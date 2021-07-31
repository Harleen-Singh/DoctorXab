package com.example.doc_app_android.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.FilterData;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.services.filterService;
import com.example.doc_app_android.services.profileService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {
    filterService service = new filterService();
    private final com.example.doc_app_android.services.profileService profileService = new profileService();
    public Application app;
    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.app = application;
    }

    public LiveData<ArrayList<FilterData>> getFilters(){
        return service.getdata(app);
    }

    public LiveData<ProfileData> getHomeDrawerProfileDetails(){
        return profileService.getProfileDetails(app, getApplication().getApplicationContext());
    }

}
