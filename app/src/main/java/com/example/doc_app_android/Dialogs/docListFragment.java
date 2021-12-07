package com.example.doc_app_android.Dialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private SharedPreferences preferences;
    private docListAdapter adapter;


    public docListFragment(CharSequence selectedDate) {
        date = selectedDate;
    }

    public docListFragment() {
    }

    Register_view_model model;
    private Button button;

    boolean duringRegisteration = false;
    boolean fromDataLoaderFragment = false;

    public docListFragment(boolean duringRegisteration) {
        this.duringRegisteration = duringRegisteration;
    }

    public docListFragment(boolean duringRegisteration, Button button, boolean fromDataLoaderFragment) {
        this.duringRegisteration = duringRegisteration;
        this.button = button;
        this.fromDataLoaderFragment = fromDataLoaderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doc_list, container, false);
        DoctorListService service = new DoctorListService();
        model = new ViewModelProvider(requireActivity()).get(Register_view_model.class);
        RecyclerView rcv = view.findViewById(R.id.docList);
        ConstraintLayout constraintLayout = view.findViewById(R.id.container);
        TextView label = view.findViewById(R.id.label_for_list);
        TextView no_res = view.findViewById(R.id.no_res11);
        ImageView no_res1 = view.findViewById(R.id.no_res00);
        EditText editText = view.findViewById(R.id.edit_text_for_search);
        ImageFilterButton imageFilterButton = view.findViewById(R.id.search_list);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        preferences = requireContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
        if (preferences.getBoolean("isDoc", false) && fromDataLoaderFragment) {
            label.setText("List Of Doctors");
        } else if (preferences.getBoolean("isDoc", false)) {
            label.setText("List Of Patients");
        } else {
            label.setText("List Of Doctors");
        }
        service.getDocList(getContext(), fromDataLoaderFragment).observeForever(new Observer<ArrayList<DocData>>() {
            @Override
            public void onChanged(ArrayList<DocData> docData) {
                if (!duringRegisteration) {
                    adapter = new docListAdapter(docData, date, docDialog, duringRegisteration, no_res, no_res1, rcv);
                } else {
                    adapter = new docListAdapter(docData, date, docDialog, true, no_res, no_res1, rcv, button);
                    Log.d("Testing", "During registration " + duringRegisteration);
                }
                rcv.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                label.setVisibility(View.VISIBLE);
                editText.setVisibility(View.VISIBLE);
                imageFilterButton.setVisibility(View.VISIBLE);
                rcv.setVisibility(View.VISIBLE);


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


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TextWatcher", "Entered Text:" + s.toString());
                int height = rcv.getHeight();
                int width = rcv.getWidth();
                Log.d("WH", "height: " + height + " Width: " + width);
                adapter.getFilter().filter(s.toString());

            }
        });


        return view;
    }

    public void setDialog(docListFragment docDialog) {
        this.docDialog = docDialog;
    }
}
