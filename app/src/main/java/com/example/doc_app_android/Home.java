package com.example.doc_app_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.doc_app_android.Adapter.PatientDetails;
import com.example.doc_app_android.Adapter.PatientDetailsAdapter;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    NavigationView nav;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    MenuItem menuItem;
    Menu menu;
    private boolean regornot = false;


    private RecyclerView rcv;
    private ArrayList<PatientDetails> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcv = findViewById(R.id.patient_details_rcv);

        regornot = getIntent().getBooleanExtra("reg", false);



        // For adding the action bar.
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //
        nav = (NavigationView)findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        // we have passes five parameters in the ActionBarDrawerToggle constructor
        // 1. Context itself.
        // 2. drawerlayout object, we have passed the drawer layout object in the function for connecting the drawer layout with toggle button.
        // 3. toolbar object, to fix the toggle button to the toolbar.
        // 4. two messages has been passed open and close(It can only be passed through string file reference).
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        // we have used addDrawerListener with drawerlayout object and passed toggle button as parameter because drawer layout will monitor
        // the activity of the toggle button and as its response it will be opened or closed.
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
//        nav.setCheckedItem(R.id.menu_profile);


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_profile:
                        Toast.makeText(getApplicationContext(), "Home Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new ProfileFragment();
                        break;

                    case R.id.menu_appointments:
                        Toast.makeText(getApplicationContext(), "Call Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new AppointmentFragment();
                        break;

                    case R.id.menu_schedule:
                        Toast.makeText(getApplicationContext(), "Settings Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new ScheduleFragment();
                        break;

                    case R.id.menu_settings:
                        Toast.makeText(getApplicationContext(), "Settings Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new SettingsFragment();
                        break;

                    case R.id.menu_privacyplicy:
                        Toast.makeText(getApplicationContext(), "Settings Pane is opened.", Toast.LENGTH_SHORT).show();
                        temp = new PrivacyPolicyFragment();
                        break;

                    case R.id.menu_logout:
                        if(regornot){
                            Intent intent = new Intent(Home.this, MainActivity.class);
                            startActivity(intent);
                            //finish();
                        } else{
                            finish();
                        }
                        break;
                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        init();
        setRecycler();
    }

    private void setRecycler(){
        PatientDetailsAdapter patientDetailsAdapter = new PatientDetailsAdapter(data,this);
        rcv.setAdapter(patientDetailsAdapter);
        rcv.setHasFixedSize(true);
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
}