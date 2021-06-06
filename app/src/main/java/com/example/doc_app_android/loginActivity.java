package com.example.doc_app_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.doc_app_android.Service.loginService;
import com.example.doc_app_android.ViewModel.LoginViewModel;
import com.example.doc_app_android.databinding.SignInBinding;

public class loginActivity extends AppCompatActivity {

private Button signIn;
private TextView signUp;
private SignInBinding binding;
private LoginViewModel model;
private loginService service;
    @Override
    public void onBackPressed() {
        super.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        model = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(loginActivity.this, R.layout.sign_in);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(model);



    }
}