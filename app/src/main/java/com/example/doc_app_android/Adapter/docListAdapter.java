package com.example.doc_app_android.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.Dialogs.dialogs;
import com.example.doc_app_android.Dialogs.docListFragment;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.DocData;
import com.example.doc_app_android.databinding.AppointmentFromDoctorBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class docListAdapter extends RecyclerView.Adapter<docListAdapter.viewHolder> {
    public ArrayList<DocData> data;
    docListFragment docDialog;
    private CharSequence date;
    public MutableLiveData<String> docId = new MutableLiveData<>();
    public MutableLiveData<String> docName = new MutableLiveData<>();
    private boolean duringRegisteration;
    private Dialog dialog1;
    private AppointmentFromDoctorBinding fromDoctorBinding;
    private int mDay, mMonth, mYear, mHour, mMinute;
    private String selectedTime, selectedDate;
    private SharedPreferences preferences;

    public docListAdapter(ArrayList<DocData> arr, CharSequence date, docListFragment docDialog, boolean duringRegisteration) {
        data = arr;
        this.date = date;
        this.docDialog = docDialog;
        this.duringRegisteration = duringRegisteration;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doclist_single, null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Picasso.get().load(data.get(position).getImage()).placeholder(R.drawable.doctor_profile_image).into(holder.image);
        if (!TextUtils.isEmpty(data.get(position).getName())) {
            holder.name.setText(data.get(position).getName());
        }
        Log.e("TAG", "onBindViewHolder: " + data.get(position).getName());
        String docName = data.get(position).getName().toUpperCase();
        DocData newData = data.get(position);
        String docID = data.get(position).getDocID();

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!duringRegisteration) {

                    preferences = v.getContext().getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);

                    dialog1 = new Dialog(v.getContext());
                    fromDoctorBinding = AppointmentFromDoctorBinding.inflate(LayoutInflater.from(v.getContext()));
                    fromDoctorBinding.getRoot().setBackgroundResource(android.R.color.transparent);


                    if (!preferences.getBoolean("isDoc", false)) {
                        fromDoctorBinding.addAppointmentWithPatient.setText("Book Appointment");
                        String text = "Dr." + docName;
                        fromDoctorBinding.appointmentPatientName.setText(text);
                    } else {

                        fromDoctorBinding.appointmentPatientName.setText(docName);
                    }


                    fromDoctorBinding.selectedDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            mDay = calendar.get(Calendar.DAY_OF_MONTH);
                            mMonth = calendar.get(Calendar.MONTH);
                            mYear = calendar.get(Calendar.YEAR);
                            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    selectedDate = String.valueOf(year + "-" + addZeroToStart(String.valueOf(month)) + "-" + addZeroToStart(String.valueOf(dayOfMonth)));
                                    fromDoctorBinding.selectedDate.setText(selectedDate);
                                }
                            }, mYear, mMonth, mDay);
                            datePickerDialog.show();
                        }
                    });

                    fromDoctorBinding.selectedTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            mHour = calendar.get(Calendar.HOUR_OF_DAY);
                            mMinute = calendar.get(Calendar.MINUTE);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    selectedTime = String.valueOf(addZeroToStart(String.valueOf(hourOfDay)) + ":" + addZeroToStart(String.valueOf(minute)));
                                    fromDoctorBinding.selectedTime.setText(selectedTime);
                                }
                            }, mHour, mMinute, true);
                            timePickerDialog.show();
                        }
                    });

                    fromDoctorBinding.addAppointmentWithPatient.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            manageClicks(v, docName, newData, selectedDate, selectedTime);
                            dialog1.dismiss();
                        }
                    });

                    dialog1.setContentView(fromDoctorBinding.getRoot());
                    Window window1 = dialog1.getWindow();
                    window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog1.show();

                } else {
                    giveDataToModel(docName, docID);
                    Log.e("TAG", "onClick: " + docName + docID);
                }
            }
        });

    }

    public String addZeroToStart(String value) {
        if (value.length() == 1) {
            value = "0" + value;
            return value;
        }
        return value;
    }

    private void giveDataToModel(String docName, String docID) {
        docId.setValue(docID);
        this.docName.setValue(docName);
        docDialog.dismiss();
    }

    private void manageClicks(View v, String docName, DocData newData, String selectedDate, String selectedTime) {
        dialogs dialog = new dialogs();

        dialog.displayConfirmationDialog("Do you want to book appointment with Dr." + docName + "?", v.getContext(), newData, selectedDate, docDialog, selectedTime);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView image;
        ConstraintLayout click;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.docImage);
            name = itemView.findViewById(R.id.Doc_name);
            click = itemView.findViewById(R.id.click);
        }
    }
}
