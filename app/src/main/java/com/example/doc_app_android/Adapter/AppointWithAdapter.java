package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.databinding.AppointmentRowBinding;

import java.util.ArrayList;

public class AppointWithAdapter extends RecyclerView.Adapter<AppointWithAdapter.AppointmentWithViewHolder> {

    private ArrayList<String> nameList;
    private Context mContext;

    public AppointWithAdapter(ArrayList<String> nameList, Context mContext) {
        this.nameList = nameList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AppointmentWithViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.appointment_row, parent, false);
        return new AppointmentWithViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentWithViewHolder holder, int position) {
        holder.binding.appointmentDoctorName.setText(nameList.get(position));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    protected class AppointmentWithViewHolder extends RecyclerView.ViewHolder {

        AppointmentRowBinding binding;
        public AppointmentWithViewHolder(@NonNull AppointmentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
