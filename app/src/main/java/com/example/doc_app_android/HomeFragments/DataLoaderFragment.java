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
//        ((SimpleItemAnimator) Objects.requireNonNull(binding.detailsRcv.getItemAnimator())).setSupportsChangeAnimations(false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.detailsRcv.setLayoutManager(linearLayoutManager);
        binding.dataLoadingProgressBar.setVisibility(View.VISIBLE);
        dataLoaderViewModel = new ViewModelProvider(requireActivity()).get(DataLoaderViewModel.class);
        dataLoaderViewModel.getPatientListForDoctorHome(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<ProfileData>>() {
            @Override
            public void onChanged(ArrayList<ProfileData> profileData) {
                patientDetailsAdapter.setdata(profileData);
                patientDetailsAdapter.notifyDataSetChanged();
//                if(profileData.isEmpty()){
//
//                }
                binding.dataLoadingProgressBar.setVisibility(View.GONE);
            }
        });





        // Inflate the layout for this fragment
        return binding.getRoot();
    }

//    private void setRecycler(FragmentDataLoaderBinding binding){
//
//        binding.detailsRcv.setAdapter(patientDetailsAdapter);
//        ((SimpleItemAnimator) Objects.requireNonNull(binding.detailsRcv.getItemAnimator())).setSupportsChangeAnimations(false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.detailsRcv.setLayoutManager(linearLayoutManager);
//    }
//
//    private void init(){
//        data1 = new ArrayList<>();
//
//        data1.add(new PatientDetails("John Cena", "01-01-2021", "21", "OPERATION", "New York"));
//        data1.add(new PatientDetails("Harry Potter", "02-01-2021", "21", "OPERATION", "United Kingdom"));
//        data1.add(new PatientDetails("Ed Sheeran", "03-01-2021", "31", "OPERATION", "South Yorkshire"));
//        data1.add(new PatientDetails("Virat Kohli", "04-01-2021", "32", "OPERATION", "New Delhi"));
//        data1.add(new PatientDetails("Ms Dhoni", "05-01-2021", "40", "OPERATION", "Jharkhand"));
//        data1.add(new PatientDetails("Hg Wells", "06-01-2021", "78", "OPERATION", "Southern California"));
//        data1.add(new PatientDetails("Alex Rovanja", "07-01-2021", "21", "OPERATION", "London"));
//        data1.add(new PatientDetails("Johnny Cash", "08-01-2021", "93", "OPERATION", "New Jersey"));
//        data1.add(new PatientDetails("Ankita Thakur", "09-01-2021", "23", "OPERATION", "Punjab"));
//        data1.add(new PatientDetails("Harleen Singh", "10-01-2021", "24", "OPERATION", "Punjab"));
//
//    }
}