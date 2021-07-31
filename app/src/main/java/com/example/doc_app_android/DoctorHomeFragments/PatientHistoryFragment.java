package com.example.doc_app_android.DoctorHomeFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.FilterRCVadapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.FilterData;
import com.example.doc_app_android.databinding.FragmentPatientCheckUpHistoryBinding;
import com.example.doc_app_android.databinding.FragmentPatientHistoryBinding;
import com.example.doc_app_android.view_model.PatientChechkUpHistoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientHistoryFragment extends Fragment {

    private FilterRCVadapter filterRCVadapter;
    private PatientChechkUpHistoryModel model;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientHistoryFragment newInstance(String param1, String param2) {
        PatientHistoryFragment fragment = new PatientHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // This particular line will hide the default toolbar of the Home Activity when fragment gets opened.
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onStop() {
        super.onStop();
        // This particular line will show the default toolbar of the Home Activity on Home Page when fragment gets closed.
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPatientHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient_history, container, false);
        binding.setLifecycleOwner(this);
        // Inflate the layout for this fragment

        assert getArguments() != null;
        String name = getArguments().getString("name");
        String age = getArguments().getString("age");
        String image = getArguments().getString("image");

        binding.patientInfoNameTv.setText(name);
        binding.patientInfoAgeLabel.setText(age);
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.doctor_profile_image)
                .into(binding.patientInfoProfileImage);

        filterRCVadapter = new FilterRCVadapter(getContext(), true);
        binding.patientHistoryLabelsRcv.setAdapter(filterRCVadapter);
        model = new ViewModelProvider(requireActivity()).get(PatientChechkUpHistoryModel.class);
        model.getData().observe(getViewLifecycleOwner(), new Observer<ArrayList<FilterData>>() {
            @Override
            public void onChanged(ArrayList<FilterData> filterData) {
                filterRCVadapter.setFilter(filterData);
                filterRCVadapter.notifyDataSetChanged();
            }
        });

        binding.patientInfoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(PatientHistoryFragment.this).commit();
            }
        });
        return binding.getRoot();
    }
}