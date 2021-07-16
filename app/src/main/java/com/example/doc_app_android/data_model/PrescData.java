package com.example.doc_app_android.data_model;

import java.util.ArrayList;

public class PrescData {
private  String date;
private ArrayList<Pat_prescription> prescText;

    public PrescData(String date, ArrayList<Pat_prescription> prescText) {
        this.date = date;
        this.prescText = prescText;
    }

    public ArrayList<Pat_prescription> getPrescText() {
        return prescText;
    }

    public void setPrescText(ArrayList<Pat_prescription> prescText) {
        this.prescText = prescText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
