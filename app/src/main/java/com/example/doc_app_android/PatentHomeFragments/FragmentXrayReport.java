package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doc_app_android.Adapter.checkupHistoryAdapter;
import com.example.doc_app_android.HomeFragments.ProfileFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.databinding.ActivityHomeBinding;
import com.example.doc_app_android.databinding.FragmentXrayReportBinding;
import com.example.doc_app_android.view_model.FragmentChkHstryViewModel;
import com.example.doc_app_android.view_model.FragmentXrayScanViewModel;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentXrayReport extends Fragment {

    public FragmentXrayReport() {
        // Required empty public constructor
    }

    public static FragmentXrayReport newInstance() {
        FragmentXrayReport fragment = new FragmentXrayReport();
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
    private FragmentXrayScanViewModel xray_model;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_xray_report,null,false);
        binding.setLifecycleOwner(this);
        desc_model = new ViewModelProvider(requireActivity()).get(FragmentChkHstryViewModel.class);
        xray_model = new ViewModelProvider(requireActivity()).get(FragmentXrayScanViewModel.class);
        xray_model.get_XrayReport_data().observe(getViewLifecycleOwner(), new Observer<Xray_data>() {
            @Override
            public void onChanged(Xray_data xray_data) {
                binding.tvXrayIDValue.setText(xray_data.getXray_ID());
                binding.tvDateVal.setText(xray_data.getDate());
                binding.tvTimeVal.setText(xray_data.getTime());
                binding.tvCategoryVal.setText(xray_data.getCategory());
            }
        });
        desc_model.get_XrayReport_desc().observe(getViewLifecycleOwner(), new Observer<CkpHstryData>() {
            @Override
            public void onChanged(CkpHstryData ckpHstryData) {
                binding.getDate.setText(ckpHstryData.getDate());
                binding.textViewDesc.setText(ckpHstryData.getTextarea());
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().remove(FragmentXrayReport.this).commit();
            }
        });
        return binding.getRoot();
    }
}