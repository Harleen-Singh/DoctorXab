package com.example.doc_app_android.DoctorHomeFragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.PatentHomeFragments.AppointmentsFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentPatientInfoAppointmentBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientInfoAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientInfoAppointmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentPatientInfoAppointmentBinding binding;

    public PatientInfoAppointmentFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PatientInfoAppointmentFragment newInstance() {

        return new PatientInfoAppointmentFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_patient_info_appointment, container, false);
        binding.setLifecycleOwner(this);

        binding.pdProgress.setVisibility(View.VISIBLE);


        Bundle bundle = new Bundle();
        bundle.putBoolean("isPatientInfoAppointment", true);

        AppointmentsFragment appointmentsFragment = new AppointmentsFragment();
        appointmentsFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.patient_calendar_fragment_container, appointmentsFragment).setReorderingAllowed(true).commit();
        binding.pdProgress.setVisibility(View.GONE);


        binding.editCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putBoolean("isPatientInfoAppointment",true);
                DoctorAppointmentsFragment doctorAppointmentsFragment = new DoctorAppointmentsFragment();
                doctorAppointmentsFragment.setArguments(bundle1);
                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentHome_container, doctorAppointmentsFragment).addToBackStack("name9").commit();

            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}