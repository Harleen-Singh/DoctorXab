package com.example.doc_app_android.DoctorHomeFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.doc_app_android.AppointmentV2;
import com.example.doc_app_android.PatentHomeFragments.AppointmentsFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentScheduleBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    private ImageView back_button;
    private FragmentScheduleBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }


    @Override
    public void onStop() {
        super.onStop();
        // This particular line will show the default toolbar of the Home Activity on Home Page when fragment gets closed.
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false);
        binding.setLifecycleOwner(this);
        binding.scheduleProgress.setVisibility(View.VISIBLE);

        AppointmentV2 appointmentV2 = new AppointmentV2();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.scheduleLayoutContainer, appointmentV2).setReorderingAllowed(true).commit();

        binding.scheduleProgress.setVisibility(View.GONE);


        binding.scheduleBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });




        // Inflate the layout for this fragment
        return binding.getRoot();

    }
}