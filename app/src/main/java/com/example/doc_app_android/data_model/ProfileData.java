package com.example.doc_app_android.data_model;

public class ProfileData {

    private int id;
    private String userName, email;
    boolean isDoc, isPatient;
    private int department;
    private int doctor, problem;

    public ProfileData(int id, String userName, String email, boolean isDoc, boolean isPatient, int department) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.isDoc = isDoc;
        this.isPatient = isPatient;
        this.department = department;
    }

    public ProfileData(int id, String userName, String email, boolean isDoc, boolean isPatient, int doctor, int problem) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.isDoc = isDoc;
        this.isPatient = isPatient;
        this.doctor = doctor;
        this.problem = problem;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isDoc() {
        return isDoc;
    }

    public boolean isPatient() {
        return isPatient;
    }

    public int getDepartment() {
        return department;
    }

    public int getDoctor() {
        return doctor;
    }

    public int getProblem() {
        return problem;
    }
}
