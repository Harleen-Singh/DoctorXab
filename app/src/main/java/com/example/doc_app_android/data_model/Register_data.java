package com.example.doc_app_android.data_model;

public class Register_data {

    private String username ,name, contact , email , specialistof , conslt , cpass , cfpass , age , gender ;
    private Boolean isDoc ;


    public Register_data(String username,String email , String cpass , String cfpass) {
        this.username = username;
        this.email = email;
        this.cpass = cpass;
        this.cfpass = cfpass;
    }
    public Register_data(String name, String contact, String age , String gender , Boolean isDoc) {
        this.name = name;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
        this.isDoc = isDoc;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialistof() {
        return specialistof;
    }

    public void setSpecialistof(String specialistof) {
        this.specialistof = specialistof;
    }

    public String getConslt() {
        return conslt;
    }

    public void setConslt(String conslt) {
        this.conslt = conslt;
    }

    public String getCpass() {
        return cpass;
    }

    public void setCpass(String cpass) {
        this.cpass = cpass;
    }

    public String getCfpass() {
        return cfpass;
    }

    public void setCfpass(String cfpass) {
        this.cfpass = cfpass;
    }

    public Boolean getDoc() {
        return isDoc;
    }

    public void setDoc(Boolean doc) {
        isDoc = doc;
    }
}
