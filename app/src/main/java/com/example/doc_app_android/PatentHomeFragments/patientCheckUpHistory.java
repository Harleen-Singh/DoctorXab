package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.R;

public class patientCheckUpHistory extends Fragment {


    public patientCheckUpHistory() {
        // Required empty public constructor
    }

    public static patientCheckUpHistory newInstance() {
        patientCheckUpHistory fragment = new patientCheckUpHistory();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_patient_check_up_history, container, false);



        return root;
    }
}