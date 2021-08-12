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
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Adapter.docListAdapter;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.services.DoctorListService;

import java.util.ArrayList;

public class docListFragment extends DialogFragment {
    docListFragment docDialog;
    private CharSequence date;
    public docListFragment(CharSequence selectedDate) {
        date = selectedDate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_list,container,false);
        DoctorListService service = new DoctorListService();
        RecyclerView rcv = view.findViewById(R.id.docList);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        service.getDocList(getContext()).observeForever(new Observer<ArrayList<DocData>>() {
            @Override
            public void onChanged(ArrayList<DocData> docData) {
                docListAdapter adapter = new docListAdapter(docData,date ,docDialog);
                rcv.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
        });
        return view;
    }

    public void setDialog(docListFragment docDialog) {
        this.docDialog = docDialog;
    }
}
