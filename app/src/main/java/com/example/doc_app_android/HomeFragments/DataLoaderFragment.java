package com.example.doc_app_android.HomeFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.PatientDetails;
import com.example.doc_app_android.Adapter.PatientDetailsAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.FragmentDataLoaderBinding;
import com.example.doc_app_android.view_model.DataLoaderViewModel;
import com.example.doc_app_android.view_model.ProfileViewModel;

import java.util.ArrayList;
import java.util.Objects;


public class DataLoaderFragment extends Fragment {

    private RecyclerView rcv ;
    private ArrayList<PatientDetails> data1;
    private static String id;
    private DataLoaderViewModel dataLoaderViewModel;
    private PatientDetailsAdapter patientDetailsAdapter;
    private DataLoaderFragment fragment;


    public DataLoaderFragment() {
        // Required empty public constructor
    }


    public static DataLoaderFragment newInstance(String problemId) {
        DataLoaderFragment fragment = new DataLoaderFragment();
        id = problemId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDataLoaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_loader, container, false);
        patientDetailsAdapter = new PatientDetailsAdapter(getContext());
        binding.detailsRcv.setAdapter(patientDetailsAdapter);
        binding.dataLoadingProgressBar.setVisibility(View.VISIBLE);
        dataLoaderViewModel = new ViewModelProvider(requireActivity()).get(DataLoaderViewModel.class);
        dataLoaderViewModel.getPatientListForDoctorHome(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<ProfileData>>() {
            @Override
            public void onChanged(ArrayList<ProfileData> profileData) {
                patientDetailsAdapter.setdata(profileData);
                patientDetailsAdapter.notifyDataSetChanged();
                binding.dataLoadingProgressBar.setVisibility(View.GONE);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

}