package com.example.doc_app_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.data_model.Login_data;
import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.databinding.SignInBinding;
import com.example.doc_app_android.services.loginService;
import com.example.doc_app_android.view_model.Login_view_model;

public class MainActivity extends AppCompatActivity {

    private loginService service;
    private Login_view_model viewModel;
    private ProgressDialog progressDialog;
    private dialogs dialogs = new dialogs();
    private SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        super.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        progressDialog = new ProgressDialog(this, R.style.AlertDialog);

        SignInBinding signInBinding = DataBindingUtil.setContentView(this, R.layout.sign_in);
        viewModel = new ViewModelProvider(this).get(Login_view_model.class);
        signInBinding.setLifecycleOwner(this);
        signInBinding.setViewmodel(viewModel);


        final SharedPreferences sp = getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("cur_frag_id","1");
        editor.apply();

        boolean isDoc = sp.getBoolean("isDoc", false);
        if (sp.contains("id")) {
            editor = sp.edit();
            editor.putString("cur_frag_id","1");
            editor.apply();
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }


        viewModel.getUser().observe(this, new Observer<Login_data>() {
            @Override
            public void onChanged(Login_data login_data) {

                if (login_data.getPassword() != null && login_data.getloginUsername() != null) {

                    if (!login_data.getloginUsername().isEmpty() && !login_data.getPassword().isEmpty()) {
                        Log.e("TAG", "onChanged: username " + login_data.getloginUsername());
                        Log.e("TAG", "onChanged: pass " + login_data.getPassword());
                        service = new loginService(login_data, MainActivity.this);

                    } else if (!login_data.getloginUsername().isEmpty() && login_data.getPassword().isEmpty()) {
                        signInBinding.editPass.setError("Enter Password");
                    } else if (login_data.getloginUsername().isEmpty() && !login_data.getPassword().isEmpty()) {
                        signInBinding.editUsername.setError("Enter Username");
                    } else {
                        signInBinding.editUsername.setError("Enter Username");
                        signInBinding.editPass.setError("Enter Password");
                    }
                }
                else{
                    if(login_data.getPassword() == null){
                        signInBinding.editPass.setError("Enter Password");
                    }
                    if(login_data.getloginUsername() == null) {
                        signInBinding.editUsername.setError("Enter Username");
                    }
                }
            }
        });
    }
}