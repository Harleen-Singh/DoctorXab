package com.example.doc_app_android.data_model;

public class CkpHstryData {
    private String date , reportData , reportID;

    public CkpHstryData(String reportId, String date, String data ) {
        this.date = date;
        this.reportData = data;
        reportID = reportId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReportData() {
        return reportData;
    }

    public void setReportData(String reportData) {
        this.reportData = reportData;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }
}
