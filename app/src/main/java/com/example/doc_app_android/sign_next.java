package com.example.doc_app_android;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.data_model.Register_data;
import com.example.doc_app_android.databinding.SignNextBinding;
import com.example.doc_app_android.services.signUpService;
import com.example.doc_app_android.view_model.Register_view_model;

public class sign_next extends AppCompatActivity {

    private Register_view_model register_view_model;
    private signUpService signUpService;
    private static final String [] genderChoice = new String[]{"Male","Female","Other"};

    @Override
    public void onBackPressed() {   //do it prperly
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
        ArrayAdapter<String> genAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,genderChoice);
        signNextBinding.genderActv.setAdapter(genAdapter);

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
