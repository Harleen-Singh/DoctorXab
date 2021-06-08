package com.example.doc_app_android.Data_model;

public class Register_data {

    private String username , contact , email , specialistof , conslt , cpass , cfpass ;
    private Boolean isDoc ;


    public Register_data(String username, String contact, String email , Boolean is_Doc) {
        this.username = username;
        this.contact = contact;
        this.email = email;
        this.isDoc = is_Doc;
    }

    public Register_data(String specialistof, String conslt, String cpass, String cfpass) {
        this.specialistof = specialistof;
        this.conslt = conslt;
        this.cpass = cpass;
        this.cfpass = cfpass;
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
