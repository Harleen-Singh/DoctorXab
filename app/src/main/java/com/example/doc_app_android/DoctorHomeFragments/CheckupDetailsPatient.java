package com.example.doc_app_android.DoctorHomeFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentCheckupDetailsPatientBinding;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckupDetailsPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckupDetailsPatient extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckupDetailsPatient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckupDetailsPatient.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckupDetailsPatient newInstance(String param1, String param2) {
        CheckupDetailsPatient fragment = new CheckupDetailsPatient();
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
        // Inflate the layout for this fragment
        FragmentCheckupDetailsPatientBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkup_details_patient, container, false);
        binding.setLifecycleOwner(this);

        assert getArguments() != null;
        String name = getArguments().getString("name");
        String image = getArguments().getString("image");
        String age = getArguments().getString("age");

        Picasso.get()
                .load(image)
                .placeholder(R.drawable.doctor_profile_image)
                .into(binding.checkupDetailsProfileImage);
        binding.checkupDetailsDoctorName.setText(name);
        binding.checkupDetailsAge.setText(age);

        binding.checkupDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(CheckupDetailsPatient.this).commit();
            }
        });

        binding.addAboutCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkupDetailsChangeableTv.setText("ADD ABOUT CHECKUP");
                binding.addAboutCheckup.setVisibility(View.GONE);

                if(binding.checkupDetailsContainerXrayScan.getVisibility() == View.VISIBLE){
                    binding.checkupDetailsContainerXrayScan.setVisibility(View.GONE);
                    binding.addXrayScan.setVisibility(View.VISIBLE);
                }
                binding.addAboutCheckupLabel.setVisibility(View.VISIBLE);
                binding.checkupDetailsContainerAboutCheckup.setVisibility(View.VISIBLE);
                binding.checkupDetailsSaveButton.setVisibility(View.VISIBLE);



            }
        });

        binding.checkupDetailsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addAboutCheckupLabel.setVisibility(View.GONE);
                binding.checkupDetailsContainerAboutCheckup.setVisibility(View.GONE);
                binding.checkupDetailsSaveButton.setVisibility(View.GONE);
                binding.checkupDetailsContainerXrayScan.setVisibility(View.GONE);

                binding.addAboutCheckup.setVisibility(View.VISIBLE);
                binding.addXrayScan.setVisibility(View.VISIBLE);



            }
        });

        binding.addXrayScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkupDetailsChangeableTv.setText("ADD X-RAY/SCAN");
                if(binding.checkupDetailsContainerAboutCheckup.getVisibility() == View.VISIBLE){
                    binding.checkupDetailsContainerAboutCheckup.setVisibility(View.GONE);
                }
                binding.addXrayScan.setVisibility(View.GONE);

                if(binding.checkupDetailsContainerAboutCheckup.getVisibility() == View.VISIBLE){
                    binding.checkupDetailsContainerAboutCheckup.setVisibility(View.GONE);
                    binding.addAboutCheckup.setVisibility(View.VISIBLE);
                }

                binding.addAboutCheckup.setVisibility(View.VISIBLE);
                binding.addAboutCheckupLabel.setVisibility(View.VISIBLE);
                binding.checkupDetailsContainerXrayScan.setVisibility(View.VISIBLE);
                binding.checkupDetailsSaveButton.setVisibility(View.VISIBLE);


            }
        });


        return binding.getRoot();
    }
}