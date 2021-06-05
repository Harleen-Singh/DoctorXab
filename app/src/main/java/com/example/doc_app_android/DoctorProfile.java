package com.example.doc_app_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DoctorProfile extends AppCompatActivity {

    private ImageView backButton;
    private Button savedSave;
    private Button save;
    private Button edit;
    private RelativeLayout editLayout;
    private RelativeLayout saveLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        backButton = findViewById(R.id.profile_back_button_save);
        savedSave = findViewById(R.id.saved_save_button);
        edit = findViewById(R.id.saved_edit_button);
        save = findViewById(R.id.btn_edit_save);
        editLayout = findViewById(R.id.doctor_profile_edit);
        saveLayout = findViewById(R.id.doctor_profile_save);


        savedSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLayout.setVisibility(View.GONE);
                editLayout.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLayout.setVisibility(View.GONE);
                saveLayout.setVisibility(View.VISIBLE);

            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}