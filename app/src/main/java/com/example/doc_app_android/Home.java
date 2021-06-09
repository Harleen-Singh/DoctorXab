package com.example.doc_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.doc_app_android.HomeFragments.PrivacyPolicyFragment;
import com.example.doc_app_android.HomeFragments.ProfileFragment;
import com.example.doc_app_android.HomeFragments.ScheduleFragment;
import com.example.doc_app_android.HomeFragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity {

    private NavigationView nav;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private boolean regornot = false;
    private RecyclerView rcv;
    private RecyclerView button_rcv;
    private ArrayList<PatientDetails> data;
    private ArrayList<BodyParts> data1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);





        rcv = findViewById(R.id.patient_details_rcv);
        button_rcv = findViewById(R.id.button_rcv);

        regornot = getIntent().getBooleanExtra("reg", false);




        // For adding the action bar.
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        nav = (NavigationView)findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setCheckedItem(R.id.menu_profile);




        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

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
                        if(regornot){
                            Intent intent = new Intent(Home.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            finish();
                        }
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        init();
        initButton();
        setRecycler();
        setButtonRecycler();
    }

    private void setRecycler(){
        PatientDetailsAdapter patientDetailsAdapter = new PatientDetailsAdapter(data,this);
        rcv.setAdapter(patientDetailsAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(rcv.getItemAnimator())).setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
    }

    private void init(){
        data = new ArrayList<>();

        data.add(new PatientDetails("John Cena", "01-01-2021", "21", "OPERATION", "New York"));
        data.add(new PatientDetails("Harry Potter", "02-01-2021", "21", "OPERATION", "United Kingdom"));
        data.add(new PatientDetails("Ed Sheeran", "03-01-2021", "31", "OPERATION", "South Yorkshire"));
        data.add(new PatientDetails("Virat Kohli", "04-01-2021", "32", "OPERATION", "New Delhi"));
        data.add(new PatientDetails("Ms Dhoni", "05-01-2021", "40", "OPERATION", "Jharkhand"));
        data.add(new PatientDetails("Hg Wells", "06-01-2021", "78", "OPERATION", "Southern California"));
        data.add(new PatientDetails("Alex Rovanja", "07-01-2021", "21", "OPERATION", "London"));
        data.add(new PatientDetails("Johnny Cash", "08-01-2021", "93", "OPERATION", "New Jersey"));
        data.add(new PatientDetails("Ankita Thakur", "09-01-2021", "23", "OPERATION", "Punjab"));
        data.add(new PatientDetails("Harleen Singh", "10-01-2021", "24", "OPERATION", "Punjab"));

    }

    private void setButtonRecycler(){
        BodyPartsAdapter bodyPartsAdapter = new BodyPartsAdapter(data1, this);
        button_rcv.setAdapter(bodyPartsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        button_rcv.setLayoutManager(linearLayoutManager);
    }

    private void initButton(){
        data1 = new ArrayList<>();
        data1.add(new BodyParts("Wrist"));
        data1.add(new BodyParts("Elbow"));
        data1.add(new BodyParts("Hip"));
        data1.add(new BodyParts("Eyes"));
        data1.add(new BodyParts("Ears"));
        data1.add(new BodyParts("Nose"));
        data1.add(new BodyParts("Others"));

    }
}

