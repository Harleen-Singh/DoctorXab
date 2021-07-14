package com.example.doc_app_android.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.services.chkHstryService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentChkHstryViewModel extends AndroidViewModel {
    chkHstryService service = new chkHstryService();
    public FragmentChkHstryViewModel(@NonNull @NotNull Application application) {
        super(application);
    }
    public LiveData<ArrayList<CkpHstryData>> getChkHstryData(){
        return service.getCkpHstryData();
    }
}
