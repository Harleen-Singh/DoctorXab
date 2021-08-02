package com.example.doc_app_android.DoctorHomeFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.ReportData;
import com.example.doc_app_android.PatentHomeFragments.AppointmentsFragment;
import com.example.doc_app_android.ProfileEditFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentCheckupDetailsPatientBinding;
import com.example.doc_app_android.view_model.ProfileViewModel;
import com.example.doc_app_android.view_model.ReportDataViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ReportDataViewModel reportDataViewModel ;
    private boolean isReportEdited = false;
    private ReportData reportData;

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
        binding.addAboutCheckupTv.setEnabled(false);

        reportDataViewModel = new ViewModelProvider(requireActivity()).get(ReportDataViewModel.class);

        reportDataViewModel.getReportData().observe(getViewLifecycleOwner(), new Observer<ReportData>() {
            @Override
            public void onChanged(ReportData reportData) {
                String date = "DATE: " + reportData.getDate();
                String data = reportData.getData();
                if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(data)) {
                    binding.addAboutCheckupDate.setText(date);
                    binding.addAboutCheckupTv.setText(data);
                }

            }
        });

        binding.addAboutCheckupTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                binding.ckscrollable.requestDisallowInterceptTouchEvent(true);
                return false;
            }


        });



        binding.addAboutCheckEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.addAboutCheckupTv.setEnabled(true);
                binding.addAboutCheckupTv.setScrollContainer(true);
                isReportEdited = true;
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {

            String name = bundle.getString("name");
            String image = bundle.getString("image");
            String age = bundle.getString("age");

            preferences = getContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("AddName", name);
            editor.putString("AddAge", age);
            editor.putString("AddImage", image);
            editor.apply();

            Picasso.get()
                    .load(image)
                    .placeholder(R.drawable.doctor_profile_image)
                    .into(binding.checkupDetailsProfileImage);
            binding.checkupDetailsDoctorName.setText(name);
            binding.checkupDetailsAge.setText(age);
        } else {

            preferences = getContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
            String name = preferences.getString("patientInfoName", "");
            String age = preferences.getString("patientInfoAge", "");
            String image = preferences.getString("patientInfoImage", "");
            binding.checkupDetailsDoctorName.setText(name);
            binding.checkupDetailsAge.setText(age);
            Picasso.get()
                    .load(image)
                    .placeholder(R.drawable.doctor_profile_image)
                    .into(binding.checkupDetailsProfileImage);
        }



        binding.checkupDetailsShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                requireActivity().getSupportFragmentManager().popBackStack();
                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, new ShareFragment()).setReorderingAllowed(true).addToBackStack("share").commit();
            }
        });

        binding.checkupDetailsEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, new ProfileEditFragment()).setReorderingAllowed(true).addToBackStack("editProf").commit();

            }
        });

        binding.checkupDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
                //requireActivity().getSupportFragmentManager().beginTransaction().remove(CheckupDetailsPatient.this).commit();

            }
        });

        binding.addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, new AppointmentsFragment()).setReorderingAllowed(true).addToBackStack("appointment").commit();

            }
        });

        binding.addAboutCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkupDetailsChangeableTv.setText("ADD ABOUT CHECKUP");
                binding.addAboutCheckup.setVisibility(View.GONE);

                if (binding.checkupDetailsContainerXrayScan.getVisibility() == View.VISIBLE) {
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
                binding.addAboutCheckupTv.setEnabled(false);

                if(isReportEdited){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new Date());
                    String data = binding.addAboutCheckupTv.getText().toString();
                    reportData = new ReportData(date, data);

                    reportDataViewModel.updateReportData(reportData);
                }


            }
        });

        binding.addXrayScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.checkupDetailsChangeableTv.setText("ADD X-RAY/SCAN");
                if (binding.checkupDetailsContainerAboutCheckup.getVisibility() == View.VISIBLE) {
                    binding.checkupDetailsContainerAboutCheckup.setVisibility(View.GONE);
                }
                binding.addXrayScan.setVisibility(View.GONE);

                if (binding.checkupDetailsContainerAboutCheckup.getVisibility() == View.VISIBLE) {
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