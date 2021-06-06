package com.example.doc_app_android.ViewModel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doc_app_android.DataModel.LoginModel;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<LoginModel> userMutableLiveData;

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public MutableLiveData<LoginModel> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        Log.d("TAG", "getUser: username " + userMutableLiveData.getValue().getloginUsername());
        Log.d("TAG", "getUser: password " + userMutableLiveData.getValue().getPassword());
        return userMutableLiveData;
    }

    public void onClick(View view) {
        LoginModel loginUser = new LoginModel(userName.getValue(), password.getValue(), view.getContext());

        userMutableLiveData.setValue(loginUser);
//        Intent intent = new Intent(loginActivity.this, sign_as.class);
//        startActivity(intent);
    }
}
