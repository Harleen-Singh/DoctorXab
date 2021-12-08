package com.example.doc_app_android.DoctorHomeFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doc_app_android.Adapter.MedicineAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.MedicineList;
import com.example.doc_app_android.databinding.FragmentWritePrescriptionBinding;
import com.example.doc_app_android.view_model.WritePrescriptionViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WritePrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WritePrescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentWritePrescriptionBinding binding;
    private MedicineAdapter adapter;
    private ArrayList<MedicineList> medicineList;
    private int patientId;
    private WritePrescriptionViewModel model;
    private MedicineList list;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WritePrescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WritePrescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WritePrescriptionFragment newInstance(String param1, String param2) {
        WritePrescriptionFragment fragment = new WritePrescriptionFragment();
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
    public void onResume() {
        super.onResume();
        // This particular line will hide the default toolbar of the Home Activity when fragment gets opened.
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }


    @Override
    public void onStop() {
        super.onStop();
        // This particular line will show the default toolbar of the Home Activity on Home Page when fragment gets closed.
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_prescription, container, false);
        binding.setLifecycleOwner(this);
        medicineList = new ArrayList<>();
//        medicineList.add("");
        adapter = new MedicineAdapter(medicineList, requireContext());
        binding.prescriptionList.setAdapter(adapter);
        model = new ViewModelProvider(requireActivity()).get(WritePrescriptionViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            patientId = bundle.getInt("patient_id", patientId);
        }


        binding.moreMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.editPrescription.getText().toString()) && !TextUtils.isEmpty(binding.editMedicineQuantity.getText().toString())) {
                    list = new MedicineList(binding.editPrescription.getText().toString(), binding.editMedicineQuantity.getText().toString());
                    medicineList.add(list);
                    adapter.setData(medicineList);
                    adapter.notifyDataSetChanged();
                    binding.editPrescription.setText("");
                    binding.editMedicineQuantity.setText("");
                } else if (TextUtils.isEmpty(binding.editPrescription.getText().toString()) && TextUtils.isEmpty(binding.editMedicineQuantity.getText().toString())) {
                    Toast.makeText(requireContext(), "Please add the Medicine and its Quantity.", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(binding.editPrescription.getText().toString())) {
                    Toast.makeText(requireContext(), "Please add the Medicine.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(requireContext(), "Please add the Quantity.", Toast.LENGTH_SHORT).show();

                }
            }
        });


        binding.sendPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(binding.editPrescription.getText().toString())) {
                    Toast.makeText(requireContext(), "Add the Medicine", Toast.LENGTH_SHORT).show();
                } else {
                    model.send_Medicine_List(adapter.getData(), patientId, requireContext(), requireActivity().getSupportFragmentManager());
                }
//                requireActivity().getSupportFragmentManager().popBackStack();

            }
        });


        binding.prescriptionBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}