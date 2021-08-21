package com.example.doc_app_android.DoctorHomeFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.doc_app_android.Adapter.PatientDetailsAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.ActivityHomeBinding;
import com.example.doc_app_android.databinding.FragmentDataLoaderBinding;
import com.example.doc_app_android.view_model.DataLoaderViewModel;

import java.util.ArrayList;


public class DataLoaderFragment extends Fragment {

    private RecyclerView rcv;
    private ArrayList<ProfileData> data1 = new ArrayList<>();
    private static String id;
    private DataLoaderViewModel dataLoaderViewModel;
    private PatientDetailsAdapter patientDetailsAdapter;
    private DataLoaderFragment fragment;
    private FragmentDataLoaderBinding binding;
    private ActivityHomeBinding activityHomeBinding;
    public String query_text = "";


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_loader, container, false);
        binding.setLifecycleOwner(this);
        patientDetailsAdapter = new PatientDetailsAdapter(getContext());
        binding.detailsRcv.setAdapter(patientDetailsAdapter);
        binding.dataLoadingProgressBar.setVisibility(View.VISIBLE);
        dataLoaderViewModel = new ViewModelProvider(requireActivity()).get(DataLoaderViewModel.class);
        dataLoaderViewModel.getPatientListForDoctorHome(id).observe(getViewLifecycleOwner(), new Observer<ArrayList<ProfileData>>() {
            @Override
            public void onChanged(ArrayList<ProfileData> profileData) {
                patientDetailsAdapter.setdata(profileData);
                patientDetailsAdapter.notifyDataSetChanged();
                data1 = profileData;
                binding.dataLoadingProgressBar.setVisibility(View.GONE);
                if (profileData.isEmpty()) {
                    if (binding.dataFragmentContainer != null) {
                        binding.dataFragmentContainer.setBackground(AppCompatResources.getDrawable(requireContext(), R.drawable.no_data));
                    } else {
                        Log.d("Testing", "binding.dataFragmentContainer is null");
                    }
                }
            }
        });

        EditText view = requireActivity().findViewById(R.id.edit_search);
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TextWatcher", "Entered Text:"  + s.toString());
                patientDetailsAdapter.getFilter().filter(s.toString());

            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();
    }



}