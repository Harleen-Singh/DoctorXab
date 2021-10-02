package com.example.doc_app_android.actvities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doc_app_android.R;

public class sign_as extends AppCompatActivity {
    private Button Doctor, Patient;
    private Boolean catcher;
    private ImageView backImgButton;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_as);
        Doctor = findViewById(R.id.button_doctor);
        Patient = findViewById(R.id.button_patient);
        backImgButton = findViewById(R.id.back_button_img);

        backImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putBoolean("SIGNED_UP_AS_DOCTOR", true);
                editor.apply();
                catcher = false;
                Intent intent = new Intent(sign_as.this, sign_next.class);
                intent.putExtra("catcher", catcher);
                startActivity(intent);
                finish();
            }
        });
        Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catcher = true;
                Intent intent = new Intent(sign_as.this, sign_next.class);
                intent.putExtra("catcher", catcher);
                startActivity(intent);
                finish();
            }
        });

    }

}
