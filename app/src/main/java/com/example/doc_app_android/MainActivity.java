package com.example.doc_app_android;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.Data_model.Login_data;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.databinding.SignInBinding;
import com.example.doc_app_android.services.loginService;
import com.example.doc_app_android.view_model.Login_view_model;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private loginService service;
    private Login_view_model viewModel;

    @Override
    public void onBackPressed() {
        super.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SignInBinding signInBinding = DataBindingUtil.setContentView(this, R.layout.sign_in);
        viewModel = new ViewModelProvider(this).get(Login_view_model.class);
        signInBinding.setLifecycleOwner(this);
        signInBinding.setViewmodel(viewModel);

        viewModel.getUser().observe(this, new Observer<Login_data>() {
            @Override
            public void onChanged(Login_data login_data) {
                Log.e("TAG", "onChanged: username " + login_data.getloginUsername());
                Log.e("TAG", "onChanged: pass " + login_data.getPassword());
                service = new loginService(login_data, MainActivity.this);

                if(TextUtils.isEmpty(Objects.requireNonNull(login_data).getloginUsername())){
                    signInBinding.editUsername.setError("Enter Username");
                }
                if(TextUtils.isEmpty(Objects.requireNonNull(login_data).getPassword())){
                    signInBinding.editPass.setError("Enter Password");
                }
            }
        });
    }
}