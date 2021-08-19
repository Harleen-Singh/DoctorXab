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
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> specialistof = new MutableLiveData<>();
    public MutableLiveData<String> createpass = new MutableLiveData<>();
    public MutableLiveData<String> cfmpass = new MutableLiveData<>();
    public MutableLiveData<String> age = new MutableLiveData<>();
    public MutableLiveData<String> gender = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDoc = new MutableLiveData<>();
    public Boolean flag;


    public MutableLiveData<Register_data> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
//        Log.d("TAG", "getUser: username " + userMutableLiveData.getValue().getloginUsername());
//        Log.d("TAG", "getUser: password " + userMutableLiveData.getValue().getPassword());
        return userMutableLiveData;
    }

    public void onNextClick(View view) {
        flag = true;
        if (!(username.getValue() == null && email.getValue() == null && createpass.getValue() == null && cfmpass.getValue()==null)) {
            register_data = new Register_data(username.getValue(), email.getValue(), createpass.getValue(), cfmpass.getValue());
            if(register_data.getCfpass().equals(register_data.getCpass())) {
                register_data = new Register_data(username.getValue(), email.getValue(), createpass.getValue(), cfmpass.getValue());
                userMutableLiveData.setValue(register_data);
            }
            else{
                Toast.makeText(view.getContext(), "Passwords Don't Match", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(view.getContext(), "Please Enter Your Details Properly", Toast.LENGTH_SHORT).show();
    }

    public void onClickSignIn(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickSignUp(View view) {
       flag = false;
        if (!(name.getValue() == null && contact.getValue() == null && age.getValue() == null && gender.getValue()==null)) {
            register_data = new Register_data(name.getValue(),contact.getValue(),age.getValue(),gender.getValue(),isDoc.getValue());
            System.out.println("--->>>"+gender.getValue());
            userMutableLiveData.setValue(register_data);
        }
        else
            Toast.makeText(view.getContext(), "Please Enter Your Details Properly", Toast.LENGTH_SHORT).show();
    }

    }

//    public void onClickBack(View v) {
//        if (frameLayout.getValue() == View.VISIBLE)
//            frameLayout.setValue(View.GONE);   // is done only when second frag is visible so if condition is also not required
//    }

