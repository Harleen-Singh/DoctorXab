package com.example.doc_app_android.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.NotiData;
import com.example.doc_app_android.databinding.SingleListNotiBinding;
import java.util.ArrayList;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {
    private ArrayList<NotiData> notiData;


    public notificationAdapter() {
        notiData = new ArrayList<>();
    }

    public void setData(ArrayList<NotiData> d){
        this.notiData = d;
    }

    @NonNull
    @Override
    public notificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleListNotiBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_noti,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapter.ViewHolder holder, int position) {
        holder.binding.setNotiData(notiData.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return notiData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleListNotiBinding binding;
        public ViewHolder(@NonNull SingleListNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
