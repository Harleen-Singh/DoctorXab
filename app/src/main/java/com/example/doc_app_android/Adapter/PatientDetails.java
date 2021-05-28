package com.example.doc_app_android.Adapter;

public class PatientDetails {

    private String name;
    private String last_checkUp;
    private String age;
    private String case_level;
    private String state;
    private boolean expandable = false;

    public PatientDetails(String name, String last_checkUp, String age, String case_level, String state) {
        this.name = name;
        this.last_checkUp = last_checkUp;
        this.age = age;
        this.case_level = case_level;
        this.state = state;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_checkUp() {
        return last_checkUp;
    }

    public void setLast_checkUp(String last_checkUp) {
        this.last_checkUp = last_checkUp;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCase_level() {
        return case_level;
    }

    public void setCase_level(String case_level) {
        this.case_level = case_level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
