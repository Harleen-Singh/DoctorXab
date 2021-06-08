package com.example.doc_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.databinding.SignNextBinding;
import com.example.doc_app_android.view_model.Login_view_model;
import com.example.doc_app_android.view_model.Register_view_model;

public class sign_next extends AppCompatActivity {

    private Register_view_model register_view_model;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SignNextBinding signNextBinding = DataBindingUtil.setContentView(this,R.layout.sign_next);
        register_view_model = new ViewModelProvider(this).get(Register_view_model.class);
        signNextBinding.setLifecycleOwner(this);
        signNextBinding.setRegisterViewModel(register_view_model);


        register_view_model.isDoc.setValue(getIntent().getBooleanExtra("catcher", false));

        register_view_model.frameLayout.setValue(View.GONE);

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        finish();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//    }
}
