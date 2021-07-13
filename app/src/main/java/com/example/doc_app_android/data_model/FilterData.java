package com.example.doc_app_android.data_model;

public class FilterData {
    String Filter;
    String id;
    String department;

    public FilterData(String filter, String id, String department) {
        Filter = filter;
        this.id = id;
        this.department = department;
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

    public String getFilter() {
        return Filter;
    }

    public void setFilter(String filter) {
        Filter = filter;
    }

    public FilterData(String filter) {
        Filter = filter;
    }

}
