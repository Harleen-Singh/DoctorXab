package com.example.doc_app_android.view_model;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doc_app_android.data_model.Register_data;
import com.example.doc_app_android.MainActivity;

public class Register_view_model extends ViewModel {

    public MutableLiveData<Integer> frameLayout = new MutableLiveData<Integer>();
    public MutableLiveData<Boolean> isClickable = new MutableLiveData<Boolean>();
    public MutableLiveData<Integer> hintChange = new MutableLiveData<Integer>();
    public Register_data register_data;
    public MutableLiveData<Register_data> userMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> contact = new MutableLiveData<>();
    public MutableLiveData<String> specialistof = new MutableLiveData<>();
    public MutableLiveData<String> createpass = new MutableLiveData<>();
    public MutableLiveData<String> cfmpass = new MutableLiveData<>();

    public MutableLiveData<Boolean> isDoc = new MutableLiveData<>();


    public MutableLiveData<Register_data> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
//        Log.d("TAG", "getUser: username " + userMutableLiveData.getValue().getloginUsername());
//        Log.d("TAG", "getUser: password " + userMutableLiveData.getValue().getPassword());
        return userMutableLiveData;
    }

    public void onNextClick(View view) {
        register_data = new Register_data(username.getValue(), contact.getValue(), email.getValue(), isDoc.getValue());
        if (!(username.getValue() == null && contact.getValue() == null && email.getValue() == null))
            if (!(username.getValue().isEmpty() && contact.getValue().isEmpty() && email.getValue().isEmpty()))
                frameLayout.setValue(View.VISIBLE);
            else
                Toast.makeText(view.getContext(), "Please Enter Your Details Properly", Toast.LENGTH_SHORT).show();
            else
            Toast.makeText(view.getContext(), "Please Enter Your Details Properly", Toast.LENGTH_SHORT).show();


    }

    public void onClickSignIn(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickSignUp(View view) {
        register_data.setCfpass(cfmpass.getValue());
        register_data.setCpass(createpass.getValue());
        register_data.setSpecialistof(specialistof.getValue());
        userMutableLiveData.setValue(register_data);
    }

    public void onClickBack(View v) {
        if (frameLayout.getValue() == View.VISIBLE)
            frameLayout.setValue(View.GONE);   // is done only when second frag is visible so if condition is also not required
    }

}
