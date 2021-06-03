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

public class sign_next extends AppCompatActivity {
    private Button Next;
    private Intent intent;
    private Boolean Catch;
    private TextView signIn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_next);
        Next = findViewById(R.id.btn_next);
        signIn = findViewById(R.id.tv_sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(sign_next.this, MainActivity.class);
                intent.putExtra("reg",true);
                startActivity(intent);
                finish();
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(sign_next.this, sign_up_doctor_or_patient.class);
                Catch = getIntent().getBooleanExtra("catcher", false);
                intent.putExtra("Catch", Catch);
                startActivity(intent);
            }
        });
    }
}
