package com.example.doc_app_android.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.databinding.SingleListXrayBinding;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class X_Ray_adapter extends RecyclerView.Adapter<X_Ray_adapter.X_RayVH> {
    private ArrayList<Xray_data> data;
    public X_Ray_adapter() {
        //empty
    }
    public void setdata(ArrayList<Xray_data> d){
        this.data=d;
    }

    @NonNull
    @NotNull
    @Override
    public X_RayVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SingleListXrayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_list_xray,parent,false);
        return new X_RayVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull X_Ray_adapter.X_RayVH holder, int position) {
        holder.binding.setXRayData(data.get(position));
        Log.d("TAG", "onBindViewHolder: "+ data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class X_RayVH extends RecyclerView.ViewHolder{
        SingleListXrayBinding binding;
        public X_RayVH(@NonNull @NotNull SingleListXrayBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
