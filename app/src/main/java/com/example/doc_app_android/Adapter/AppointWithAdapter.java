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
    private OnCancelListener onCancelListener;

    public AppointWithAdapter(ArrayList<Event> nameList, Context mContext, OnCancelListener onCancelListener) {
        this.nameList = nameList;
        this.mContext = mContext;
        this.onCancelListener = onCancelListener;
    }

    public void setData(ArrayList<Event> nameList) {
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public AppointmentWithViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppointmentRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.appointment_row, parent, false);
        return new AppointmentWithViewHolder(binding, onCancelListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentWithViewHolder holder, int position) {
        Event feed = nameList.get(position);
        String name = String.valueOf(feed.getData());
        String[] arr = name.split("-");
        if(arr[2].equals("before")) {
            holder.binding.appointmentWithLabel.setText("YOU HAD AN APPOINTMENT WITH");
            holder.binding.appointmentDoctorName.setText(arr[0]);
            holder.binding.appointmentCancel.setVisibility(View.GONE);
        }else {
            holder.binding.appointmentCancel.setVisibility(View.VISIBLE);
            holder.binding.appointmentDoctorName.setText(arr[0]);
        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    protected class AppointmentWithViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppointmentRowBinding binding;
        OnCancelListener onCancelListener;

        public AppointmentWithViewHolder(@NonNull AppointmentRowBinding binding, OnCancelListener onCancelListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onCancelListener = onCancelListener;
            binding.appointmentCancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCancelListener.onCancelClick(getAbsoluteAdapterPosition(), binding);
        }
    }

    public interface OnCancelListener {
        void onCancelClick(int position, AppointmentRowBinding binding);
    }
}
