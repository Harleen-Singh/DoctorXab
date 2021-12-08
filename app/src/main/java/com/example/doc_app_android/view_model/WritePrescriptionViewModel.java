package com.example.doc_app_android.view_model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;

import com.example.doc_app_android.data_model.MedicineList;
import com.example.doc_app_android.services.WritePrescriptionService;

import java.util.ArrayList;

public class WritePrescriptionViewModel extends AndroidViewModel {


    private WritePrescriptionService service = new WritePrescriptionService();

    public WritePrescriptionViewModel(@NonNull Application application) {
        super(application);
    }

    public void send_Medicine_List(ArrayList<MedicineList> medicineList, int patient_id, Context context, FragmentManager fragmentManager) {
        service.send_Medicine_List(medicineList, patient_id, context, fragmentManager);
    }
}
