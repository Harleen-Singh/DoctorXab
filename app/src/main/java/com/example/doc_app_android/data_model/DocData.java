package com.example.doc_app_android.data_model;

public class DocData {
    String docID , username, name , age, state,image, email,phone , department, patientId;

    public DocData(String name, String image, String patientId) {
        this.name = name;
        this.image = image;
        this.patientId = patientId;
    }

    public DocData(String docID, String username, String name, String age, String state, String image, String email, String phone, String department) {
        this.docID = docID;
        this.username = username;
        this.name = name;
        this.age = age;
        this.state = state;
        this.image = image;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    public DocData(String docID, String name) {
        this.docID = docID;
        this.name = name;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
