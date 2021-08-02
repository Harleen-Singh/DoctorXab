package com.example.doc_app_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.DoctorHomeFragments.CheckupDetailsPatient;
import com.example.doc_app_android.DoctorHomeFragments.PatientHistoryFragment;
import com.example.doc_app_android.DoctorHomeFragments.ShareFragment;
import com.example.doc_app_android.databinding.FragmentProfileBinding;
import com.example.doc_app_android.databinding.FragmentProfileEditBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentProfileEditBinding binding;
    private SharedPreferences preferences;
    private boolean isFromPatientHistory;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileEditFragment newInstance(String param1, String param2) {
        ProfileEditFragment fragment = new ProfileEditFragment();
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_profile_edit, container, false);
        binding.setLifecycleOwner(this);
        preferences = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        isFromPatientHistory = preferences.getBoolean("isFromPatientHistory", false);


        binding.profileBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requireActivity().getSupportFragmentManager().popBackStack();
//                if (isFromPatientHistory) {
//                    requireActivity().getSupportFragmentManager().popBackStack();
////                    requireActivity().getSupportFragmentManager().beginTransaction().remove(ProfileEditFragment.this).commit();
////                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container, new PatientHistoryFragment()).commit();
//                } else {
//                    requireActivity().getSupportFragmentManager().popBackStack();
////                    requireActivity().getSupportFragmentManager().beginTransaction().remove(ProfileEditFragment.this).commit();
////                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container, new CheckupDetailsPatient()).commit();
//                }

            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}