package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.R;

public class FragmentPrescription extends Fragment {

    public FragmentPrescription() {
        // Required empty public constructor
    }
    public static FragmentPrescription newInstance() {
        FragmentPrescription fragment = new FragmentPrescription();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prescription, container, false);
    }
}