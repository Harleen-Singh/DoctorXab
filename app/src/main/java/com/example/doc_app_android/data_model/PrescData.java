package com.example.doc_app_android.data_model;

import java.util.ArrayList;

public class PrescData {
private  String date,prescID;
private ArrayList<Pat_prescription> prescText;

    public PrescData(String date, String prescID, ArrayList<Pat_prescription> prescText) {
        this.date = date;
        this.prescID = prescID;
        this.prescText = prescText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrescID() {
        return prescID;
    }

    public void setPrescID(String prescID) {
        this.prescID = prescID;
    }

    public ArrayList<Pat_prescription> getPrescText() {
        return prescText;
    }

    public void setPrescText(ArrayList<Pat_prescription> prescText) {
        this.prescText = prescText;
    }
}
