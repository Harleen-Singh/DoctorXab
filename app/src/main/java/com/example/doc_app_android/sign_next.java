package com.example.doc_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.Data_model.Register_data;
import com.example.doc_app_android.databinding.SignNextBinding;
import com.example.doc_app_android.services.signUpService;
import com.example.doc_app_android.view_model.Login_view_model;
import com.example.doc_app_android.view_model.Register_view_model;

public class sign_next extends AppCompatActivity {

    private Register_view_model register_view_model;
    private signUpService signUpService;

    @Override
    public void onBackPressed() {
        if (register_view_model.frameLayout.getValue() == View.VISIBLE)
            register_view_model.frameLayout.setValue(View.GONE);
        else
            super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SignNextBinding signNextBinding = DataBindingUtil.setContentView(this, R.layout.sign_next);
        register_view_model = new ViewModelProvider(this).get(Register_view_model.class);
        signNextBinding.setLifecycleOwner(this);
        signNextBinding.setRegisterViewModel(register_view_model);


        register_view_model.isDoc.setValue(getIntent().getBooleanExtra("catcher", false));
        if (getIntent().getBooleanExtra("catcher", false)) {
            register_view_model.hintChange.setValue(R.string.consult_to);
        } else {
            register_view_model.hintChange.setValue(R.string.specialist_of);
        }
        register_view_model.frameLayout.setValue(View.GONE);

        register_view_model.getUser().observe(this, new Observer<Register_data>() {
            @Override
            public void onChanged(Register_data register_data) {
                if (register_data.getCfpass() != null && register_data.getCpass() != null)
                    if (register_data.getCfpass().equals(register_data.getCpass()))
                        signUpService = new signUpService(register_data, sign_next.this, getIntent().getBooleanExtra("catcher", false));
                    else
                        Toast.makeText(sign_next.this, "enter same pass", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
