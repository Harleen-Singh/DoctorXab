package com.example.doc_app_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.doc_app_android.Adapter.BodyParts;
import com.example.doc_app_android.Adapter.BodyPartsAdapter;
import com.example.doc_app_android.Adapter.PatientDetails;
import com.example.doc_app_android.Adapter.PatientDetailsAdapter;
import com.example.doc_app_android.HomeFragments.AppointmentsFragment;
import com.example.doc_app_android.HomeFragments.DataLoaderFragment;
import com.example.doc_app_android.HomeFragments.PrivacyPolicyFragment;
import com.example.doc_app_android.HomeFragments.ProfileFragment;
import com.example.doc_app_android.HomeFragments.ScheduleFragment;
import com.example.doc_app_android.HomeFragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity implements BodyPartsAdapter.OnPartListener {

    private NavigationView nav;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private boolean regornot = false;
    //private RecyclerView rcv;
    private RecyclerView button_rcv;
    private ArrayList<PatientDetails> data;
    private ArrayList<BodyParts> data1;
    private int selectedItemPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //rcv = findViewById(R.id.patient_details_rcv);
        button_rcv = findViewById(R.id.button_rcv);

        regornot = getIntent().getBooleanExtra("reg", false);


        // For adding the action bar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        nav = (NavigationView) findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setCheckedItem(R.id.menu_profile);


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_profile:
                        temp = new ProfileFragment();
                        break;

                    case R.id.menu_appointments:
                        Toast.makeText(getApplicationContext(), "Appointments Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new AppointmentsFragment();
                        break;

                    case R.id.menu_schedule:
                        Toast.makeText(getApplicationContext(), "Schedule Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new ScheduleFragment();
                        break;

                    case R.id.menu_settings:
                        Toast.makeText(getApplicationContext(), "Settings Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new SettingsFragment();
                        break;

                    case R.id.menu_privacyplicy:
                        Toast.makeText(getApplicationContext(), "Privacy Policy Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new PrivacyPolicyFragment();
                        break;

                    case R.id.menu_logout:
                        SharedPreferences.Editor editor = getSharedPreferences("tokenFile", Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(Home.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                if (temp != null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        initButton();
        setButtonRecycler();
    }


    private void setButtonRecycler() {
        BodyPartsAdapter bodyPartsAdapter = new BodyPartsAdapter(data1, this, this);
        button_rcv.setAdapter(bodyPartsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        button_rcv.setLayoutManager(linearLayoutManager);
    }

    private void initButton() {
        data1 = new ArrayList<>();
        data1.add(new BodyParts("Wrist"));
        data1.add(new BodyParts("Elbow"));
        data1.add(new BodyParts("Hip"));
        data1.add(new BodyParts("Eyes"));
        data1.add(new BodyParts("Ears"));
        data1.add(new BodyParts("Nose"));
        data1.add(new BodyParts("Others"));

    }

    @Override
    public void onPartClick(int position, Button btn) {

        selectedItemPosition = position;
        if (selectedItemPosition == position) {
            Drawable bg = getResources().getDrawable(R.drawable.btn_theme_1);
            btn.setBackground(bg);
            btn.setTextColor(getResources().getColor(R.color.white));
        }
        else {
            Drawable bg = getResources().getDrawable(R.drawable.add_label_theme);
            btn.setBackground(bg);
            btn.setTextColor(getResources().getColor(R.color.scnd_grey));
        }

        Log.e("Testing", "I am at " + position + " getting clicked.");
        getSupportFragmentManager().beginTransaction().replace(R.id.patient_container, new DataLoaderFragment()).commit();

    }
}

