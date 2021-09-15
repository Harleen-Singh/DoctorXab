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
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;

public class AppointWithAdapter extends RecyclerView.Adapter<AppointWithAdapter.AppointmentWithViewHolder> {

    private ArrayList<Event> nameList;
    private Context mContext;

    public AppointWithAdapter(ArrayList<Event> nameList, Context mContext) {
        this.nameList = nameList;
        this.mContext = mContext;
    }

    public void setData(ArrayList<Event> nameList){
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public AppointmentWithViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.appointment_row, parent, false);
        return new AppointmentWithViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentWithViewHolder holder, int position) {
        Event feed = nameList.get(position);
        holder.binding.appointmentDoctorName.setText(String.valueOf(feed.getData()));
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
