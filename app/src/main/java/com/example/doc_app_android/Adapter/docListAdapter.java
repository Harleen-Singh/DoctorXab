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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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
import com.example.doc_app_android.data_model.ProfileData;
import com.example.doc_app_android.databinding.AppointmentFromDoctorBinding;
import com.example.doc_app_android.utils.PreferencesName;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class docListAdapter extends RecyclerView.Adapter<docListAdapter.viewHolder> implements Filterable {
    public ArrayList<DocData> data;
    public ArrayList<DocData> backup;
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
    private SharedPreferences.Editor editor;
    private ImageView no_res1;
    private TextView no_res;
    private RecyclerView rcv;
    private Button buttonFromRegistration;

    public docListAdapter(ArrayList<DocData> arr, CharSequence date, docListFragment docDialog, boolean duringRegisteration, TextView no_res, ImageView no_res1, RecyclerView rcv) {
        data = arr;
        this.backup = new ArrayList<>(arr);
        this.date = date;
        this.docDialog = docDialog;
        this.duringRegisteration = duringRegisteration;
        this.no_res = no_res;
        this.no_res1 = no_res1;
        this.rcv = rcv;

    }

    public docListAdapter(ArrayList<DocData> arr, CharSequence date, docListFragment docDialog, boolean duringRegisteration, TextView no_res, ImageView no_res1, RecyclerView rcv, Button buttonFromRegistration) {
        data = arr;
        this.backup = new ArrayList<>(arr);
        this.date = date;
        this.docDialog = docDialog;
        this.duringRegisteration = duringRegisteration;
        this.no_res = no_res;
        this.no_res1 = no_res1;
        this.rcv = rcv;
        this.buttonFromRegistration = buttonFromRegistration;

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
                preferences = v.getContext().getApplicationContext().getSharedPreferences("tokenFile", Context.MODE_PRIVATE);

                if (!duringRegisteration) {
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
                                    selectedDate = String.valueOf(year + "-" + addZeroToStart(String.valueOf(month+1)) + "-" + addZeroToStart(String.valueOf(dayOfMonth)));
                                    fromDoctorBinding.selectedDate.setText(selectedDate);
                                }
                            }, mYear, mMonth, mDay);
                            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
//                    giveDataToModel(docName, docID);
//                    Log.e("TAG", "onClick: " + docName + docID);


                    editor = preferences.edit();
                    editor.putString(PreferencesName.SELECTED_DOCTOR_ID, docID);
                    editor.apply();

                    buttonFromRegistration.setText("Dr." + docName);
                    Log.d("Testing", "During registration in docListFragment " + duringRegisteration + "with name and id " + docName + " and " + docID);
                    docDialog.dismiss();



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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<DocData> filteredData = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredData.addAll(backup);
            } else {
                for (DocData obj : backup) {
                    if (obj.getName().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredData.add(obj);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredData;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results != null) {
                data.clear();
                Log.d("TextWatcher", "Size of Published Data: " + ((ArrayList<ProfileData>) results.values).size());

                if (((ArrayList<ProfileData>) results.values).size() > 0) {

                    data.addAll((ArrayList<DocData>) results.values);
                    no_res.setVisibility(View.GONE);
                    no_res1.setVisibility(View.GONE);
                    rcv.setVisibility(View.VISIBLE);

                } else {
                    rcv.setVisibility(View.GONE);
                    no_res.setVisibility(View.VISIBLE);
                    no_res1.setVisibility(View.VISIBLE);
                }

                notifyDataSetChanged();
                Log.d("TextWatcher", "publishResults: I am working");
            }

        }
    };


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
