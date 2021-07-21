package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.checkupHistoryAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.databinding.ChkUpHistoryBinding;
import com.example.doc_app_android.databinding.FragmentPatientCheckUpHistoryBinding;
import com.example.doc_app_android.view_model.FragmentChkHstryViewModel;

import java.util.ArrayList;

public class patientCheckUpHistory extends Fragment {

    public patientCheckUpHistory() {
        // Required empty public constructor
    }

    public static patientCheckUpHistory newInstance() {
        patientCheckUpHistory fragment = new patientCheckUpHistory();
        return fragment;
    }

    public FragmentPatientCheckUpHistoryBinding binding;
    public FragmentChkHstryViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_patient_check_up_history,null,false);
        checkupHistoryAdapter adapter = new checkupHistoryAdapter();
        binding.rView.setAdapter(adapter);
        model = new ViewModelProvider(requireActivity()).get(FragmentChkHstryViewModel.class);
        binding.progressbar.setVisibility(View.VISIBLE);
        model.getChkHstryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CkpHstryData>>() {
            @Override
            public void onChanged(ArrayList<CkpHstryData> ckpHstryData) {
                Log.d("TAG", "onChanged: " + ckpHstryData);
                adapter.setData(ckpHstryData);
                adapter.notifyDataSetChanged();
                if (ckpHstryData.isEmpty()){
                    binding.background.setBackground(AppCompatResources.getDrawable(requireContext(),R.drawable.no_data));
                }
                binding.progressbar.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }
}