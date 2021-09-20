package com.example.doc_app_android.Dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.databinding.ConfirmationDialogBinding;
import com.example.doc_app_android.services.getAppointmentService;

public class dialogs {

    private boolean ifFromGetAppointmentService = false;
    private Dialog dialog;
    private ConfirmationDialogBinding confirmationBinding;

    public void alertDialogLogin(ProgressDialog progressDialog, String msg) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissDialog(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }

    public final void displayDialog(String str, Context context, boolean ifFromGetAppointmentService) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(str);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
        if (ifFromGetAppointmentService) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    alertDialog.dismiss();
                }
            }, 900);
        }
    }

    public final void displayDialog(String str, Context context) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(str);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
    }

    public final void displayConfirmationDialog(String str, Context context, DocData docData, CharSequence date, docListFragment docDialog) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        confirmationBinding = ConfirmationDialogBinding.inflate(LayoutInflater.from(context));
        confirmationBinding.getRoot().setBackgroundResource(android.R.color.transparent);
        confirmationBinding.validationMessage.setText(str);

        confirmationBinding.Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAppointmentService service = new getAppointmentService();
                ProgressDialog dialog1 = new ProgressDialog(context, R.style.AlertDialog);
                alertDialogLogin(dialog1, "Processing");
                service.getAppointment(docData, date, context, dialog1);
                docDialog.dismiss();
                dialog.dismiss();
                Log.e("TAG", "displayConfirmationDialog: confirmed");

            }
        });

        confirmationBinding.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setContentView(confirmationBinding.getRoot());
        Window window1 = dialog.getWindow();
        window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public final void displayShareConfirmationDialog(String str, Context context, DocData docData) {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        confirmationBinding = ConfirmationDialogBinding.inflate(LayoutInflater.from(context));
        confirmationBinding.getRoot().setBackgroundResource(android.R.color.transparent);
        confirmationBinding.validationMessage.setText(str);

        confirmationBinding.Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAppointmentService service = new getAppointmentService();
                ProgressDialog dialog1 = new ProgressDialog(context, R.style.AlertDialog);
                alertDialogLogin(dialog1, "Processing");
                service.shareReport(context, dialog1, docData);
                dialog.dismiss();
                Log.e("TAG", "displayConfirmationDialog: confirmed");

            }
        });

        confirmationBinding.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(confirmationBinding.getRoot());
        Window window1 = dialog.getWindow();
        window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

}
