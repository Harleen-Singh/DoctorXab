package com.example.doc_app_android.PatentHomeFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.checkupHistoryAdapter;
import com.example.doc_app_android.Adapter.prescListAdapter;
import com.example.doc_app_android.Adapter.prescListDataAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.data_model.PrescData;
import com.example.doc_app_android.databinding.FragmentPatientCheckUpHistoryBinding;
import com.example.doc_app_android.databinding.FragmentPrescriptionBinding;
import com.example.doc_app_android.databinding.ListPrescBinding;
import com.example.doc_app_android.view_model.FragmentChkHstryViewModel;
import com.example.doc_app_android.view_model.FragmentPrescViewModel;

import java.util.ArrayList;

public class FragmentPrescription extends Fragment {

    public FragmentPrescription() {
        // Required empty public constructor
    }

    public static FragmentPrescription newInstance() {
        FragmentPrescription fragment = new FragmentPrescription();
        return fragment;
    }

    public FragmentPrescriptionBinding binding;
    private ListPrescBinding listPrescBinding;
    public FragmentPrescViewModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_prescription,null,false);
        prescListAdapter adapter = new prescListAdapter();
        binding.parentRview.setAdapter(adapter);
        model = new ViewModelProvider(requireActivity()).get(FragmentPrescViewModel.class);
        ArrayList<PrescData> prescDatatemp = new ArrayList<>();
        adapter.setData(prescDatatemp);
        model.getPrescData().observe(getViewLifecycleOwner(), new Observer<ArrayList<PrescData>>() {
            @Override
            public void onChanged(ArrayList<PrescData> prescData) {
                adapter.setData(prescData);
                adapter.notifyDataSetChanged();
            }
        });
        
        return binding.getRoot();
    }
}