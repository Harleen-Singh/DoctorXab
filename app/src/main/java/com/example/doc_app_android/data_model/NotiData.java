package com.example.doc_app_android.data_model;

public class NotiData {
    String id, data, sender, receiver, icon, status, time, date;

    public NotiData(String id, String data, String sender, String receiver, String icon, String status) {
        this.id = id;
        this.data = data;
        this.sender = sender;
        this.receiver = receiver;
        this.icon = icon;
        this.status = status;
    }

    public NotiData(String id, String data, String sender, String receiver, String icon, String status, String time, String date) {
        this.id = id;
        this.data = data;
        this.sender = sender;
        this.receiver = receiver;
        this.icon = icon;
        this.status = status;
        this.time = time;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
