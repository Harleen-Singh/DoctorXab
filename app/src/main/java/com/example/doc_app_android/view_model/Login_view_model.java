package com.example.doc_app_android.view_model;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doc_app_android.data_model.Login_data;
import com.example.doc_app_android.sign_as;


public class Login_view_model extends ViewModel {
    public MutableLiveData<Login_data> userMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    private Login_data loginUser;

    public MutableLiveData<Login_data> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
//        Log.d("TAG", "getUser: username " + userMutableLiveData.getValue().getloginUsername());
//        Log.d("TAG", "getUser: password " + userMutableLiveData.getValue().getPassword());
        return userMutableLiveData;
    }

    public void onClick(View view) {
        Log.d("TAG", "onClick: Button Clicked");
        loginUser = new Login_data(userName.getValue(), password.getValue(), view.getContext());
        userMutableLiveData.setValue(loginUser);
    }

    public void onClickSignUp(View view){
        Intent intent = new Intent(view.getContext(), sign_as.class);
        view.getContext().startActivity(intent);
    }
}
