package com.example.doc_app_android.Dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.services.getAppointmentService;

public class dialogs {

    private boolean ifFromGetAppointmentService = false;

    public void alertDialogLogin(ProgressDialog progressDialog , String msg){
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissDialog(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }

    public final void displayDialog(String str,Context context, boolean ifFromGetAppointmentService) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(str);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
        if(ifFromGetAppointmentService){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    alertDialog.dismiss();
                }
            }, 800);
        }
    }

    public final void displayDialog(String str,Context context) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(str);
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
    }

    public final void displayConfirmationDialog(String title, String str, Context context, DocData docData, CharSequence date, docListFragment docDialog) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogLowRadius);
        builder.setMessage(str);
        builder.setTitle(title);
        builder.setPositiveButton("Confirm" ,(dialog, which) -> {
            getAppointmentService service = new getAppointmentService();
            ProgressDialog dialog1 = new ProgressDialog(context, R.style.AlertDialog);
            alertDialogLogin(dialog1,"Processing");
            if(date!=null){
                service.getAppointment(docData, date, context, dialog1);
            }else{
                service.shareReport(context,dialog1,docData);
            }
            docDialog.dismiss();
            Log.e("TAG", "displayConfirmationDialog: confirmed" );

        });
        builder.setNegativeButton("Dismiss" , (dialog, which) -> {
            Log.e("TAG", "displayConfirmationDialog: Canceled" );
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().getWindowStyle();
    }

}
