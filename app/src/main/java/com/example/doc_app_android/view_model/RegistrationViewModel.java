package com.example.doc_app_android.view_model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.ProblemsList;
import com.example.doc_app_android.data_model.RegistrationData;
import com.example.doc_app_android.services.RegistrationService;

import java.util.ArrayList;

public class RegistrationViewModel extends AndroidViewModel {

    private final RegistrationService registrationService = new RegistrationService();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public void registerUser(Context context, RegistrationData registrationData, ConstraintLayout regContainer, FrameLayout regContainer1, boolean isDoctor, boolean fromDataLoaderFragment) {
        registrationService.registerUser(context, registrationData, regContainer, regContainer1, isDoctor, fromDataLoaderFragment);
    }

    public void addPatientToDatabase(Context context, RegistrationData registrationData, String userId, String doctorId, Activity activity, boolean fromDataLoaderFragment, int problem_id) {
        registrationService.addPatientToDataBase(context, registrationData, userId, doctorId, activity, fromDataLoaderFragment, problem_id);
    }

    public void sendRegisteredUserInformation(Context context, RegistrationData registrationData, String userId, Activity activity, boolean fromDataLoaderFragment) {
        registrationService.sendRegisteredUserInformation(context, registrationData, userId, activity, true, fromDataLoaderFragment);
    }

    public void AddProblem(String problem, Context context, RegistrationData registrationData, String userId, String doctorId, Activity activity, boolean fromDataLoaderFragment, int problem_id) {
        registrationService.addProblem(problem, context, registrationData, userId, doctorId, activity, fromDataLoaderFragment, problem_id);
    }

//    public LiveData<ProblemsList> returnedProblem(String problem, Context context) {
//        return registrationService.returnedProblem(problem, context);
//    }



    public LiveData<ArrayList<ProblemsList>> get_problem_list(Context context) {
        return registrationService.get_problem_list(context);
    }


}
