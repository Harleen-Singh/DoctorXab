package com.example.doc_app_android.HomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doc_app_android.Adapter.PatientDetails;
import com.example.doc_app_android.Adapter.PatientDetailsAdapter;
import com.example.doc_app_android.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataLoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataLoaderFragment extends Fragment {

    private RecyclerView rcv ;
    private ArrayList<PatientDetails> data1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DataLoaderFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataLoaderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataLoaderFragment newInstance(String param1, String param2) {
        DataLoaderFragment fragment = new DataLoaderFragment();
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
        View view = inflater.inflate(R.layout.fragment_data_loader, container, false);
        rcv = (RecyclerView)view.findViewById(R.id.details_rcv);

        init();
        setRecycler();


        // Inflate the layout for this fragment
        return view;
    }

    private void setRecycler(){
        PatientDetailsAdapter patientDetailsAdapter = new PatientDetailsAdapter(data1,getContext());
        rcv.setAdapter(patientDetailsAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(rcv.getItemAnimator())).setSupportsChangeAnimations(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
    }

    private void init(){
        data1 = new ArrayList<>();

        data1.add(new PatientDetails("John Cena", "01-01-2021", "21", "OPERATION", "New York"));
        data1.add(new PatientDetails("Harry Potter", "02-01-2021", "21", "OPERATION", "United Kingdom"));
        data1.add(new PatientDetails("Ed Sheeran", "03-01-2021", "31", "OPERATION", "South Yorkshire"));
        data1.add(new PatientDetails("Virat Kohli", "04-01-2021", "32", "OPERATION", "New Delhi"));
        data1.add(new PatientDetails("Ms Dhoni", "05-01-2021", "40", "OPERATION", "Jharkhand"));
        data1.add(new PatientDetails("Hg Wells", "06-01-2021", "78", "OPERATION", "Southern California"));
        data1.add(new PatientDetails("Alex Rovanja", "07-01-2021", "21", "OPERATION", "London"));
        data1.add(new PatientDetails("Johnny Cash", "08-01-2021", "93", "OPERATION", "New Jersey"));
        data1.add(new PatientDetails("Ankita Thakur", "09-01-2021", "23", "OPERATION", "Punjab"));
        data1.add(new PatientDetails("Harleen Singh", "10-01-2021", "24", "OPERATION", "Punjab"));

    }
}