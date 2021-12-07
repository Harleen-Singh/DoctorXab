package com.example.doc_app_android.DoctorHomeFragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doc_app_android.Adapter.FilterRCVadapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.FilterData;
import com.example.doc_app_android.databinding.FragmentPatientHistoryBinding;
import com.example.doc_app_android.view_model.PatientChechkUpHistoryModel;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientHistoryFragment extends Fragment {

    private FilterRCVadapter filterRCVadapter;
    private PatientChechkUpHistoryModel model;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String required_id = "";
    private String patientId;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientHistoryFragment() {
        // Required empty public constructor
        Log.d("Fragment", "Constructor Called");

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
        Log.d("Fragment", "newInstance Called");

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        Log.d("Fragment", "onPause Called");


    }

    @Override
    public void onResume() {
        super.onResume();
        // This particular line will hide the default toolbar of the Home Activity when fragment gets opened.
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        Log.d("Fragment", "onResume Called");


    }


    @Override
    public void onStop() {
        super.onStop();
        // This particular line will show the default toolbar of the Home Activity on Home Page when fragment gets closed.
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        Log.d("Fragment", "onStop Called");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPatientHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient_history, container, false);
        binding.setLifecycleOwner(this);

//        FragmentAppointmentsBinding appointmentsBinding = FragmentAppointmentsBinding.inflate(LayoutInflater.from(requireContext()));
//
//        appointmentsBinding.editCalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, new AppointmentsFragment()).setReorderingAllowed(true).addToBackStack("sharei").commit();
//            }
//        });

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();


        Bundle bundle = getArguments();

        if (bundle != null) {
            if (!TextUtils.isEmpty(bundle.getString("name")) && !TextUtils.isEmpty(bundle.getString("age")) && !TextUtils.isEmpty(bundle.getString("image"))) {
                String name = bundle.getString("name");
                String age = bundle.getString("age");
                String image = bundle.getString("image");
                required_id = bundle.getString("required_id");
                patientId = bundle.getString("patient_id");


                preferences = getContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("patientInfoName", name);
                editor.putString("patientInfoAge", age);
                editor.putString("patientInfoImage", image);
                editor.apply();


                binding.patientInfoNameTv.setText(name);
                binding.patientInfoAgeLabel.setText(age);
                Picasso.get()
                        .load(image)
                        .placeholder(R.drawable.doctor_profile_image)
                        .into(binding.patientInfoProfileImage);
            }
        }
//        } else{
//            //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//            preferences = getContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
//            String name = preferences.getString("patientInfoName", "");
//            String age = preferences.getString("patientInfoAge", "");
//            String image = preferences.getString("patientInfoImage", "");
//            binding.patientInfoNameTv.setText(name);
//            binding.patientInfoAgeLabel.setText(age);
//            Picasso.get()
//                    .load(image)
//                    .placeholder(R.drawable.doctor_profile_image)
//                    .into(binding.patientInfoProfileImage);
//        }

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

        binding.patientInfoChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                Uri phoneNumber = Uri.parse("smsto:" + getArguments().getString("mobile_number"));
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_TEXT, phoneNumber);
                intent.setData(phoneNumber);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(requireContext(), "No Application found in your device to handle the task.", Toast.LENGTH_LONG).show();
                    ;
                }
            }
        });

//        binding.patientInfoEditButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, new ProfileEditFragment()).addToBackStack("editi").commit();
//                editor = preferences.edit();
//                editor.putBoolean("isFromPatientHistory", true);
//                editor.apply();
//
//            }
//        });

        binding.patientInfoShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, new ShareFragment()).setReorderingAllowed(true).addToBackStack("sharei").commit();
                editor = preferences.edit();
                editor.putBoolean("isFromPatientHistory", true);
                editor.apply();
            }
        });

        binding.patientInfoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager().beginTransaction().remove(PatientHistoryFragment.this).commit();
            }
        });

//        binding.patientSendPrescriptionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle pres_bundle = new Bundle();
//                pres_bundle.putInt("patient_id", Integer.parseInt(patientId));
//                WritePrescriptionFragment writePrescriptionFragment = new WritePrescriptionFragment();
//                writePrescriptionFragment.setArguments(pres_bundle);
//                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, writePrescriptionFragment).setReorderingAllowed(true).addToBackStack("hist_pres").commit();
//
//            }
//        });


        return binding.getRoot();
    }
}