package com.example.doc_app_android.DoctorHomeFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.ShareAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.databinding.FragmentShareBinding;
import com.example.doc_app_android.view_model.DataLoaderViewModel;
import com.example.doc_app_android.view_model.DoctorShareViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentShareBinding binding;
    private DoctorShareViewModel model;
    private SharedPreferences preferences;
    private boolean isFromPatientHistory;
    private int patientId;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareFragment newInstance(String param1, String param2) {
        ShareFragment fragment = new ShareFragment();
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
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_share, container, false);
        binding.setLifecycleOwner(this);


        preferences = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isFromPatientHistory = preferences.getBoolean("isFromPatientHistory", false);

        ShareAdapter shareAdapter = new ShareAdapter(requireContext());
        binding.shareAdapter.setAdapter(shareAdapter);

        model = new ViewModelProvider(requireActivity()).get(DoctorShareViewModel.class);
        binding.shareProgressBar.setVisibility(View.VISIBLE);

        model.getDoctorList().observe(getViewLifecycleOwner(), new Observer<ArrayList<DocData>>() {
            @Override
            public void onChanged(ArrayList<DocData> docData) {
                shareAdapter.setData(docData);
                shareAdapter.notifyDataSetChanged();
                binding.shareProgressBar.setVisibility(View.GONE);
            }
        });

        binding.shareBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();

            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}