package com.example.doc_app_android.data_model;

public class CkpHstryData {
    private String date , textarea;

    public CkpHstryData(String date, String textarea) {
        this.date = date;
        this.textarea = textarea;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTextarea() {
        return textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }
}
