package com.example.doc_app_android.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doc_app_android.PatentHomeFragments.FragmentXrayReport;
import com.example.doc_app_android.R;
import com.example.doc_app_android.data_model.Xray_data;
import com.example.doc_app_android.databinding.SingleListXrayBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class X_Ray_adapter extends RecyclerView.Adapter<X_Ray_adapter.X_RayVH> {

    private ArrayList<Xray_data> data = new ArrayList<>();
    Fragment temp = null;
    private Context mContext;
    private SharedPreferences preferences;

    public X_Ray_adapter(Context context) {
        this.mContext =context;
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
        Picasso.get().load(data.get(position).getImageUrl()).error(R.drawable.errorimgload).placeholder(R.drawable.loading).into(holder.binding.imgXray);
        Log.e(" image url -->", "onBindViewHolder: "+data.get(position).getImageUrl() );
        holder.binding.btnViewXray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = mContext.getSharedPreferences("tokenFile", Context.MODE_PRIVATE);
                temp = FragmentXrayReport.newInstance(data.get(position));
                boolean isDoc = preferences.getBoolean("isDoc", false);
                if(isDoc) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)mContext ;

                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container,temp).addToBackStack("xray").commit();
                } else {
                    AppCompatActivity appCompatActivity = (AppCompatActivity)mContext ;
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHome_container,temp).addToBackStack(null).commit();
                }


            }
        });

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
