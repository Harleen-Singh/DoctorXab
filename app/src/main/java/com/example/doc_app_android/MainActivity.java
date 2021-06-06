package com.example.doc_app_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doc_app_android.Data_model.Login_data;
import com.example.doc_app_android.databinding.SignInBinding;
import com.example.doc_app_android.services.loginService;
import com.example.doc_app_android.view_model.Login_view_model;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

private Button signIn;
private TextView signUp;
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
                Log.e("TAG", "onChanged: username " + login_data.getloginUsername() );
                Log.e("TAG", "onChanged: pass " + login_data.getPassword() );
                service = new loginService(login_data.getloginUsername() , login_data.getPassword() , MainActivity.this);
            }
        });

//        setContentView(R.layout.sign_in);
//        signIn = findViewById(R.id.btn_sign_in);
//        signUp = findViewById(R.id.tv_sign_up);
//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,Home.class);
//                startActivity(intent);
//            }
//        });
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,sign_as.class);
//                startActivity(intent);
//            }
//        });
    }
}