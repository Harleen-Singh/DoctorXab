package com.example.doc_app_android.Dialogs;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.databinding.FragmentAppointmentConfirmationDialogBinding;
import com.example.doc_app_android.services.notiService;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentConfirmationDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentConfirmationDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAppointmentConfirmationDialogBinding binding;
    private NotiData notificationData;
    private int position;
    private notiService service = new notiService();
    private ArrayList<NotiData> notiData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppointmentConfirmationDialogFragment() {
        // Required empty public constructor
    }

    public AppointmentConfirmationDialogFragment(NotiData notificationData, int position, ArrayList<NotiData> notiData) {
        this.notificationData = notificationData;
        this.position = position;
        this.notiData = new ArrayList<>(notiData);
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentConfirmationDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentConfirmationDialogFragment newInstance(String param1, String param2) {
        AppointmentConfirmationDialogFragment fragment = new AppointmentConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appointment_confirmation_dialog, container, false);

        binding.validationMessage.setText(notificationData.getData());

        binding.Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.acceptReq(notificationData, requireContext());
                notiData.get(position).setStatus("1");
                dismiss();
            }
        });

        binding.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.rejectRequest(notificationData, requireContext());
                notiData.get(position).setStatus("1");
                dismiss();
            }
        });


        return binding.getRoot();
    }
}