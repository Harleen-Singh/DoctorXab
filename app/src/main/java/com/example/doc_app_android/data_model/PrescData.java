package com.example.doc_app_android.data_model;

public class PrescData {
private  String prescText,date;
    public PrescData(String prescText, String date) {
        this.prescText = prescText;
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrescText() {
        return prescText;
    }

    public void setPrescText(String prescText) {
        this.prescText = prescText;
    }
}
