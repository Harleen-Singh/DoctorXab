package com.example.doc_app_android.view_model;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doc_app_android.DoctorHomeFragments.ProfileFragment;
import com.example.doc_app_android.data_model.ProfileData;

import com.example.doc_app_android.services.profileService;

import org.jetbrains.annotations.NotNull;

public class ProfileViewModel extends AndroidViewModel {

    private final profileService profileService = new profileService();
    public MutableLiveData<Integer> savedProfilePage = new MutableLiveData<>();
    public MutableLiveData<Integer> editProfilePage = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> department = new MutableLiveData<>();


    private ProfileData profileData;
    private MutableLiveData<ProfileData> profileServiceLiveData;
    private MutableLiveData<ProfileData> profileEditedDataMutableLiveData;
    private final Application application;

    public ProfileViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application = application;
    }



    public LiveData<ProfileData> getProfileDetails(){
        return profileService.getProfileDetails(application, getApplication().getApplicationContext());
    }

    public void getEditedProfileDetails(ProfileData profileData, Context context){
          profileService.getEditedProfileDetails(application, context, profileData);
    }

    public void onRefresh(String url, boolean values){
        profileService.getData(url, values);
    }

//    public void onClickBackButton(View view){
////        FragmentManager fragmentManager = ((AppCompatActivity)application.getApplicationContext()).getSupportFragmentManager();
////        fragmentManager.beginTransaction().remove(application.).commit();
//
//    }

//    public void onClickSaveEditedData(View view){
//
//        profileData = new ProfileData(email.getValue(), name.getValue(), phoneNumber.getValue());
//        profileEditService.init(profileData, getApplication().getApplicationContext());
//
//        profileData = profileEditService.getProfileEditedObject();
//        name.postValue(profileData.getUserName());
//        email.postValue(profileData.getEmail());
//        phoneNumber.postValue(profileData.getPhoneNumber());
//
//        savedProfilePage.setValue(View.VISIBLE);
//        editProfilePage.setValue(View.GONE);
//
//    }



}
