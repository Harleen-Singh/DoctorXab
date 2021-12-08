package com.example.doc_app_android.data_model;

public class MedicineList {
    String meds_name, quantity;

    public MedicineList(String meds_name, String quantity) {
        this.meds_name = meds_name;
        this.quantity = quantity;
    }

    public String getMeds_name() {
        return meds_name;
    }

    public void setMeds_name(String meds_name) {
        this.meds_name = meds_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
