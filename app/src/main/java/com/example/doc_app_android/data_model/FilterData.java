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
