package com.example.doc_app_android.view_model;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doc_app_android.MainActivity;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.data_model.Register_data;

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
    public MutableLiveData<Integer> gender = new MutableLiveData<>();
    public MutableLiveData<Boolean> isDoc = new MutableLiveData<>();
    public MutableLiveData<Boolean> docFragFlag = new MutableLiveData<>();
    public Boolean flag;
    public MutableLiveData<String> docId = new MutableLiveData<>();
    public MutableLiveData<Register_data> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
//        Log.d("TAG", "getUser: username " + userMutableLiveData.getValue().getloginUsername());
//        Log.d("TAG", "getUser: password " + userMutableLiveData.getValue().getPassword());
        return userMutableLiveData;
    }


    public void onClickSignUp(View view) {
        flag = true;

        if (username.getValue() != null && email.getValue() != null && createpass.getValue() != null && cfmpass.getValue() != null) {
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

    public void selectDialog(View v) {
        docFragFlag.setValue(true);
    }

    public void onClickContinue(View view) {
       flag = false;
        Log.e("TAG", "doc name----->>: "+specialistof.getValue());
        if (specialistof.getValue()!=null && name.getValue() != null && contact.getValue() != null && age.getValue() != null && gender.getValue() != null) {
            if(contact.getValue().length()==10 && (Integer.parseInt(age.getValue())>=1 && Integer.parseInt(age.getValue())<=110)){
                String gen="Male";
                switch (gender.getValue()){
                    case 0:
                        gen = "Male";
                        break;
                    case 1:
                        gen = "Female";
                        break;
                    case 2:
                        gen = "Other";
                        break;
                }

                register_data = new Register_data(name.getValue(),contact.getValue(),age.getValue(),gen,isDoc.getValue());
                register_data.setSpecialistof(docId.getValue());
                userMutableLiveData.setValue(register_data);
            }
            else
                Toast.makeText(view.getContext(), "Invalid Age (1-110) or Phone Number (10-Digits)", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(view.getContext(), "Please Enter Your Details Properly", Toast.LENGTH_SHORT).show();
    }

    }

//    public void onClickBack(View v) {
//        if (frameLayout.getValue() == View.VISIBLE)
//            frameLayout.setValue(View.GONE);   // is done only when second frag is visible so if condition is also not required
//    }

