package com.example.doc_app_android.data_model;

import android.net.Uri;

public class ReportData {

    private String xray_id, date, time, category, body_area, report;
    private Uri uri;
    private int patientId;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public ReportData(int patientId, String xray_id, String date, String time, String category, String body_area, String report, Uri uri) {
        this.xray_id = xray_id;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.category = category;
        this.body_area = body_area;
        this.report = report;
        this.uri = uri;
    }

    public String getXray_id() {
        return xray_id;
    }

    public void setXray_id(String xray_id) {
        this.xray_id = xray_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody_area() {
        return body_area;
    }

    public void setBody_area(String body_area) {
        this.body_area = body_area;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
