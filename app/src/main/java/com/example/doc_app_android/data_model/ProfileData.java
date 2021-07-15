package com.example.doc_app_android.data_model;

public class ProfileData {

    private int id;
    private String userName, email, name;
    boolean isDoc, isPatient;
    private int department;
    private int doctor, problem;
    private String phoneNumber, address, image;
    private int age;

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

    public ProfileData(int id, String userName, String email, String phoneNumber, String address, String image, int age) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.image = image;
        this.age = age;
    }

    public ProfileData(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDoc(boolean doc) {
        isDoc = doc;
    }

    public void setPatient(boolean patient) {
        isPatient = patient;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
