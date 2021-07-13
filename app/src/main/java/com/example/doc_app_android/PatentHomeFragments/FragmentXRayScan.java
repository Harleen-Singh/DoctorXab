package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.R;

public class FragmentXRayScan extends Fragment {

    public FragmentXRayScan() {
        // Required empty public constructor
    }

    public static FragmentXRayScan newInstance() {
        FragmentXRayScan fragment = new FragmentXRayScan();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_x_ray_scan, container, false);
    }
}