package com.example.doc_app_android.DataModel;

import android.content.Context;

import com.example.doc_app_android.Service.loginService;

public class LoginModel {
    loginService service ;
    String loginUsername , password;

    private boolean isDoc , isPatient ;
    private String userID, email , username;

    public LoginModel(String username, String password , Context context) {
        this.loginUsername = username;
        this.password = password;
        service = new loginService(loginUsername , this.password  , context);
    }

    public String getloginUsername() {
        return loginUsername;
    }


    public String getPassword() {
        return password;
    }
}
