package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.databinding.FragmentXrayReportBinding;
import com.example.doc_app_android.view_model.FragmentChkHstryViewModel;

public class FragmentXrayReport extends Fragment {
    private static final String XRAYID = "XrayID";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String CATEGORY = "category";
    private static final String REPORTDATE = "reportDate";
    private static final String REPORTDATA = "reportData";
    private static final String IMAGEURL = "imageURL";
    private static final String REPORTID = "reportData";
    private Xray_data data;
    public FragmentXrayReport() {
        // Required empty public constructor
    }

    public static FragmentXrayReport newInstance(Xray_data xray_data) {
        FragmentXrayReport fragment = new FragmentXrayReport();
        Bundle args = new Bundle();
        args.putString(XRAYID,xray_data.getXray_ID());
        args.putString(DATE,xray_data.getDate());
        args.putString(TIME,xray_data.getTime());
        args.putString(CATEGORY,xray_data.getCategory());
        args.putString(REPORTID,xray_data.getReportId());
        args.putString(REPORTDATE,xray_data.getReportDate());
        args.putString(REPORTDATA,xray_data.getReportData());
        args.putString(IMAGEURL,xray_data.getImageUrl());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    FragmentXrayReportBinding binding;
    public FragmentChkHstryViewModel desc_model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            String xrayid, date,time,category,reportdate ,reportID , reportdata,imageURL;
            xrayid = getArguments().getString(XRAYID);
            date = getArguments().getString(DATE);
            time = getArguments().getString(TIME);
            category = getArguments().getString(CATEGORY);
            reportdate = getArguments().getString(REPORTDATE);
            reportdata = getArguments().getString(REPORTDATA);
            reportID = getArguments().getString(REPORTID);
            imageURL = getArguments().getString(IMAGEURL);
            data = new Xray_data(xrayid,category,date,time,imageURL,reportID,reportdate,reportdata);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_xray_report,null,false);
        binding.setLifecycleOwner(this);
        binding.setXRayData(data);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().remove(FragmentXrayReport.this).commit();
            }
        });
        return binding.getRoot();
    }
}