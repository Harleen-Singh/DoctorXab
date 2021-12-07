package com.example.doc_app_android.data_model;

public class ProblemsList {

    String problem, id, department;

    public ProblemsList(String problem, String id, String department) {
        this.problem = problem;
        this.id = id;
        this.department = department;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
