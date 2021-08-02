package com.example.doc_app_android.Adapter;

public class ReportData {

    private int reportId, patientId;
    private String date, data;

    public ReportData(String date, String data) {
        this.date = date;
        this.data = data;
    }

    public ReportData(int reportId, int patientId, String date, String data) {
        this.reportId = reportId;
        this.patientId = patientId;
        this.date = date;
        this.data = data;
    }

    public int getReportId() {
        return reportId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public String getData() {
        return data;
    }
}
