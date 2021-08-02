package com.example.doc_app_android.Dialogs;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.example.doc_app_android.R;

public class dialogs {

    public void alertDialogLogin(ProgressDialog progressDialog , String msg){
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissDialog(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }

    public final void displayDialog(String str,Context context) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(str);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
    }
}
