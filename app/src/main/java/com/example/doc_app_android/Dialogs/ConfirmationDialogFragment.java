package com.example.doc_app_android.Dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.databinding.FragmentConfirmationDialogBinding;
import com.example.doc_app_android.services.getAppointmentService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmationDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmationDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentConfirmationDialogBinding binding;
    private String text;
    private Context context;
    private DocData newData;
    private boolean fromNotification = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfirmationDialogFragment() {
        // Required empty public constructor
    }

    public ConfirmationDialogFragment(String text, Context context, DocData newData, boolean fromNotification) {
        this.text = text;
        this.context = context;
        this.newData = newData;
        this.fromNotification = fromNotification;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmationDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmationDialogFragment newInstance(String param1, String param2) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_dialog, container, false);

        binding.validationMessage.setText(text);

        binding.Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAppointmentService service = new getAppointmentService();
                ProgressDialog dialog1 = new ProgressDialog(context, R.style.AlertDialog);
                alertDialogLogin(dialog1, "Processing");
                service.shareReport(context, dialog1, newData);
                dismiss();
                Log.e("TAG", "displayConfirmationDialog: confirmed");

            }
        });

        binding.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return binding.getRoot();
    }

    public void alertDialogLogin(ProgressDialog progressDialog, String msg) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }
}