package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.services.profileService;

import org.jetbrains.annotations.NotNull;

public class ProfileViewModel extends AndroidViewModel {

    private final profileService profileService = new profileService();
    private MutableLiveData<ProfileData> profileServiceLiveData;
    private final Application application;

    public ProfileViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<ProfileData> getProfileDetails(){
        return profileService.getProfileDetails(application, getApplication().getApplicationContext());
    }

}
