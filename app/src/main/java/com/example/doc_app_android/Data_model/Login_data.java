package com.example.doc_app_android.Data_model;


import android.content.Context;

import com.example.doc_app_android.services.loginService;


public class Login_data {
    loginService service;
    String loginUsername, password;

    public Login_data(String username, String password, Context context) {
        this.loginUsername = username;
        this.password = password;
    }

    public String getloginUsername() {
        return loginUsername;
    }


    public String getPassword() {
        return password;
    }
}

