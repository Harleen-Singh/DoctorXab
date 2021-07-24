package com.example.doc_app_android.data_model;

public class AppointmentData {
    private String id, date, patient_id , doctor_id ;

    public AppointmentData(String id, String date, String patient_id, String doctor_id) {
        this.id = id;
        this.date = date;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }
}
