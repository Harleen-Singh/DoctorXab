package com.example.doc_app_android.data_model;

public class RegistrationData {

    private String userName, email, password;
    private String id, receivedUserName, name, age, state, gender, image, receivedEmail, address, phoneNumber;
    private boolean receivedIsDoctor, receivedIsPatient;
    private String sendingName, sendingContact, sendingState, sendingAddress, sendingAge, sendingGender;



    public RegistrationData(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public RegistrationData(String id, String receivedUserName, String name, String age, String state, String gender, String image, String receivedEmail, String address, String phoneNumber, boolean isDoctor, boolean receivedIsPatient) {
        this.id = id;
        this.receivedUserName = receivedUserName;
        this.name = name;
        this.age = age;
        this.state = state;
        this.gender = gender;
        this.image = image;
        this.receivedEmail = receivedEmail;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.receivedIsDoctor = isDoctor;
        this.receivedIsPatient = receivedIsPatient;
    }

    public RegistrationData(String sendingName, String sendingContact, String sendingState, String sendingAddress, String sendingAge, String sendingGender) {
        this.sendingName = sendingName;
        this.sendingContact = sendingContact;
        this.sendingState = sendingState;
        this.sendingAddress = sendingAddress;
        this.sendingAge = sendingAge;
        this.sendingGender = sendingGender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceivedUserName() {
        return receivedUserName;
    }

    public void setReceivedUserName(String receivedUserName) {
        this.receivedUserName = receivedUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReceivedEmail() {
        return receivedEmail;
    }

    public void setReceivedEmail(String receivedEmail) {
        this.receivedEmail = receivedEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isReceivedIsDoctor() {
        return receivedIsDoctor;
    }

    public void setReceivedIsDoctor(boolean receivedIsDoctor) {
        this.receivedIsDoctor = receivedIsDoctor;
    }

    public boolean isReceivedIsPatient() {
        return receivedIsPatient;
    }

    public void setReceivedIsPatient(boolean receivedIsPatient) {
        this.receivedIsPatient = receivedIsPatient;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSendingName() {
        return sendingName;
    }

    public void setSendingName(String sendingName) {
        this.sendingName = sendingName;
    }

    public String getSendingContact() {
        return sendingContact;
    }

    public void setSendingContact(String sendingContact) {
        this.sendingContact = sendingContact;
    }

    public String getSendingState() {
        return sendingState;
    }

    public void setSendingState(String sendingState) {
        this.sendingState = sendingState;
    }

    public String getSendingAddress() {
        return sendingAddress;
    }

    public void setSendingAddress(String sendingAddress) {
        this.sendingAddress = sendingAddress;
    }

    public String getSendingAge() {
        return sendingAge;
    }

    public void setSendingAge(String sendingAge) {
        this.sendingAge = sendingAge;
    }

    public String getSendingGender() {
        return sendingGender;
    }

    public void setSendingGender(String sendingGender) {
        this.sendingGender = sendingGender;
    }
}
