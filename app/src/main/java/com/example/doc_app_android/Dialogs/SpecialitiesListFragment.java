package com.example.doc_app_android.Dialogs;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.SpecialistAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.FragmentSpecialitiesListBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecialitiesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecialitiesListFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentSpecialitiesListBinding binding;
    private SpecialistAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpecialitiesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpecialitiesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpecialitiesListFragment newInstance(String param1, String param2) {
        SpecialitiesListFragment fragment = new SpecialitiesListFragment();
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_specialities_list, container, false );

        ArrayList<String> specialities = new ArrayList<>();
        specialities.add("ORTHOLOGIST");
        specialities.add("NEUROLOGIST");
        specialities.add("NEPHROLOGIST");
        specialities.add("ORTHOPAEDIC");
        specialities.add("CARDIOLOGIST");
        adapter = new SpecialistAdapter(specialities, requireContext());
        binding.specialityRcv.setAdapter(adapter);






        return binding.getRoot();
    }
}