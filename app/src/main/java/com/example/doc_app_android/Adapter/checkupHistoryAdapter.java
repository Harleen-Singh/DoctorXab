package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.CkpHstryData;
import com.example.doc_app_android.databinding.ChkUpHistoryBinding;

import java.util.ArrayList;


public class checkupHistoryAdapter extends RecyclerView.Adapter<checkupHistoryAdapter.ViewHolder> {
    private ArrayList<CkpHstryData> data;

    public checkupHistoryAdapter() {
        // empty constructor
    }

  public void setData(ArrayList<CkpHstryData> d){this.data = d;}

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        ChkUpHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.chk_up_history,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull checkupHistoryAdapter.ViewHolder holder, int position) {
            holder.binding.setChkData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ChkUpHistoryBinding binding;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull ChkUpHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
