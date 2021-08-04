package com.example.doc_app_android.data_model;

import android.graphics.Bitmap;

public class ProfileData {
    private int pateint_Id, count, department, doctor_Id, problem, age;
    private String userName, email, name, phoneNumber, address, image, state, speciality;
    boolean isDoc, isPatient, isExpanded, isRowExpanded;
    private Bitmap bitmap;

    public ProfileData(int doctor_Id, int age, String userName, String email, String name, String phoneNumber, String address, String image) {
        this.doctor_Id = doctor_Id;
        this.age = age;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.image = image;
    }

    public ProfileData(int patient_id, String speciality, String email, String name, String phoneNumber, String image, Bitmap bitmap) {
        this.pateint_Id = patient_id;
        this.speciality = speciality;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.bitmap = bitmap;
    }

    public ProfileData(int pateint_id, int count, int doctor_id, int problem, String userName, String email, String name, String phoneNumber, String image, boolean isDoc, boolean isPatient, int age, String state) {
        this.pateint_Id = pateint_id;
        this.count = count;
        this.doctor_Id = doctor_id;
        this.problem = problem;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.isDoc = isDoc;
        this.isPatient = isPatient;
        this.age = age;
        this.state = state;
        this.isExpanded = false;
        this.isRowExpanded = true;
    }



    public ProfileData(int patient_id, int count, int doctor_id, int problem, String userName, String email, boolean isDoc, boolean isPatient) {
        this.pateint_Id = patient_id;
        this.count = count;
        this.doctor_Id = doctor_id;
        this.problem = problem;
        this.userName = userName;
        this.email = email;
        this.isDoc = isDoc;
        this.isPatient = isPatient;
    }

    public ProfileData(int patient_id, String userName, String email, boolean isDoc, boolean isPatient, int department, String image, String name, String phoneNumber) {
        this.pateint_Id = patient_id;
        this.userName = userName;
        this.email = email;
        this.isDoc = isDoc;
        this.isPatient = isPatient;
        this.department = department;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public ProfileData(int patient_id, String userName, String email, boolean isDoc, boolean isPatient, int doctor_id, int problem, String image, String name, String phoneNumber ) {
        this.pateint_Id = patient_id;
        this.userName = userName;
        this.email = email;
        this.isDoc = isDoc;
        this.isPatient = isPatient;
        this.doctor_Id = doctor_id;
        this.problem = problem;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public ProfileData(int patient_id, String userName, String email, String phoneNumber, String address, String image, int age) {
        this.pateint_Id = patient_id;
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

    public boolean isRowExpanded() {
        return isRowExpanded;
    }

    public void setRowExpanded(boolean rowExpanded) {
        isRowExpanded = rowExpanded;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setState(String state) {
        this.state = state;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }



    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getCount() {
        return count;
    }

    public String getAge() {
        return String.valueOf(age);
    }

    public String getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
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



    public int getProblem() {
        return problem;
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

    public String getSpeciality() {
        return speciality;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getPateint_Id() {
        return pateint_Id;
    }

    public int getDoctor_Id() {
        return doctor_Id;
    }
}
