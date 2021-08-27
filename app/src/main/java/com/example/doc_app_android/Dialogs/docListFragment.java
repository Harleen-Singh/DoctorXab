package com.example.doc_app_android.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Adapter.docListAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.services.DoctorListService;
import com.example.doc_app_android.view_model.Register_view_model;

import java.util.ArrayList;

public class docListFragment extends DialogFragment {
    docListFragment docDialog;
    private CharSequence date = null;

    public docListFragment(CharSequence selectedDate) {
        date = selectedDate;
    }

    public docListFragment() {
    }
    Register_view_model model;
    boolean duringRegisteration = false;
    public docListFragment(boolean duringRegisteration) {
        this.duringRegisteration = duringRegisteration;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_list, container, false);
        DoctorListService service = new DoctorListService();
        model = new ViewModelProvider(requireActivity()).get(Register_view_model.class);
        RecyclerView rcv = view.findViewById(R.id.docList);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        service.getDocList(getContext()).observeForever(new Observer<ArrayList<DocData>>() {
            @Override
            public void onChanged(ArrayList<DocData> docData) {
                final docListAdapter adapter = new docListAdapter(docData, date, docDialog,duringRegisteration);
                rcv.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                adapter.docName.observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        model.specialistof.setValue(s);
                    }
                });
                adapter.docId.observeForever(new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        model.docId.setValue(s);
                    }
                });
            }
        });

        return view;
    }

    public void setDialog(docListFragment docDialog) {
        this.docDialog = docDialog;
    }
}
